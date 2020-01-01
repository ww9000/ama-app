package app.ww.ama.context;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
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

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
@ComponentScan({"app.ww.ama.persistence.dto", "app.ww.ama.persistence.dao."+"${datasource.type}"})
public class PersistenceConfiguration {

	private final String DTO_PACKAGE = "app.ww.ama.persistence.dto";
	
	private final String PROP_DATASOURCE_DRIVER = "datasource.driver";
	private final String PROP_DATASOURCE_URL = "datasource.url";
	private final String PROP_DATASOURCE_USERNAME = "datasource.username";
	private final String PROP_DATASOURCE_PASSWORD = "datasource.password";

	private final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
	private final String PROP_HIBERNATE_HBM2DDL = "hibernate.hbm2ddl.auto";
	
	@Autowired
	private Environment env;

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { DTO_PACKAGE });
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty(PROP_DATASOURCE_DRIVER));
		dataSource.setUrl(env.getProperty(PROP_DATASOURCE_URL));
		dataSource.setUsername(env.getProperty(PROP_DATASOURCE_USERNAME));
		dataSource.setPassword(env.getProperty(PROP_DATASOURCE_PASSWORD));

		return dataSource;
	}

	@Bean
	public Properties hibernateProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.hbm2ddl.auto", env.getProperty(PROP_HIBERNATE_HBM2DDL));
		props.setProperty("hibernate.dialect", env.getProperty(PROP_HIBERNATE_DIALECT));
		props.setProperty("hibernate.globally_quoted_identifiers", "true");

		return props;
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
