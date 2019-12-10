package cn.cloud.user_api.message;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.utils.base.DreamClassUtills;




@Component
public class UserProducer {
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private Queue queue;
	@Autowired
	private Topic topic;
	

	public void sendQueueMsg(String msg) throws JMSException{
		Assert.notNull(msg, "String ms is null ");
		TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText(DreamClassUtills.TYPE_STRING);
		textMessage.setObjectProperty("value", msg);
		jmsMessagingTemplate.convertAndSend(queue,textMessage);
	}
	public void sendTopicMsg(String msg) throws JMSException{
		Assert.notNull(msg, "String ms is null ");
		TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText(DreamClassUtills.TYPE_STRING);
		textMessage.setObjectProperty("value", msg);
		jmsMessagingTemplate.convertAndSend(topic, textMessage);
		
	}
	
	public void sendQueueSysUser(SysUser sysUser) throws JMSException{
		Assert.notNull(sysUser, "String ms is null ");
		TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText(DreamClassUtills.TYPE_SYS_USER);
		textMessage.setObjectProperty("value", sysUser);
		jmsMessagingTemplate.convertAndSend(queue,textMessage);
	}
	public void sendTopicSysUser(SysUser sysUser) throws JMSException{
		Assert.notNull(sysUser, "String ms is null ");

		TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText(DreamClassUtills.TYPE_SYS_USER);
		textMessage.setObjectProperty("value", sysUser);
		jmsMessagingTemplate.convertAndSend(topic, textMessage);
	}
	


	
}
