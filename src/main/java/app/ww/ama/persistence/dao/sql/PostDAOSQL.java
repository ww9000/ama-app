package app.ww.ama.persistence.dao.sql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.ww.ama.persistence.AbstractDAO;
import app.ww.ama.persistence.dao.PostDAO;
import app.ww.ama.persistence.dto.Post;

@Primary
@Repository
@Transactional
public class PostDAOSQL extends AbstractDAO<Post, Integer> implements PostDAO {
	
	@Autowired
	@Override
	public void setDtoClass() {
		this.dtoClass = Post.class;
	}

	@Override
	public List<Post> findByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
