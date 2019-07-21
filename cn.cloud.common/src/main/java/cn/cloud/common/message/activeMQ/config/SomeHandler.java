package cn.cloud.common.message.activeMQ.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class SomeHandler implements ErrorHandler {

	
	private static final Logger log = LoggerFactory.getLogger(SomeHandler.class);

	@Override
	public void handleError(Throwable t) {
		log.error("Error in listener", t);

	}

}
