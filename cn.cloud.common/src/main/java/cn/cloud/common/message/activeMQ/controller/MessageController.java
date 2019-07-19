package cn.cloud.common.message.activeMQ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.common.message.activeMQ.service.Producer;


@RestController
public class MessageController {
	
	@Autowired
	private Producer producer;



    @GetMapping("jms/queue")
    public void jmsQueueTemplate(@RequestParam String value) {
        
        producer.sendQueueMsg("myqueue", value);
        System.out.println(value+"  >>>>>>>>myqueue");
    }

    @GetMapping("jms/topic")
    public void jmsTopicTemplate(@RequestParam String value) {
        // 可以将以下步骤封装进service 层, 并暴露出一个 destinationName 和 message 出来
        
        producer.sendTopicMsg("mytopic", value);
        System.out.println(value+"  >>>>>>>>>>>mytopic");
    }


}
