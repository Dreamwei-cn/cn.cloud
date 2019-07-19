package cn.cloud.common.message.activeMQ.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class ConsumerTopic {
	
	
	@JmsListener(destination = "mytopic" ,containerFactory = "jmsTopicListenerContainerFactory")
//	@SendTo("replyTo.topic")
	public String receive(String textString) {
		System.out.println("<<<<<<<<<<<<<<< 接受消息topic "+textString);
		
		return textString;
	}

}
