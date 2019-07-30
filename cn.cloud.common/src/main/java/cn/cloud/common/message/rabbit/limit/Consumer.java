package cn.cloud.common.message.rabbit.limit;

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

		com.rabbitmq.client.Consumer consumer = new LimitConsumer(channel);
		String exChangeName = "qos-ex";
		String routingKey = "qos.*";
		
		String queueName = "test_qos";

		channel.exchangeDeclare(exChangeName, "topic", true);
		/*
		 * queue:声明的队列名称
           durable：是否持久化，是否将队列持久化到mnesia数据库中，有专门的表保存我们的队列声明。

            exclusive：排外，①当前定义的队列是connection的channel是共享的，其他的connection是访问不到的。②当connection关闭的时候，队列将被删除。
            autoDelete：自动删除，当最后一个consumer（消费者）断开之后，队列将自动删除。
            arguments：参数是rabbitmq的一个扩展，功能非常强大，基本是AMPQ中没有的。
		 * 
		 */
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exChangeName, routingKey);
		//限流  关闭 autoAck    false
		channel.basicQos(0, 1, false);
		channel.basicConsume(queueName, false, consumer);
		
		
	}
	
	
	public static void testACK(Channel channel) throws IOException, TimeoutException {

		com.rabbitmq.client.Consumer consumer = new AckConsumer(channel);
		String exChangeName = "ack-ex";
		String routingKey = "ack.*";
		
		String queueName = "test_ack";

		channel.exchangeDeclare(exChangeName, "topic", true);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exChangeName, routingKey);
		//限流  关闭 autoAck    false
		//channel.basicQos(0, 1, false);
		// 手动签收 要关闭 autoAck  机制
		channel.basicConsume(queueName, false, consumer);
		
		
	}

	
	public static void main(String[] args) throws IOException, TimeoutException {
		//confirmReceive(getChannel());
		testACK(getChannel());
	}

}
