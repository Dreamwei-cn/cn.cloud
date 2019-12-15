package cn.cloud.user_api.rabbit;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.sound.midi.MidiDevice.Info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import cn.cloud.user_api.user.entity.SysUser;

/**
 * @author Dream
 * @apiNote it is a class for sending rabbitMq message
 * {@docRoot  The method with name   sendMessage   is to send  a message of  Message Class;  
 *    The method with name   sendUserObjectMessage   is to send  a message of  SysUser Class;     }  
 *
 */
@Service
public class RabbitProducer {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbit:send:exchange}")
	private String sendExchange;
	
	@Value("${rabbit:send:routingKey:msg}")
	private String sendRoutingKeyMsg;
	
	@Value("${rabbit:send:routingKey:obj}")
	private String sendRoutingKeyObj;
	
	
	@Value("${user.delay.exchange}")
	private String delayExchangeName;
	
	@Value("${user.delay.key}")
	private String delayKey;
	
	
	private static final Logger log = LoggerFactory.getLogger(RabbitProducer.class);

	final ConfirmCallback confirmCallback = new ConfirmCallback() {
		
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			if (ack) {
				log.info("confirmCallback>>> 消息: {} 已经被确认！" ,correlationData.getId() );
			}else {
				log.info("confirmCallback>>> 消息: " + correlationData.getId() + " 签收失败！原因是 ： "  + cause );
			}
		}
	};
	final ConfirmCallback userConfirmCallback = (CorrelationData correlationData, boolean ack, String cause) ->{
		if (ack) {
			log.info("userConfirmCallback 消息: {} 已经被确认！" ,correlationData.getId() );
		}else {
			log.info("userConfirmCallback 消息: %s 签收失败！原因是 ： %s" , correlationData.getId(),cause );
		}
	};
	
	final ReturnCallback returnCallback = new ReturnCallback() {
		
		@Override
		public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
				String exchange, String routingKey) {
			log.info("returnCallback   return   exchange " + exchange + " , routingkey : " + routingKey);
			log.info("     replyCode " + replyCode + " , replyText : " + replyText);
			
		}
	};
	
	
	
	public void sendMessage(Object msg,Map<String, Object> properties) {
		Assert.notNull(msg,"消息不能为空");
		Assert.notNull(properties,"消息消息属性不能为空");
		MessageHeaders messageHeaders = new MessageHeaders(properties);
		Message<?> message = MessageBuilder.createMessage(msg, messageHeaders);
		rabbitTemplate.setConfirmCallback(userConfirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
		CorrelationData correlationData = new CorrelationData();
		correlationData.setId(UUID.randomUUID().toString());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
		log.info("消息发送时间: "+formatter.format(LocalDateTime.now()));
		log.info("correlationData.getId >>>>>>>>>>>>>>{}",correlationData.getId());
		rabbitTemplate.convertAndSend(sendExchange,sendRoutingKeyMsg,message,correlationData);
	}

	
	public void sendUserObjectMessage(SysUser sysUser) {
		Assert.notNull(sysUser,"消息体不能为空！");
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
		
		CorrelationData correlationData = new CorrelationData();
		correlationData.setId(UUID.randomUUID().toString());
		log.info("correlationData.getId >>>>>>>>>>>>>>{}",correlationData.getId());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
		log.info("消息发送时间: "+formatter.format(LocalDateTime.now()));
		rabbitTemplate.convertAndSend(sendExchange,sendRoutingKeyObj,sysUser,correlationData);
	}
	
	public void sendDelayMessage(Object msg) {
		Assert.notNull(msg,"消息体不能为空！");
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
		CorrelationData correlationData = new CorrelationData();
		correlationData.setId(UUID.randomUUID().toString());
		log.info("correlationData.getId >>>>>>>>>>>>>>{}",correlationData.getId());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
		log.info("延时消息发送时间: "+formatter.format(LocalDateTime.now()));
		
		rabbitTemplate.convertAndSend(delayExchangeName, delayKey, msg, new MessagePostProcessor() {
			
			@Override
			public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message)
					throws AmqpException {
				message.getMessageProperties().setHeader("x-delay", 5000);
				return message;
			}
		},correlationData);
	}
	
	 public void sendMsg(String routingKey,String msg) {

	        CorrelationData correlationData = new CorrelationData();
			correlationData.setId(UUID.randomUUID().toString());
			log.info("correlationData.getId >>>>>>>>>>>>>>{}",correlationData.getId());
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
			log.info("延时消息发送时间: "+formatter.format(LocalDateTime.now()));
	        rabbitTemplate.convertAndSend("test_exchange", routingKey, msg, new MessagePostProcessor() {
				
				@Override
				public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message)
						throws AmqpException {
					message.getMessageProperties().setHeader("x-delay", 5000);
					return message;
				}
	        },correlationData);
	    }

}
