package app.ww.ama.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import app.ww.ama.common.AbstractLogger;

@Transactional
public abstract class AbstractDAOSQL<DTO, PK extends Serializable> extends AbstractLogger {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private Gson gson;

	protected Class<DTO> dtoClass;

	public abstract void setDtoClass();

	public List<DTO> findAll() {
		logger.debug("Getting listing for: " + this.dtoClass.getName());
		@SuppressWarnings("unchecked")
		List<DTO> list = getCurrentSession().createQuery("from " + this.dtoClass.getName()).list();
		return list;
	}

	public DTO findById(PK id) {
		logger.debug("Finding object of class: " + this.dtoClass.getName() + " with id: " + gson.toJson(id));
		return getCurrentSession().get(dtoClass, id);
	}

	public PK save(DTO object) {
		logger.debug("Saving entity: " + gson.toJson(object));
		@SuppressWarnings("unchecked")
		PK savedId = (PK) getCurrentSession().save(object);
		logger.debug("Saved entity of class: " + dtoClass.getName() + " with id: " + gson.toJson(savedId));
		return savedId;
	}

	public List<PK> save(DTO[] objects) {
		List<PK> savedPKs = new ArrayList<>();
		for (DTO object : objects) {
			@SuppressWarnings("unchecked")
			PK savedId = (PK) getCurrentSession().save(object);
			savedPKs.add(savedId);
		}
		return savedPKs;
	}

	public DTO update(DTO object) {
		logger.debug("Updating object: " + gson.toJson(object));
		@SuppressWarnings("unchecked")
		DTO updatedObject = (DTO) getCurrentSession().merge(object);
		return updatedObject;
	}

	public void delete(DTO object) {
		logger.debug("Deleting object: " + gson.toJson(object));
		getCurrentSession().delete(object);
	}

	public void deleteById(PK id) {
		logger.debug("Deleting object with id: " + gson.toJson(id));
		DTO entity = findById(id);
		delete(entity);
	}

	protected Query getQuery(QueryCriteria... criteriaList) {
		CriteriaBuilder critBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<DTO> critQuery = critBuilder.createQuery(dtoClass);
		Root<DTO> fromCrit = critQuery.from(dtoClass);

		List<Predicate> predicates = new ArrayList<Predicate>();
		for (QueryCriteria criteria : criteriaList) {
			switch (criteria.getConditionType()) {
			case EQUAL:
				Predicate predicate = critBuilder.equal(fromCrit.get(criteria.getFieldName()),
						criteria.getFieldValue());
				predicates.add(predicate);
				break;

			default:
				logger.error("Failed to parse query condition type: " + criteria.getConditionType().name());
				break;
			}
		}
		critQuery.where(predicates.toArray(new Predicate[] {}));
		
		return getCurrentSession().createQuery(critQuery);
	}

	public void clearCache() {
		getCurrentSession().clear();
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}
