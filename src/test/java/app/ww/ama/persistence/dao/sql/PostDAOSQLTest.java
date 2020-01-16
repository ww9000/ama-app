package app.ww.ama.persistence.dao.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.PropertyValueException;
import org.hibernate.TransientPropertyValueException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import app.ww.ama.persistence.JUnitAbstractDAO;
import app.ww.ama.persistence.dao.PostDAO;
import app.ww.ama.persistence.dao.UserDAO;
import app.ww.ama.persistence.dto.Post;
import app.ww.ama.persistence.dto.User;

public class PostDAOSQLTest extends JUnitAbstractDAO {

	@Autowired
	private PostDAO postDao;

	@Autowired
	private UserDAO userDao;
	private User userOne = new User(TestPosts.POST_ONE.posterId, null);
	private User userTwo = new User(TestPosts.POST_TWO.posterId, null);

	public enum TestPosts {
		POST_ONE("gilfoyle", "CODE GAY", "You're gay for my code, you're code gay.", 999),
		POST_TWO("gavin", "A BETTER PLACE",
				"I don't want to live in a world where someone else makes the world a better place better than we do.",
				0),
		POST_THREE("hanneman", "LOOK IN THE MIRROR AND SAY", "This guy fucks.", -69);

		public String posterId;
		public String title;
		public String content;
		public Integer rating;

		private TestPosts(String posterId, String title, String content, Integer rating) {
			this.posterId = posterId;
			this.title = title;
			this.content = content;
			this.rating = rating;
		}
	}

	/*
	 * Populate Users to satisfy foreign key constraint on posterId
	 */
	@Before
	public void setup() {
		userDao.save(userOne);
		userDao.save(userTwo);
	}

	@Test
	@Rollback(true)
	public void testSaveAndFindById() {
		Post postOne = new Post(TestPosts.POST_ONE.title, TestPosts.POST_ONE.content);
		postOne.setPoster(userOne);
		Integer postOneId = postDao.save(postOne);
		assertTrue(postOneId > 0);
		
		Post retrievedPost = postDao.findById(postOneId);
		assertEquals(TestPosts.POST_ONE.content, retrievedPost.getContent());
	}

	@Test
	@Rollback(true)
	public void testFindByIdNotFound() {
		Post postOne = postDao.findById(1);
		assertEquals(postOne, null);
	}
	
	@Test
	@Rollback(true)
	public void testFindByUser() {
		Post postOne = new Post(TestPosts.POST_ONE.title, TestPosts.POST_ONE.content);
		postOne.setPoster(userTwo);
		Integer postOneId = postDao.save(postOne);

		List<Post> retrievedPosts = postDao.findByUser(userTwo);
		assertTrue(retrievedPosts.size() == 1);
		
		Post postTwo = new Post(TestPosts.POST_TWO.title, TestPosts.POST_TWO.content);
		postTwo.setPoster(userTwo);
		Integer postTwoId = postDao.save(postTwo);
		
		retrievedPosts = postDao.findByUser(userTwo);
		assertTrue(retrievedPosts.size() == 2);
		assertEquals(postOneId, retrievedPosts.get(0).getId());
		assertEquals(postTwoId, retrievedPosts.get(1).getId());
	}
	
	@Test
	@Rollback(true)
	public void testSavePostWithInvalidPoster() {

		// Null poster
		assertThrows(PropertyValueException.class, () -> {
			Integer postId = postDao.save(new Post(TestPosts.POST_THREE.title, TestPosts.POST_THREE.content));
			assertTrue(postId > 0);
		});

		// Unsaved poster
		assertThrows(TransientPropertyValueException.class, () -> {
			User user = new User("invalid", null);
			Integer postId = postDao.save(new Post(TestPosts.POST_THREE.title, TestPosts.POST_THREE.content, user));
			assertTrue(postId > 0);
		});
	}
	
	@Test
	@Rollback(true)
	public void testUpdate() {
		Post post = new Post(TestPosts.POST_THREE.title, TestPosts.POST_THREE.content);
		post.setPoster(userOne);
		postDao.save(post);
		
		post.setRating(TestPosts.POST_THREE.rating);
		String censoredString = post.getContent().replace("fucks", "**CENSORED**"); 
		post.setContent(censoredString);
		Post updatedPost = postDao.update(post);
		assertEquals(TestPosts.POST_THREE.rating, updatedPost.getRating());
		assertEquals(censoredString, updatedPost.getContent());
	}
}
