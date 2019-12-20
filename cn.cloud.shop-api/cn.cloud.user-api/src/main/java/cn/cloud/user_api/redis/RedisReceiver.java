package cn.cloud.user_api.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisReceiver {
	
	
	private static final Logger log = LoggerFactory.getLogger(RedisReceiver.class);

	public void receiveUserMessage(String message) {
		log.info(" receiveUserMessage  接受一条消息 " + message);
	}

	public void receiveOrderMessage(String message) {
		log.info(" receiveOrderMessage  接受一条消息 " + message);
	}

}
