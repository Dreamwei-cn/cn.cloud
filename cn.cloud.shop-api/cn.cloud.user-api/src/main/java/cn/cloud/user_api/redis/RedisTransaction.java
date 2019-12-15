package cn.cloud.user_api.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTransaction {

	@Autowired
	private StringRedisTemplate sTemplate;

	public void redisTransaciton() {
		sTemplate.opsForValue().set("watch", "2");
		
	}

}
