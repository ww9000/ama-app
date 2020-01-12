package app.ww.ama.testapps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import app.ww.ama.configuration.PersistenceConfiguration;

public class LoggerApp {

	private static final Logger logger = LogManager.getLogger(LoggerApp.class);
	
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(PersistenceConfiguration.class);
		logger.info("---------------------- Test Log Successful ------------------------");
		((ConfigurableApplicationContext)ctx).close();
	}

}
