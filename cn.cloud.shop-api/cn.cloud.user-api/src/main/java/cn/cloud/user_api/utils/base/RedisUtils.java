package cn.cloud.user_api.utils.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

public class RedisUtils extends cn.cloud.common.util.RedisUtils {

	@Autowired
	private StringRedisTemplate sTemplate;
	
	public void sendMessage(String channel, String message) {
		Assert.notNull(channel, "消息主题不能为空");
		Assert.notNull(message, "不能发送空消息");
		sTemplate.convertAndSend(channel, message);
	}
}
