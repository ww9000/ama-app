package app.ww.ama.testapps;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import app.ww.ama.context.PersistenceConfiguration;
import app.ww.ama.persistence.dao.UserDAO;
import app.ww.ama.persistence.dto.User;

public class HibernateApp {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(PersistenceConfiguration.class);
		UserDAO userDao = ctx.getBean(UserDAO.class);
		List<User> allUsers = userDao.getUsers();
		for(User user : allUsers) {
			System.out.println(user.getId());
		}
		((ConfigurableApplicationContext)ctx).close();
	}
	
}
