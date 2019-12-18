package cn.cloud.order_api.order.service;

import java.util.Map;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import cn.cloud.common.model.Order;



@Service
public class RabbitSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	final ConfirmCallback confirmCallback = new  ConfirmCallback() {
		
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			System.out.println("correlationData  : " + correlationData);
			System.out.println("ack :  "+ ack);
			System.out.println(" cause : " + cause);
			if (!ack) {
				System.out.println("  异常处理   ");
			}
		}
	};
	final ReturnCallback returnCallback = new ReturnCallback() {
		
		@Override
		public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
				String exchange, String routingKey) {
			
			System.out.println("   return   exchange " + exchange + " , routingkey : " + routingKey) ;
			
			System.out.println("     replyCode " + replyCode + " , replyText : " + replyText) ;
		}
	};
	public void send(Object msg,Map<String, Object> properties) {
		MessageHeaders mhs = new MessageHeaders(properties);
		Message<?> message = MessageBuilder.createMessage(msg, mhs);
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
		CorrelationData correlationData = new CorrelationData();
		correlationData.setId("123456");// 真实业务全局唯一
		rabbitTemplate.convertAndSend("exchange-1", "springboot.hello", message,correlationData);
	}	
	
	public void sendOrder(Order order) {

		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
		CorrelationData correlationData = new CorrelationData();
		correlationData.setId(order.getId());// 真实业务全局唯一
		rabbitTemplate.convertAndSend("exchange-2", "springboot.hello", order,correlationData);
	}
}
