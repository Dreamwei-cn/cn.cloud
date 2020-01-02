package cn.cloud.goods_api.goods.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cloud.goods_api.goods.entity.GoodInfo;



@Service
public class MessageService {
	
	@Autowired
	private JmsMessagingTemplate jtTemplate;
	
	
	@Autowired
	private GoodInfoService goodInfoService;
	
	private static final Logger log = LoggerFactory.getLogger(MessageService.class);

	@Transactional
	public void sendMessage(String msg) {
		jtTemplate.convertAndSend("demo:jta", "jms消息 ： " + msg  );
		
		log.info(" jms  >>>>>>>>>>>> 发送消息 >>>" + msg);

	}
	
	@JmsListener( destination = "demo:jta")
	public void onMessage(String msg) {
		log.info(" demo:jta  《《《《《《《《接收 " + msg);
		GoodInfo goodInfo = new GoodInfo();
		goodInfo.setName(msg);
		goodInfoService.addGoodInfo(goodInfo);
		simpleException(msg);
		jtTemplate.convertAndSend("demo:jta:replay", " 成功创建 消息 " + msg);
		
		log.info(" demo:jta  >>>>>>>>>>>> 发送消息 >>  >" + msg);
	}
	@JmsListener(destination = "demo:jta:replay")
	public void onMessageReplay(String msg) {
		log.info("demo:jta:replay>>>>>>>>>>>>>>>>  接收   消息 " + msg);
	}
	
	private void simpleException(String msg) {
		if (msg.contains("error")) {
			throw new  RuntimeException(" 产生错误信息" + msg);
		}
	}

}
