package cn.cloud.common.message.rabbit.test;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestMessage {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = getChannel();
		
		Map<String, Object> headers = new HashMap<>();
		headers.put("my1", "111");
		
		AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
				.deliveryMode(2)
				.contentEncoding("UTF-8")
				.expiration("10000")
				.headers(headers)
				.build();
				
		channel.basicPublish("", "test001", properties, "message".getBytes());
		System.out.println("发送消息");
	}

	public static Channel getChannel() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory =new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		return channel;
	}
}
