package app.ww.ama.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.ww.ama.persistence.dao.UserDAO;
import app.ww.ama.persistence.dto.User;

@Service
public class UserService extends AbstractService {
	
	@Autowired
	private UserDAO dao;
	
	public UserService() {};
	
	public List<User> getAllUsers() {
		return dao.getUsers();
	}
}
