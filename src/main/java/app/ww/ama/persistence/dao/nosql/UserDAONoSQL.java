package app.ww.ama.persistence.dao.nosql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import app.ww.ama.persistence.dao.UserDAO;
import app.ww.ama.persistence.dto.User;

@Repository
public class UserDAONoSQL implements UserDAO {

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return new ArrayList<User>();
	}

}
