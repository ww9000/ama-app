package app.ww.ama.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractLogger {

	protected final Logger logger = LogManager.getLogger(getClass());
}
