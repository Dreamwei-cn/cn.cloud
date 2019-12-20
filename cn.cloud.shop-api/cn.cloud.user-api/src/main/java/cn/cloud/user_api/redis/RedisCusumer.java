package cn.cloud.user_api.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisCusumer implements MessageListener{

	
	private static final Logger log = LoggerFactory.getLogger(RedisCusumer.class);

	@Autowired
	private StringRedisTemplate  sTemplate;
	@Override
	public void onMessage(Message message, byte[] pattern) {
		RedisSerializer<String> valueSerializer = sTemplate.getStringSerializer();
		String deserialize = valueSerializer.deserialize(message.getBody());
		log.info("接受到redis消息", deserialize);
		
	}

}
