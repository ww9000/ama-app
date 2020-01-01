package app.ww.ama.persistence.dao;

import java.util.List;

import app.ww.ama.persistence.dto.User;

public interface UserDAO {

	public void save(User user);
	
	public List<User> getUsers();
	
}