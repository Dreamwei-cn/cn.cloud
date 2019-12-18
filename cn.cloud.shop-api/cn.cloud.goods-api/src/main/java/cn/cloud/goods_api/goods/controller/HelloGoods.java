package cn.cloud.goods_api.goods.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.goods_api.goods.entity.GoodInfo;
import cn.cloud.goods_api.goods.service.GoodsServiceJta;

@RestController
@RequestMapping("/goods")
public class HelloGoods {
	@Value("${server.port}")
	private String port;
	
	@Autowired
	private GoodsServiceJta goodsServiceJta;
	@RequestMapping("/getGoods")
	public String getGoods(@RequestParam String name) {
		return name + " from  "+ port;
	}
	@GetMapping("/create")
	public String testSaveOrUpdate(String msg) {
		GoodInfo goodInfo = new GoodInfo();
		goodInfo.setName(msg);
		goodInfo.setCreatedate(new Date());
		goodInfo.setCreator("test");
		goodInfo.setEnabled("1");
		int num = goodsServiceJta.saveOrUpdate(goodInfo);
		
		return (" 成功條數" + num);		
	}

}
