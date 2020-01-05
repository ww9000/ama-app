package app.ww.ama.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import app.ww.ama.common.EncryptionService;
import app.ww.ama.persistence.dto.User;
import app.ww.ama.service.UserService;

@RestController
public class Controller {
	
	@Autowired
	UserService userService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@GetMapping(value = "/getAllUsers")
	public List<User> test() {
		List<User> users = userService.getAllUsers();
		return users;
	}
	
	@GetMapping("")
	public String home() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("hello", "world");
		objectNode.put("!", "!");
		return objectNode.toPrettyString();
	}
	
	@GetMapping("/encrypt")
	public String encrypt() throws GeneralSecurityException, IOException {
		return encryptionService.decrypt(
				encryptionService.encrypt("password123", "/home/wayne/Workspaces/ama/creds/secret.key"),
				"/home/wayne/Workspaces/ama/creds/secret.key");
	}
}
