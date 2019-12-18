package cn.cloud.goods_api.goods.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import cn.cloud.goods_api.goods.entity.GoodInfo;
import cn.cloud.goods_api.goods.service.GoodsServiceJta;

public class CustomerGoodsService {
	
	@Autowired
	private GoodsServiceJta goodsServiceJta;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(CustomerGoodsService.class);

	@JmsListener(destination = "customer:msg:jta")
	public void creatBylistener(String msg) {
		log.info("CustomerGoodsService  in  annotation  by listener create goods");
		GoodInfo goodInfo = new GoodInfo();
		goodInfo.setName("jtaGoods");
		goodInfo.setCreator("listener");
		goodsServiceJta.saveOrUpdate(goodInfo);
		jmsTemplate.convertAndSend("customer:goods:replay",goodInfo.getName());
		
	}
	@Transactional
	@JmsListener(destination = "customer:goods:replay")
	public void showMessage(String msg) {
		log.info(" customer:goods:replay 监听到消息 " + msg);
		System.out.println(" customer:goods:replay 监听到消息 " + msg);

	}

}
