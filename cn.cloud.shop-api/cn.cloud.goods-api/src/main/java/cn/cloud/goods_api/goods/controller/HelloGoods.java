package cn.cloud.goods_api.goods.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class HelloGoods {
	@Value("${server.port}")
	private String port;
	
	
	@RequestMapping("/getGoods")
	public String getGoods(@RequestParam String name) {
		return name + " from  "+ port;
	}

}
