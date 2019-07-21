package cn.cloud.common.message.activeMQ.service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class TestMessage {
	private static final String URL_STRING= "failover:(tcp://127.0.0.1:61617,tcp://127.0.0.1:61618)?randomize";
	
	private static final String QUEUENQME_STRING = "test";
	
	public void producer() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL_STRING);
		Connection connection =  connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(QUEUENQME_STRING);
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		TextMessage textMessage = session.createTextMessage("消息");
		producer.send(textMessage);
		
		connection.close();
	}
	public void consumer() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL_STRING);
		Connection connection =  connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(QUEUENQME_STRING);
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				System.out.println("接受消息" +textMessage);
			}
		});
		consumer.setMessageListener( m ->{
			System.out.println((TextMessage)m + " ：接受消息");
		});
		
		
	}
	public static void main(String[] args) {
		
	}

}
