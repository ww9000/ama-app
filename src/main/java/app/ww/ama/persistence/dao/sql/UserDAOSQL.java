package app.ww.ama.persistence.dao.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.ww.ama.persistence.AbstractDAOSQL;
import app.ww.ama.persistence.dao.UserDAO;
import app.ww.ama.persistence.dto.User;

@Primary
@Repository
@Transactional
public class UserDAOSQL extends AbstractDAOSQL<User, String> implements UserDAO {

	@Autowired
	@Override
	public void setDtoClass() {
		this.dtoClass = User.class;
	}
}
