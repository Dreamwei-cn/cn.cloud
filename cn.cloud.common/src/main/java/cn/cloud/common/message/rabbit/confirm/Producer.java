package cn.cloud.common.message.rabbit.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Producer {
	
	
	public static Channel getChannel() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		return channel;
	}
	public static void confirmSend(Channel channel) throws IOException, TimeoutException {
		//消息的确认模式
		channel.confirmSelect();
		String exChangeName = " confirm-ex";
		String routingKey = "confirm.save";
		String msg = " 消息确认模式";
		channel.basicPublish(exChangeName, routingKey, null, msg.getBytes());
		System.out.println( " 发送 消息 确认模式");
		channel.addConfirmListener(new ConfirmListener() {
			
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("+++++++++  no ack!  +++++++++");
			}
			
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("+++++++++   ack!  +++++++++");
			}
		});
	}
	
	public static void returnSend(Channel channel) throws IOException {
		String exchangeName = "test_return";
		String routingKey = "return.save";
		String msg = "return  message ";
		channel.addReturnListener(new ReturnListener() {
			
			@Override
			public void handleReturn(int replyCode,
		            String replyText,
		            String exchange,
		            String routingKey,
		            AMQP.BasicProperties properties,
		            byte[] body)
					throws IOException {
				System.err.println(" +++++++ handleReturn  +++++++++++++");
				System.out.println("  replycode : "+ replyCode + "/n replayTex:" + replyText );
			}
		});
		channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());;
		
	}
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
//		confirmSend(channel);
		returnSend(channel);
		
	}


}
