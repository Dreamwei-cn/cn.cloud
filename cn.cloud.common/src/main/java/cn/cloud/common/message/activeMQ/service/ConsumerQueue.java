package cn.cloud.common.message.activeMQ.service;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;


@Component
public class ConsumerQueue {
	
	
	
	//@JmsListener(destination = "myqueue",containerFactory = "myJmsDefaultContainerFactoryQueue")
	@JmsListener(destination = "myqueue",containerFactory = "jmsQueueListenerContainerFactory")	
	@SendTo("replyTo.queue")
	public String receive(String textString) {
		System.out.println("<<<<<<<<<<<<<< 接受消息 ： myqueue  " +textString);
		return textString;
		
	}
	@JmsListener(destination = "myqueue",containerFactory = "jmsDefaultContainerFactoryQueueRe")	
	@SendTo("replyTo.queue")
	public String receive1(TextMessage textString,Session session) throws JMSException {
		
		// 使用手动签收模式，需要手动的调用，如果不在catch中调用session.recover()消息只会在重启服务后重发
		try {
			textString.acknowledge();
			System.out.println("++++++++++++ 重新发送配置  +++++++   "+ textString.getText());
			return textString.getText();
		} catch (JMSException e) {
			session.recover();
		}
		return null;
		
	}

}
