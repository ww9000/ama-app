package app.ww.ama.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import app.ww.ama.persistence.Response;
import app.ww.ama.persistence.dto.User;
import app.ww.ama.service.UserService;

@RestController
public class Controller {
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = "/test")
	public Response test() {
		return new Response(0, "okok");
	}
	
	@GetMapping("")
	public List<User> empty() {
		List<User> users = userService.getAllUsers();
		return users;
	}
}
