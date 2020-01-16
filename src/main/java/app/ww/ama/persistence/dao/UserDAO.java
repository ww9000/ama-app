package app.ww.ama.persistence.dao;

import java.util.List;

import app.ww.ama.persistence.dto.User;

public interface UserDAO {

	public List<User> findAll();

	public User findById(String id);

	public String save(User user);

	public User update(User user);

	public void clearCache();
}