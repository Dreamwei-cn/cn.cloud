package cn.cloud.common.message.rabbit.dlx;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Dream
 * 测试 死信队列
 */
public class Producer {
	
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
		String routingKey = "dlx.save";
		String msg = "Helle  this is  test  dlx";
		AMQP.BasicProperties properties = new AMQP.BasicProperties()
				.builder()
				.deliveryMode(2)
				.contentEncoding("UTF-8")
				.expiration("2000")
				.build();
		
		channel.basicPublish(exChange, routingKey, properties, msg.getBytes());
	}
	public static void main(String[] args) throws IOException, TimeoutException {
		testDlx(getChannle());
		
	}

}
