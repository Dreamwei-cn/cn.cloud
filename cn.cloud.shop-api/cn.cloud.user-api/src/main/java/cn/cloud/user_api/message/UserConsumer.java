package cn.cloud.user_api.message;


import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cloud.common.util.DreamExceptionUtils;
import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.utils.base.DreamClassUtills;

@Service
public class UserConsumer {


	private static final Logger log = LoggerFactory.getLogger(UserConsumer.class);
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@Autowired 
	private  UserProducer userProducer;

//	
//	@JmsListener(destination = "${queueName}" ,containerFactory = "jmsContainerFactoryQueue")
//	public String handleQueueMsg(Message<?> msg,Session session) throws JMSException {
//		Object type = msg.getHeaders().get(DreamClassUtills.TYPE);
//		String classType = null;
//		String message ="";
//		SysUser user = null;
//		try {
//			classType = String.valueOf(type);
//			if (StringUtils.isEmpty(classType)) {
//				DreamExceptionUtils.ISNULL("发送消息失败！");
//			}
//			if (DreamClassUtills.TYPE_STRING.equals(classType)) {
//				message = (String) msg.getPayload();
//				
//			}
//			if (DreamClassUtills.TYPE_SYS_USER.equals(classType)) {
//				user = (SysUser)msg.getPayload();
//				message = user.getName();
//			}
//			log.info(" reverie a  queue message " + message + " : " + classType);
//		} catch (Exception e) {
//			session.recover();
//			log.error(" queue  message  recover !");
//		}		
//		return message;
//	}
//	@JmsListener(destination = "${topicName}" ,containerFactory = "jmsContainerFactoryTopic")
//	public String handleTopicMsg(Message<?> msg,Session session) throws JMSException {
//		Object type = msg.getHeaders().get(DreamClassUtills.TYPE);
//		String classType = null;
//		String message ="";
//		SysUser user = null;
//		try {
//			classType = String.valueOf(type);
//			if (StringUtils.isEmpty(classType)) {
//				DreamExceptionUtils.ISNULL("发送消息失败！");
//			}
//			if (DreamClassUtills.TYPE_STRING.equals(classType)) {
//				message = (String) msg.getPayload();
//				
//			}
//			if (DreamClassUtills.TYPE_SYS_USER.equals(classType)) {
//				user = (SysUser)msg.getPayload();
//				message = user.getName();
//			}
//			log.info(" reverie a Topic message " + message + " : " + classType);
//		} catch (Exception e) {
//			session.recover();
//			log.error(" Topic  message  recover !");
//		}		
//		return message;
//	}
	
	@JmsListener(destination = "${queueName}" ,containerFactory = "jmsContainerFactoryQueueNOJmsTransaction")
	public void handleQueueMsg(TextMessage msg,Session session) throws Exception {
		log.info(" queueName <<<<<<<<<<<<<<<<< 接受消息");
		String type = msg.getText();
		Object object = msg.getObjectProperty("value");
		ActiveMQMessage  mqMessage = (ActiveMQMessage )msg;
		String message = "";
		SysUser sysUser = null;
		if (DreamClassUtills.TYPE_STRING.equals(type)) {
			message = (String) object;
		}else if (DreamClassUtills.TYPE_SYS_USER.equals(type)) {
			sysUser =(SysUser) object;
			message = sysUser.getName();
		}else {
			log.info(" reverie a  queue message " + type + " : " + message);
			DreamExceptionUtils.throwExceptopm("this is a error queue message type ");
		}

		
		try {
			if (message.contains("error") &&  mqMessage.getRedeliveryCounter()<4) {
				log.error(" queue  222224  message  recover !" + message + ":  " + mqMessage.getRedeliveryCounter()) ;
				session.recover();				
			}else {
				msg.acknowledge();
			}
			
		} catch (Exception e) {
			log.error(" queue  message  recover !");
			session.recover();
			
		}
		
	}


	
	
	
	@JmsListener(destination = "${topicName}" ,containerFactory = "jmsContainerFactoryTopic")
	public void handleTopicMsg(TextMessage msg,Session session) throws Exception {
		log.info(" <<<<<<<<<<<<<<<<< 接受消息");
		String type = msg.getText();
		Object object = msg.getObjectProperty("value");
		String message = "";
		SysUser sysUser = null;
		if (DreamClassUtills.TYPE_STRING.equals(type)) {
			message = (String) object;
		}else if (DreamClassUtills.TYPE_SYS_USER.equals(type)) {
			sysUser =(SysUser) object;
			message = sysUser.getName();
		}else {
			log.info(" reverie a  Topic message " + type + " : " + message);
			DreamExceptionUtils.throwExceptopm("this is a error Topic message type ");
		}
		
		try {
	
			msg.acknowledge();
		} catch (Exception e) {
			log.error(" Topic  message  recover !");
			session.recover();
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * @param msg
	 *  使用默认连接工厂    sessionTransactionManager    使用监听器  触发事务 回滚
	 */
	@JmsListener(destination = "mq:ta" )
	public void handleQueueMsgTransaction(String msg)  {
        log.debug("Get JMS message to from customer:{}", msg);
        String reply = "Replied - " + msg;
//        jmsMessagingTemplate.convertAndSend("transaction", reply);
        userProducer.sendMsgByName("transaction", reply);
        if (msg.contains("error")) {
            simulateError();
        }

	}
	
	@Transactional // 加注解之后  直接调用也可以回滚    jmsTransactionManager 不会重试
	@JmsListener(destination = "mq:ta1" ,containerFactory = "jmsContainerFactoryQueue")
	public void handleQueueMsgJmsTransaction(String msg)  {
        log.debug("Get JMS message to from customer:{}", msg);
        String reply = "Replied - " + msg;
//        jmsMessagingTemplate.convertAndSend("transaction", reply);
        userProducer.sendMsgByName("transaction", reply);
        if (msg.contains("error")) {
            simulateError();
        }

	}

	
	
	private void simulateError() {
        throw new RuntimeException("some Data error.");
    }

}
