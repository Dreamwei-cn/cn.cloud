package cn.cloud.common.message.activeMQ.service;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;





@Component
public class Producer {
	
	@Resource
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	public void sendQueueMsg(String destinationName,String mesageString) {
		Destination destination = new ActiveMQQueue(destinationName);

		jmsMessagingTemplate.convertAndSend(destination, mesageString);
		System.out.println(">>>>>>> 发送： Queue "+ destinationName+": "+ mesageString);
	}
	
	public void sendTopicMsg(String destinationName,String mesageString) {
		Destination destination = new ActiveMQTopic(destinationName);
		jmsMessagingTemplate.convertAndSend(destination, mesageString);
		System.out.println(">>>>>>> 发送： Topic "+ destinationName+": "+ mesageString);
	}
	
	@JmsListener(destination = "replyTo.queue",containerFactory = "jmsQueueListenerContainerFactory")
	public void consumerMessageQueue(String text){
	    System.out.println("从replyTo.queue收到报文"+text);
	}

	@JmsListener(destination = "replyTo.topic",containerFactory = "jmsTopicListenerContainerFactory")
	public void consumerMessageTopic(String text){
	    System.out.println("从replyTo.topic收到报文"+text);
	}
	
}
