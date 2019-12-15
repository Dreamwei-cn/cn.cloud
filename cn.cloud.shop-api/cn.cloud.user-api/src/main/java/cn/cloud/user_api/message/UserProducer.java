package cn.cloud.user_api.message;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.utils.base.DreamClassUtills;




//@Service
public class UserProducer {
	
//	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
//	@Autowired
	private Queue queue;
//	@Autowired
	private Topic topic;
	
	
	private static final Logger log = LoggerFactory.getLogger(UserProducer.class);

	public void sendQueueMsg(String msg) throws JMSException{
		log.info(">>>>>>>> send  string msg " + msg);
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
	
	
	/**
	 * @param des  自定义 目的 String
	 * @param msg  String
	 * 
	 * @throws JMSException
	 */
	public void sendMsgByName(String des ,String msg) {
		Assert.notNull(msg, "String ms is null ");
		Assert.notNull(des, "des ms is null ");
		log.info(" test transaction  >>>>>>>> send  string msg " + msg  +"<<<<<<<<" + des);
		
		jmsMessagingTemplate.convertAndSend(des, msg);
		
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
