package cn.cloud.user_api.rabbit;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Argument;
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
	
	
	private static final Logger log = LoggerFactory.getLogger(RabbitConsumer.class);


	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(value = "${rabbit:send:exchange}"
									, type = "topic"
									, durable = "true"
									, ignoreDeclarationExceptions = "true")
			, value = @Queue(value = "${rabbit.queue}", durable = "true")
			, key = "${rabbit:send:routingKey:msg}" )
	)
	@RabbitHandler
	public void onMessage(Message<?> msg, Channel channel) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
		log.info("消息接受 时间<<<<<<<<<<<<<<<< "+formatter.format(LocalDateTime.now()));

		log.info("onMessage>>>>>>> id >>>>>>>>>{}",msg.getHeaders().getId());
		Long deliveryTag = (Long) msg.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		log.info(deliveryTag +"：  消息 的deliveryTag ");
		try {
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			log.debug(RabbitConsumer.class.getName() + " 消息监听 错误 " );
			log.debug(e.getMessage());
		}
	}
	
	
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(value = "${rabbit:send:exchange}"
									, type = "topic"
									, durable = "true"
									, ignoreDeclarationExceptions = "true")
			, value = @Queue(value = "${rabbit.queue.user}", durable = "true" )
			, key = "${rabbit:send:routingKey:obj}" )
	)
	@RabbitHandler
	public void onMessageBySysUser(Message<?> msg, Channel channel) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH：mm：ss");
		log.info("消息接受 时间<<<<<<<<<<<<<<<< "+formatter.format(LocalDateTime.now()));
		log.info("onMessageBySysUser>>>>>>> id >>>>>>>>>{}",msg.getHeaders().getId());
		Long deliveryTag = (Long) msg.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		log.info(deliveryTag +"：  消息的 deliveryTag ");
		try {
			
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			log.debug(RabbitConsumer.class.getName() + " 消息监听 错误 " );
			log.debug(e.getMessage());
		}
	}
	
	@RabbitListener(bindings = 	@QueueBinding(exchange = @Exchange(value = "${user.delay.exchange}"
																		, durable = "true", type = "x-delayed-message"
																		, arguments = {@Argument(name = "x-delayed-type", value = "direct")}
																		, ignoreDeclarationExceptions = "true")
												, value = @Queue(value = "${user.delay.queue}", durable = "true")
												, key = "${user.delay.key}")
	)
	@RabbitHandler
	public void onMessageDelayMsg(Message<?> msg, Channel channel) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH：mm：ss");
		log.info("延时消息接受 时间<<<<<<<<<<<<<<<< "+formatter.format(LocalDateTime.now()));
		log.info("onMessageDelayMsg>>>>>>> id >>>>>>>>>{}",msg.getHeaders().getId());
		Long deliveryTag = (Long) msg.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		log.info(deliveryTag +"：  延时消息的 deliveryTag ");
		try {			
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			log.debug(RabbitConsumer.class.getName() + " 消息监听 错误 " );
			log.debug(e.getMessage());
		}
	}
	
	@RabbitListener(queues = "test_queue_1")
	public void delayReceive(Message<?> msg, Channel channel) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH：mm：ss");
		log.info("延时消息接受 时间<<<<<<<<<<<<<<<< "+formatter.format(LocalDateTime.now()));
		log.info("onMessageDelayMsg>>>>>>> id >>>>>>>>>{}",msg.getHeaders().getId());
		Long deliveryTag = (Long) msg.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		log.info(deliveryTag +"：  延时消息的 deliveryTag ");
		try {			
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			log.debug(RabbitConsumer.class.getName() + " 消息监听 错误 " );
			log.debug(e.getMessage());
		}
	}
	


}
