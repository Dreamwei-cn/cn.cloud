package cn.cloud.common.message.activeMQ;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqConsumer {


	@JmsListener(destination = "cn.cloud.queue")
	public void receiveQueue(String text) {
		System.out.println("  接收报文  QUEUE: " + text);
	}
	
	
	@JmsListener(destination = "cn.cloud.topic",containerFactory = "jmsListenerContainerTopic")
	public void receiveTopic(String text) {
		System.out.println("  接收报文  TOPIC: " + text);
	}
}
