package app.ww.ama.context;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import app.ww.ama.common.EncryptionService;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application.properties" })
@ComponentScan({ "app.ww.ama.persistence.dto", "app.ww.ama.persistence.dao." + "${datasource.type}" })
public class PersistenceConfiguration {

	private static final String DTO_PACKAGE = "app.ww.ama.persistence.dto";

	private static final String PROP_DATASOURCE_DRIVER = "datasource.driver";
	private static final String PROP_DATASOURCE_URL = "datasource.url";
	private static final String PROP_DATASOURCE_USERNAME = "datasource.username";
	private static final String PROP_DATASOURCE_PASSWORD = "datasource.password";
	private static final String PROP_DATASOURCE_KEYPATH = "datasource.keypath";

	private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROP_HIBERNATE_HBM2DDL = "hibernate.hbm2ddl.auto";

	private static final Logger logger = LogManager.getLogger(PersistenceConfiguration.class);

	@Autowired
	private Environment env;

	@Autowired
	private EncryptionService encryptionSvc;

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { DTO_PACKAGE });
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	private Properties hibernateProperties() {
		Properties props = new Properties();

		props.setProperty("hibernate.hbm2ddl.auto", env.getProperty(PROP_HIBERNATE_HBM2DDL));
		props.setProperty("hibernate.dialect", env.getProperty(PROP_HIBERNATE_DIALECT));
		props.setProperty("hibernate.globally_quoted_identifiers", "true");

		return props;
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName(env.getProperty(PROP_DATASOURCE_DRIVER));
		dataSource.setUrl(env.getProperty(PROP_DATASOURCE_URL));

		try {
			String keyPath = env.getProperty(PROP_DATASOURCE_KEYPATH);
			if (keyPath == null || keyPath.length() == 0) {
				dataSource.setUsername(env.getProperty(PROP_DATASOURCE_USERNAME));
				dataSource.setPassword(env.getProperty(PROP_DATASOURCE_PASSWORD));
			} else {
				String encryptedUsername = env.getProperty(PROP_DATASOURCE_USERNAME + ".encrypted");
				String encryptedPassword = env.getProperty(PROP_DATASOURCE_PASSWORD + ".encrypted");

				dataSource.setUsername(encryptionSvc.decrypt(encryptedUsername, keyPath));
				dataSource.setPassword(encryptionSvc.decrypt(encryptedPassword, keyPath));
			}
		} catch (GeneralSecurityException e) {
			logger.error("Failed to decrypt credentails.\n" + e.getMessage());
		} catch (IOException e) {
			logger.error("Failed to get key file.\n" + e.getMessage());
		}

		return dataSource;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
