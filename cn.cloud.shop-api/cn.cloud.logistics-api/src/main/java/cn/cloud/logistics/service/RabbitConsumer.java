package cn.cloud.logistics.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

import cn.cloud.common.model.Order;

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
		System.err.println(" 消费端信息  ： " +message.getPayload());
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		channel.basicAck(deliveryTag, false);
	}
	
	
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "${}",durable = "true"),
			exchange = @Exchange( value ="exchange-1",
									durable = "true",
									type = "topic",
									ignoreDeclarationExceptions = "true"),
			key = "springboot.*"
			)
	)
	@RabbitHandler
	public void onOrderMessage(@Payload Order order,Channel channel,
			@Headers Map<String, Object> headers) throws IOException {
		System.err.println(" 消费端 order "  + order.getId());
		Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
		channel.basicAck(deliveryTag, false);
	}

}
