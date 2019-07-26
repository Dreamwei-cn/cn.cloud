
package cn.cloud.common.message.rabbit.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();		
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		
		Connection connection =  connectionFactory.newConnection();

		Channel channel = connection.createChannel();
		
//		testSend(channel);
		directExchange(channel);
		
		channel.close();
		connection.close();

	}
	
	public  static void  testSend(Channel channel) throws IOException {
		
		String msg = "Helle RabbitMQ      113";
		

		channel.basicPublish("", "test01",  MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
		System.out.println("  +++++++发送消息 ：  1  " + msg);
		   //关闭通道和连接

		
	}
	
	/**
	 * @param channel  直连
	 * @throws IOException
	 */
	public static void directExchange(Channel channel) throws IOException {
		String exchangeNameString = "test_direct_ex";
		String routingKey = "test_direct";
		String msg = "  Test  direct  ex";
		channel.basicPublish(exchangeNameString, routingKey, null,msg.getBytes() );
		System.out.println("  +++++++发送消息 ：  1  " + msg);
	}
	
	
public static void topicExcheng(Channel channel) throws IOException {
		
		String exchengeName = "topic_ex";
		String routingKey1 = "user.save";
		String routingKey2 = "user.update.abc";
		String msgString = " Topic  message ";
		channel.basicPublish(exchengeName, routingKey1, null, (msgString+routingKey1).getBytes());
		channel.basicPublish(exchengeName, routingKey2, null, (msgString+routingKey2).getBytes());
	}

}
