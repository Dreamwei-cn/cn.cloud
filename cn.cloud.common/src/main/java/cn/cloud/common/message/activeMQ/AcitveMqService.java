package cn.cloud.common.message.activeMQ;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AcitveMqService {
	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	@Autowired
	private Queue queue;
	@Autowired
	private Topic topic;
	public void sendMessage(Destination destination ,String message) {
		jmsTemplate.convertAndSend(destination,message);
	}
	
	public void sendMessage(String message) {
		jmsTemplate.convertAndSend(queue,message);
	}
	
	public void publish(String message) {
		jmsTemplate.convertAndSend(topic,message);
	}

}
