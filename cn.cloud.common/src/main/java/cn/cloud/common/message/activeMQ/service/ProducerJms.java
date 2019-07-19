package cn.cloud.common.message.activeMQ.service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.Message;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
@Component
public class ProducerJms {
	@Resource
	private JmsTemplate jmsTemplate;
	
	public void sendQueueMsg(String destinationName,String mesageString) {
		Destination destination = new ActiveMQQueue(destinationName);
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public javax.jms.Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(mesageString);
				return textMessage;
			}
		});
		jmsTemplate.convertAndSend(destination, mesageString);
		System.out.println(">>>>>>> 发送： Queue "+ destinationName+": "+ mesageString);
	}

}
