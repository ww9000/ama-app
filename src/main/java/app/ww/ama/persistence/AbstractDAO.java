package app.ww.ama.persistence;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import app.ww.ama.common.AbstractLogger;

public abstract class AbstractDAO extends AbstractLogger {

	@Autowired
	protected SessionFactory sessionFactory;

}
