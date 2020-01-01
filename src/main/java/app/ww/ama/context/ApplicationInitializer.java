package app.ww.ama.context;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { PersistenceConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ApplicationConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {};
	}
	
	@Override
	protected void registerContextLoaderListener(ServletContext servletContext) {
		servletContext.addListener(new JDBCDeregisterListener());
		super.registerContextLoaderListener(servletContext);
	}
}
