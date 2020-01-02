package cn.cloud.shop.tickets_api.tickets.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import cn.cloud.shop.tickets_api.tickets.kafka.common.MessageEntity;

@Component
public class SimpleConsumer {

	
	private static final Logger log = LoggerFactory.getLogger(SimpleConsumer.class);

	@KafkaListener(topics = "${kafka.topic.default}", containerFactory = "kafkaListenerContainerFactory")
	public void receive(MessageEntity messageEntity) {
		log.info("message");

	}
}
