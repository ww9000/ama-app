package app.ww.ama.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import app.ww.ama.common.AbstractLogger;

@Transactional
public abstract class AbstractDAO<DTO, PK extends Serializable> extends AbstractLogger {

	@Autowired
	private SessionFactory sessionFactory;

	protected Class<DTO> dtoClass;

	public abstract void setDtoClass();

	public List<DTO> findAll() {
		logger.debug("Getting listing for: " + this.dtoClass.getName());
		@SuppressWarnings("unchecked")
		List<DTO> list = getCurrentSession().createQuery("from " + this.dtoClass.getName()).list();
		return list;
	}

	public DTO findById(PK id) {
		logger.debug(
				"Finding object of class: " + this.dtoClass.getName() + " with id: " + JSONObject.wrap(id).toString());
		return getCurrentSession().get(dtoClass, id);
	}

	public PK save(DTO object) {
		logger.debug("Saving entity: " + JSONObject.wrap(object).toString());
		@SuppressWarnings("unchecked")
		PK savedId = (PK) getCurrentSession().save(object);
		return savedId;
	}

	public DTO update(DTO object) {
		logger.debug("Updating object: " + JSONObject.wrap(object).toString());
		@SuppressWarnings("unchecked")
		DTO updatedObject = (DTO) getCurrentSession().merge(object);
		return updatedObject;
	}

	public void delete(DTO object) {
		logger.debug("Deleting object: " + JSONObject.wrap(object).toString());
		getCurrentSession().delete(object);
	}

	public void deleteById(PK id) {
		logger.debug("Deleting object with id: " + JSONObject.wrap(id).toString());
		DTO entity = findById(id);
		delete(entity);
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}
