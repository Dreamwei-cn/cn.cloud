package cn.cloud.api.order;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import cn.cloud.api.order.service.RabbitSender;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAdmin {
	
	
	
	@Resource
	private RabbitAdmin rabbitAdmin;
	@Test
	public void testAdmin() {
		DirectExchange exchange = new DirectExchange("admin_direct",false,false);
		rabbitAdmin.declareExchange(exchange);
	}

	@Autowired
	private RabbitSender rabbitSender;
	@Test
	public  void testSend1() {
		Map<String, Object> properties = new HashMap<> ();
		
		properties.put("number", "111");
		rabbitSender.send("Hello RabbitMq form  Spring boot ", properties);
	}
	

}