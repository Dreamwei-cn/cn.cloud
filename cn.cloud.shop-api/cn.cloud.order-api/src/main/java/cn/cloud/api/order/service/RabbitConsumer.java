package cn.cloud.api.order.service;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

@Service
public class RabbitConsumer {
	
	
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "queue-1",durable = "true"),
			exchange = @Exchange( value ="exchange-1",
									durable = "true",
									type = "topic",
									ignoreDeclarationExceptions = "true"),
			key = "springboot.*"
			)
	)
	@RabbitHandler
	public void onMessage(Message<?> message,Channel channel) throws IOException {
		System.out.println(" 消费端信息 1  ： " +message.getPayload());
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		channel.basicAck(deliveryTag, false);
	}
	
	
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "queue-1",durable = "true"),
			exchange = @Exchange( value ="exchange-2",
									durable = "true",
									type = "topic",
									ignoreDeclarationExceptions = "true"),
			key = "springboot.*"
			)
	)
	@RabbitHandler
	public void onMessageOrder(Message<?> message,Channel channel) throws IOException {
		System.out.println(" 消费端信息2   ： " +message.getPayload());
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		channel.basicAck(deliveryTag, false);
	}

}
