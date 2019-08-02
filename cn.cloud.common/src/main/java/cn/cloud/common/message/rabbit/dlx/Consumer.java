package cn.cloud.common.message.rabbit.dlx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;

public class Consumer {
	
	private static Channel getChannle() throws IOException, TimeoutException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		return channel;
	}
	
	private  static void testDlx(Channel channel) throws IOException {
		String exChange = "test_dlx";
		String routingKey = "dlx.*";
		String queueName = "test_dlx_queue";
		
		com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel);
		channel.exchangeDeclare(exChange, "topic", true, false, null);
		
		Map<String, Object> arguments = new HashMap<>();
		// 声明 死信队列     到队列  
		arguments.put("x-dead-letter-exchange", "dlx.exchange");		
		channel.queueDeclare(queueName, true, false, false, arguments);
		
		
		channel.queueBind(queueName, exChange, routingKey);
	
		//死信队列的声明
		channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);	
		channel.queueDeclare("dlx.queue", true, false, false, null);
		channel.queueBind("dlx.queue", "dlx.exchange", "#");
		
		
		channel.basicConsume(queueName, true,consumer);
		
	}
	
	public static void main(String[] args) throws IOException, TimeoutException {
		testDlx(getChannle());
	}

}
