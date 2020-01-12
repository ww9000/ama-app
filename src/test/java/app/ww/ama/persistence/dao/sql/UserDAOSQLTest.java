package app.ww.ama.persistence.dao.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import app.ww.ama.persistence.JUnitAbstractDAO;
import app.ww.ama.persistence.dao.UserDAO;
import app.ww.ama.persistence.dto.User;

@Transactional
public class UserDAOSQLTest extends JUnitAbstractDAO {

	@Autowired
	private UserDAO dao;

	public enum TestUsers {
		BG("gilfoyle", "Bertram Gilfoyle"),
		GB("gavin", "Gavin Belson");

		public String id;
		public String name;

		private TestUsers(String id, String name) {
			this.id = id;
			this.name = name;
		}
	}
	
	@Test
	public void testFindAll() {
		List<User> allUsers = dao.findAll();
		assertEquals(0, allUsers.size());
	}

	@Test
	@Rollback(true)
	public void testSave() {
		// Add user 1
		String savedUserId = dao.save(new User(TestUsers.BG.id, TestUsers.BG.name));
		assertEquals(TestUsers.BG.id, savedUserId);
		assertEquals(1, dao.findAll().size());

		// Add user 2 with no name
		savedUserId = dao.save(new User(TestUsers.GB.id, null));
		assertEquals(TestUsers.GB.id, savedUserId);
		assertEquals(2, dao.findAll().size());
	}

	@Test
	@Rollback(true)
	public void testSaveGeneratedTimestamps() {
		// Add user
		String savedUserId = dao.save(new User(TestUsers.BG.id, TestUsers.BG.name));
		dao.findAll();
		User retrievedUser = dao.findById(savedUserId);
		
		// Check dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String today = dateFormat.format(Calendar.getInstance().getTime());
		assertEquals(today, dateFormat.format(retrievedUser.getCreationTimestamp()));
		assertEquals(today, dateFormat.format(retrievedUser.getUpdatedTimestamp()));
	}
	
	@Test
	@Rollback(true)
	public void testSaveDuplicateUserId() {
		assertThrows(NonUniqueObjectException.class, () -> {
			dao.save(new User(TestUsers.BG.id, TestUsers.BG.name));
			dao.save(new User(TestUsers.BG.id, TestUsers.BG.name));
		});
	}

	@Test
	@Rollback(true)
	public void testSaveNullId() {
		assertThrows(IdentifierGenerationException.class, () -> {
			dao.save(new User(null, null));
		});
	}

	@Test
	@Rollback(true)
	public void testFindById() {
		// Add user
		String savedUserId = dao.save(new User(TestUsers.BG.id, TestUsers.BG.name));
		// Retrieve user
		User retrievedUser = dao.findById(savedUserId);
		assertEquals(TestUsers.BG.id, retrievedUser.getId());
		assertEquals(TestUsers.BG.name, retrievedUser.getName());
	}
	
	@Test
	@Rollback(true)
	public void testUpdate() {
		// Add user
		String savedUserId = dao.save(new User(TestUsers.BG.id, TestUsers.BG.name));
		User savedUser = dao.findById(savedUserId);
		// Change values
		savedUser.setName(TestUsers.GB.name);
		savedUser.setIsDeleted(true);
		User updatedUser = dao.update(savedUser);
		assertEquals(TestUsers.GB.name, updatedUser.getName());
		assertEquals(true, updatedUser.getIsDeleted());
		assertEquals(1, dao.findAll().size());
	}
	
	@Test
	@Rollback(true)
	public void testUpdateId() {
		// Add user
		String savedUserId = dao.save(new User(TestUsers.BG.id, TestUsers.BG.name));
		User savedUser = dao.findById(savedUserId);
		// Change id value
		savedUser.setId(TestUsers.GB.id);
		assertThrows(PersistenceException.class, () -> {
			dao.update(savedUser);
			dao.findAll();
		});
	}
}
