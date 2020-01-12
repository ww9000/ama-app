package app.ww.ama.persistence;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

import app.ww.ama.common.AbstractLogger;
import app.ww.ama.configuration.JUnitCommonConfiguration;
import app.ww.ama.configuration.JUnitPersistenceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JUnitPersistenceConfiguration.class, JUnitCommonConfiguration.class })
public class JUnitAbstractDAO extends AbstractLogger {

	@Autowired
	protected Gson gson;
	
}
