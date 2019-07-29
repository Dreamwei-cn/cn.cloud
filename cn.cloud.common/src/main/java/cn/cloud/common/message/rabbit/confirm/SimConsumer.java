package cn.cloud.common.message.rabbit.confirm;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class SimConsumer extends DefaultConsumer {

	public SimConsumer(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
			throws IOException {

		 String message = new String(body, "UTF-8");

         System.out.println("Customer Received ' " + message + " ' " +  consumerTag);
	}
	

}
