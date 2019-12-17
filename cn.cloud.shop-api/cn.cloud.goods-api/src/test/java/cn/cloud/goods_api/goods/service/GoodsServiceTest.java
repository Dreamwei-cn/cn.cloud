package cn.cloud.goods_api.goods.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import cn.cloud.goods_api.goods.entity.GoodInfo;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsServiceTest {

	@Autowired
	GoodsServiceJta goodsServiceJta;
	
	@Autowired
	JmsTemplate JmsTemplate;
	@Test
	public void testSaveOrUpdate() {
		GoodInfo goodInfo = new GoodInfo();
		goodInfo.setName("測試商品2");
		goodInfo.setCreatedate(new Date());
		goodInfo.setCreator("test");
		goodInfo.setEnabled("1");
		int num = goodsServiceJta.saveOrUpdate(goodInfo);
		
		System.out.println(" 成功條數" + num);		
	}
	@Test
	public void testTwoByCode() {
		GoodInfo goodInfo = new GoodInfo();
		goodInfo.setName("测试两次提交商品");
		goodInfo.setCreatedate(new Date());
		goodInfo.setCreator("test");
		int num = goodsServiceJta.saveOrUpdateByCode(goodInfo);
		System.out.println(" 成功條數" + num);
	}
	
	@Test
	public void testSendMessage() {
		JmsTemplate.convertAndSend("customer:goods:replay"," 发送消息测试");
	}
}
