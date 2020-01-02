package cn.cloud.shop.tickets_api.tickets.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.cloud.shop.tickets_api.tickets.kafka.common.ErrorCode;
import cn.cloud.shop.tickets_api.tickets.kafka.common.MessageEntity;
import cn.cloud.shop.tickets_api.tickets.kafka.common.Response;
import cn.cloud.shop.tickets_api.tickets.kafka.producer.SimpleProducer;

@RestController
@RequestMapping("/tickets")
public class TicketsController {
	
	@Autowired
	private SimpleProducer producer;
	
	@Value("${kafka.topic.default}")
	private String topic;
	
	
	
	private static final Logger log = LoggerFactory.getLogger(TicketsController.class);

	@RequestMapping("/hello")
	public String hello() {
		return "hello ! This is tickets";
	}
	
	@RequestMapping(value = "/kafka", method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
	public Response sendKafka() {
		return new Response(ErrorCode.SUCCESS, "OK");
	}
	
	@RequestMapping(value = "/send", method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
	public Response sendKafka(@RequestBody MessageEntity messageEntity) {
		try {
			log.info("kafka 的消息    = {}", JSON.toJSON(messageEntity));
			producer.send(topic, messageEntity);
			log.info("kafka 发送消息成功");
			return new Response(ErrorCode.SUCCESS, "kafka 发送消息成功");
		} catch (Exception e) {
			log.error("发送kafka 失败", e);
			return new Response(ErrorCode.EXCEPTION, "kafka 发送消息失败");
		}
	}

}
