package cn.cloud.user_api.message;

import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import cn.cloud.common.util.DreamExceptionUtils;
import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.utils.base.DreamClassUtills;

@Component
public class UserConsumer {


	private static final Logger log = LoggerFactory.getLogger(UserConsumer.class);

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
	
	@JmsListener(destination = "${queueName}" ,containerFactory = "jmsContainerFactoryQueue")
	public void handleQueueMsg(TextMessage msg,Session session) throws Exception {
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
			log.info(" reverie a  queue message " + type + " : " + message);
			DreamExceptionUtils.throwExceptopm("this is a error queue message type ");
		}
		
		try {
			msg.acknowledge();
		} catch (Exception e) {
			session.recover();
			log.error(" queue  message  recover !");
		}
		
	}
	

}
