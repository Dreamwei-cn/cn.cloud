package cn.cloud.api.message;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.cloud.api.user.entity.SysUser;




@Component
public class Producer {
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private Queue queue;
	@Autowired
	private Topic topic;
	
	private static String TYPE_STRING = String.class.getName();
	private static String TYPE_SYS_USER = SysUser.class.getName();
	
	public void sendQueueMsg(String msg) throws Exception{
		Assert.notNull(msg, "String ms is null ");
		Map<String, String> headers = new HashMap<>();
		headers.put("type", TYPE_STRING);
		jmsMessagingTemplate.convertAndSend(queue,msg);
	}
	public void sendTopicMsg(String msg) throws Exception{
		Assert.notNull(msg, "String ms is null ");
		jmsMessagingTemplate.convertAndSend(queue,msg);
	}
	


	
}
