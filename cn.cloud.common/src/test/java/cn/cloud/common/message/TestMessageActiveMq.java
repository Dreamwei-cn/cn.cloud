package cn.cloud.common.message;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.cloud.common.message.activeMQ.service.ConsumerQueue;
import cn.cloud.common.message.activeMQ.service.ConsumerTopic;
import cn.cloud.common.message.activeMQ.service.Producer;
import cn.cloud.common.message.activeMQ.service.ProducerJms;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMessageActiveMq {
	
	@Resource
	private ConsumerQueue consumerQueue;
	
	@Resource
	private ConsumerTopic consumerTopic;
	
	@Resource
	private Producer producer;
	
	@Resource
	private ProducerJms producerJms;
	@Test
	public void testMsg() {
		System.out.println("开始发送消息");
		
		for (int i = 0; i < 50; i++) {
			producerJms.sendQueueMsg("myqueue", " queue 消息"+ " "+ i);
		}
		
//		for (int i = 0; i < 1; i++) {
//			producer.sendTopicMsg("mytopic", " topic 消息"+ " "+ i);
//		}
	}

}
