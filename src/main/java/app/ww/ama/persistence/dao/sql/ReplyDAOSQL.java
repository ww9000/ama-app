package app.ww.ama.persistence.dao.sql;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.ww.ama.persistence.AbstractDAOSQL;
import app.ww.ama.persistence.QueryCriteria;
import app.ww.ama.persistence.QueryCriteria.QueryConditionType;
import app.ww.ama.persistence.dao.ReplyDAO;
import app.ww.ama.persistence.dto.Post;
import app.ww.ama.persistence.dto.Reply;
import app.ww.ama.persistence.dto.User;

@Primary
@Repository
@Transactional
public class ReplyDAOSQL extends AbstractDAOSQL<Reply, Integer> implements ReplyDAO {

	@Autowired
	@Override
	public void setDtoClass() {
		this.dtoClass = Reply.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reply> findByUser(User poster) {
		QueryCriteria criteria = new QueryCriteria("replier", QueryConditionType.EQUAL, poster);
		Query query = getQuery(criteria);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reply> findByParentPost(Post post) {
		QueryCriteria criteria = new QueryCriteria("parentPost", QueryConditionType.EQUAL, post);
		Query query = getQuery(criteria);
		
		return query.getResultList();
	}
}
