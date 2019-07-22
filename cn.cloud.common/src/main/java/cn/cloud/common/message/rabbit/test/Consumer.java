package cn.cloud.common.message.rabbit.test;



import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;



public class Consumer {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();		
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		
		Connection connection =  connectionFactory.newConnection();

		Channel channel = connection.createChannel();
		String queue = "test01";
		
		
		channel.queueDeclare(queue, true, false, false, null);
		 //每次从队列获取的数量
		com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
			 @Override
	            public void handleDelivery(String consumerTag, Envelope envelope,
	                                       AMQP.BasicProperties properties, byte[] body)
	                    throws IOException {
	                String message = new String(body, "UTF-8");
	                System.out.println("Customer Received '" + message + "'" +  consumerTag);
	           
	            }
		};
		channel.basicConsume(queue, true, consumer);


	}

}
