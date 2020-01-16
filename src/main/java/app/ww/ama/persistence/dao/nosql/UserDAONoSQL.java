package app.ww.ama.persistence.dao.nosql;

import java.util.List;

import org.springframework.stereotype.Repository;

import app.ww.ama.persistence.dao.UserDAO;
import app.ww.ama.persistence.dto.User;

@Repository
public class UserDAONoSQL<T> implements UserDAO {

	@Override
	public String save(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		
	}

}
