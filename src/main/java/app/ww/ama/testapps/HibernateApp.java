package app.ww.ama.testapps;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.gson.Gson;

import app.ww.ama.configuration.CommonConfiguration;
import app.ww.ama.configuration.PersistenceConfiguration;
import app.ww.ama.persistence.dao.PostDAO;
import app.ww.ama.persistence.dto.Post;
import app.ww.ama.persistence.dto.User;
import app.ww.ama.service.UserService;

public class HibernateApp {

	private static ApplicationContext ctx;

	private static Gson gson;
	
	public static void main(String[] args) {
		ctx = new AnnotationConfigApplicationContext(
				new Class[] { CommonConfiguration.class, PersistenceConfiguration.class });
		gson = ctx.getBean(Gson.class);
//		saveUsers();
//		getUsers();
//		savePosts();
//		getPosts();
		((ConfigurableApplicationContext) ctx).close();
	}
	
//	public static void savePosts() {
//		PostDAO dao = ctx.getBean(PostDAO.class);
//		Post post =  new Post();
//		post.setPosterId("wanway1");
//		post.setContent("hihi");
//		Integer id = dao.save(post);
//		
//		post = dao.findById(id);
//		System.out.println(gson.toJson(post));
//	}
	
	public static void getPosts() {
		PostDAO dao = ctx.getBean(PostDAO.class);
		Post post = dao.findById(2);
		System.out.println(gson.toJson(post));
	}

	public static void jsonifyObject() {
		User user = new User();
		user.setId("wanway");
		System.out.println(JSONObject.wrap(user).toString());
	}

	@SuppressWarnings("unused")
	private static void saveUsers() {
		UserService userService = ctx.getBean(UserService.class);
		System.out.println("Saving new user.");
		ArrayList<User> users = new ArrayList<>();
		User user1 = new User();
		user1.setId("wanway1");
		users.add(user1);
		User user2 = new User();
		user2.setId("wanway2");
		users.add(user2);
		userService.save(users);
	}

	@SuppressWarnings("unused")
	private static void getUsers() {
		UserService userService = ctx.getBean(UserService.class);
		List<User> allUsers = userService.getAllUsers();
		for (User eachUser : allUsers) {
			System.out.println(JSONObject.wrap(eachUser).toString());
		}
	}
}
