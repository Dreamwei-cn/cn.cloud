package cn.cloud.common.message.rabbit.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {
	
	public static Channel getChannel() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		return channel;
	}
	
	public static void confirmReceive(Channel channel) throws IOException, TimeoutException {

		com.rabbitmq.client.Consumer consumer = new SimConsumer(channel);
		String exChangeName = " confirm-ex";
		String routingKey = "confirm.*";
		
		String queueName = channel.queueDeclare().getQueue();
		channel.exchangeDeclare(exChangeName, "topic", true);
		channel.queueBind(queueName, exChangeName, routingKey);
		channel.basicConsume(queueName, true, consumer);
	}
	public static void returnReceive(Channel channel) throws IOException, TimeoutException {

		com.rabbitmq.client.Consumer consumer = new SimConsumer(channel);
		String exChangeName = "test_return";
		String routingKey = "return.save";
		
		String queueName = channel.queueDeclare().getQueue();
		channel.exchangeDeclare(exChangeName, "topic", true);
		channel.queueBind(queueName, exChangeName, routingKey);
		channel.basicConsume(queueName, true, consumer);
	}

	
	public static void main(String[] args) throws IOException, TimeoutException {
//		confirmReceive(getChannel());
		returnReceive(getChannel());
	}
}
