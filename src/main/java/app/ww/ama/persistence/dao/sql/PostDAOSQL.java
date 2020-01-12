package app.ww.ama.persistence.dao.sql;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.ww.ama.persistence.AbstractDAO;
import app.ww.ama.persistence.dao.PostDAO;
import app.ww.ama.persistence.dto.Post;
import app.ww.ama.persistence.dto.User;

@Primary
@Repository
@Transactional
public class PostDAOSQL extends AbstractDAO<Post, Integer> implements PostDAO {
	
	@Autowired
	@Override
	public void setDtoClass() {
		this.dtoClass = Post.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> findByUser(User poster) {
		CriteriaBuilder critBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Post> critQuery = critBuilder.createQuery(Post.class);
		Root<Post> fromCrit = critQuery.from(Post.class);
		
		Predicate condition = critBuilder.equal(fromCrit.get("poster"), poster);
		critQuery.where(condition);
		Query query = getCurrentSession().createQuery(critQuery);
		
		return query.getResultList();
	}
}
