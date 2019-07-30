package cn.cloud.common.message.rabbit.limit;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class LimitConsumer extends DefaultConsumer{

	private Channel channel;
	public LimitConsumer(Channel channel) {
		super(channel);
		this.channel = channel;
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
			throws IOException {

		 String message = new String(body, "UTF-8");

         System.out.println("Customer Received ' " + message + " ' " +  consumerTag);
         // 消息应答
         channel.basicAck(envelope.getDeliveryTag(), false);
	}
	
}
