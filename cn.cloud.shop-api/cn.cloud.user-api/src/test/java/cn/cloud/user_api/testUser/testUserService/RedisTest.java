package cn.cloud.user_api.testUser.testUserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.cloud.user_api.redis.RedisTransaction;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
	
	@Autowired
	private RedisTransaction redisTransaction;
	
	@Test
	public void testRedisTransaction() {
		redisTransaction.redisTransaciton();
	}

}
