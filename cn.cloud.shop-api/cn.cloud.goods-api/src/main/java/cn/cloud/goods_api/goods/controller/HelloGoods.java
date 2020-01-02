package cn.cloud.goods_api.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.goods_api.goods.service.MessageService;


@RestController
@RequestMapping("/goods")
public class HelloGoods {
	@Value("${server.port}")
	private String port;
	
	
	@Autowired
	private MessageService messageService;

	@RequestMapping("/getGoods")
	public String getGoods(@RequestParam String name) {
		return name + " from  "+ port;
	}
	
	@Transactional
	@RequestMapping("/send")
	public String send(String msg) {
		messageService.sendMessage(msg);
		
		return " 发送成功   " + msg;
	}


}
