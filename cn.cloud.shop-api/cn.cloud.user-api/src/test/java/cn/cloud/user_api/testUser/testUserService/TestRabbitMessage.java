package cn.cloud.user_api.testUser.testUserService;



import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.cloud.user_api.rabbit.RabbitProducer;
import cn.cloud.user_api.user.entity.SysUser;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRabbitMessage {
	
	@Autowired
	private RabbitProducer rabbitProducer;
	
	@Test
	public void testSend() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("String", "string");
		String msg =  new String(" rabbit  message ");
		rabbitProducer.sendMessage(msg, map);
	}
	
	@Test
	public void testSendObject() {
		
		SysUser sysUser = new SysUser();
		sysUser.setName("dream");
		rabbitProducer.sendUserObjectMessage(sysUser);
	}
	
	@Test
	public void testSendDelay() {
		
		SysUser sysUser = new SysUser();
		sysUser.setName("dream");
		rabbitProducer.sendMsg("test_queue_1","hello i am delay msg11");
	}

}
