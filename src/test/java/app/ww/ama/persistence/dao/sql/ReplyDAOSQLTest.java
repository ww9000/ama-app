package app.ww.ama.persistence.dao.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import app.ww.ama.persistence.JUnitAbstractDAO;
import app.ww.ama.persistence.dao.PostDAO;
import app.ww.ama.persistence.dao.ReplyDAO;
import app.ww.ama.persistence.dao.UserDAO;
import app.ww.ama.persistence.dao.sql.PostDAOSQLTest.TestPosts;
import app.ww.ama.persistence.dto.Post;
import app.ww.ama.persistence.dto.Reply;
import app.ww.ama.persistence.dto.User;

public class ReplyDAOSQLTest extends JUnitAbstractDAO {
	
	@Autowired
	private ReplyDAO replyDao;

	@Autowired
	private UserDAO userDao;
	private User userOne = new User(TestPosts.POST_ONE.posterId, null);
	private User userTwo = new User(TestPosts.POST_TWO.posterId, null);
	
	@Autowired
	private PostDAO postDao;
	private Post postOne = new Post(TestPosts.POST_ONE.title, TestPosts.POST_ONE.content, userOne);
	private Post postTwo = new Post(TestPosts.POST_TWO.title, TestPosts.POST_TWO.content, userTwo);
	
	@Before
	public void setup() {
		userDao.save(userOne);
		userDao.save(userTwo);
		postDao.save(postOne);
		postDao.save(postTwo);
	}
	
	@Test
	@Rollback(true)
	public void testSaveAndFindById() {
		Reply replyOne = new Reply(userOne, postOne, "CONTENTS");
		Integer replyOneId = replyDao.save(replyOne);
		
		Reply retrievedReplyOne = replyDao.findById(replyOneId);
		assertEquals(replyOne.getContent(), retrievedReplyOne.getContent());
	}
	
	@Test
	@Rollback(true)
	public void testFindByUser() {
		Reply replyOne = new Reply(userOne, postOne, "CONTENTS");
		replyDao.save(replyOne);
		
		List<Reply> replies = replyDao.findByUser(userOne);
		assertTrue(replies.size() == 1);
		
		Reply replyTwo = new Reply(userOne, postTwo, "CONTENTS");
		replyDao.save(replyTwo);
		
		replies = replyDao.findByUser(userOne);
		assertTrue(replies.size() == 2);
	}
	
	@Test
	@Rollback(true)
	public void testUpdate() {
		Reply replyOne = new Reply(userOne, postOne, "CONTENTS");
		replyDao.save(replyOne);
		
		String newContent = "";
		Integer newRating = 1;
		
		replyOne.setContent(newContent);
		replyOne.setRating(newRating);
		Reply retrievedReplyOne = replyDao.update(replyOne);
		
		assertEquals(newContent, retrievedReplyOne.getContent());
		assertEquals(newRating, retrievedReplyOne.getRating());
	}
	
	@Test
	@Rollback(true)
	public void testUpdateNonUpdatableFields() {
		Reply replyOne = new Reply(userOne, postOne, "CONTENTS");
		Integer replyOneId = replyDao.save(replyOne);
		
		replyOne.setParentPost(postTwo);
		replyOne.setReplier(userTwo);
		replyDao.update(replyOne);
		replyDao.clearCache();
		Reply updatedReplyOne = replyDao.findById(replyOneId);
		assertEquals(postOne.getId(), updatedReplyOne.getParentPost().getId());
		assertEquals(userOne.getId(), updatedReplyOne.getReplier().getId());
	}
	
	@Test
	@Rollback(true)
	public void testFindByParentPost() {
		Reply replyOne = new Reply(userOne, postOne, "REPLYONE");
		Integer replyOneId = replyDao.save(replyOne);
		
		List<Reply> repliesToPostOne = replyDao.findByParentPost(postOne);
		assertTrue(repliesToPostOne.size() == 1);
		
		Reply replyTwo = new Reply(userTwo, postOne, "REPLYTWO");
		Integer replyTwoId = replyDao.save(replyTwo);
		
		repliesToPostOne = replyDao.findByParentPost(postOne);
		assertTrue(repliesToPostOne.size() == 2);
		assertEquals(replyOneId, repliesToPostOne.get(0).getId());
		assertEquals(replyTwoId, repliesToPostOne.get(1).getId());
	}
}
