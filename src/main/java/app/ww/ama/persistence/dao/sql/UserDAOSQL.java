package app.ww.ama.persistence.dao.sql;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import app.ww.ama.persistence.dao.UserDAO;
import app.ww.ama.persistence.dto.User;

@Primary
@Repository
public class UserDAOSQL implements UserDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory (SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(user);
		tx.commit();
		session.close();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		Session session = this.sessionFactory.openSession();
		List<User> userList = session.createQuery("from User").list();
		session.close();
		return userList;
	}
}
