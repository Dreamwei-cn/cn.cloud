package cn.cloud.ribbon.balance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.ribbon.balance.service.HelloService;

@RestController
public class HelloController {
	
	@Autowired
	private HelloService hService;
	
	@RequestMapping("/hi")
	public String hi(@RequestParam String name) {
		
		return hService.userService(name);
	}
	
	@RequestMapping("/higoods")
	public String hiGoods(@RequestParam String name) {
		
		return hService.goodsService(name);
	}
	@RequestMapping("/hilogistics")
	public String hiLogistics(@RequestParam String name) {
		
		return hService.logisticsService(name);
	}



}
