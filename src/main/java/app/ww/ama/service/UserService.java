package app.ww.ama.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.ww.ama.persistence.dao.UserDAO;
import app.ww.ama.persistence.dto.User;

@Service
@Transactional
public class UserService extends AbstractService {
	
	@Autowired
	private UserDAO dao;
	
	public UserService() {};
	
	public List<User> getAllUsers() {
		return dao.findAll();
	}
	
	public void save(List<User> users) {
		for(User user : users) {
			save(user);
		}
	}
	
	public void save(User user) {
		dao.save(user);
	}
}
