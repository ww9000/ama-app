package app.ww.ama.persistence.dao.sql;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.ww.ama.persistence.AbstractDAO;
import app.ww.ama.persistence.dao.ReplyDAO;
import app.ww.ama.persistence.dto.Reply;

@Primary
@Repository
@Transactional
public class ReplyDAOSQL extends AbstractDAO<Reply, Integer> implements ReplyDAO {

	@Override
	public List<Reply> findByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDtoClass() {
		this.dtoClass = Reply.class;
	}

}
