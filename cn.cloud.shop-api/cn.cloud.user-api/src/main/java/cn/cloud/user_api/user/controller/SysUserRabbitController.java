package cn.cloud.user_api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.user_api.rabbit.RabbitProducer;
import cn.cloud.user_api.user.service.SysUserService;
import cn.cloud.user_api.user.service.SysUserServiceMul;

@RestController
@RequestMapping("/sysuser")
public class SysUserRabbitController {
	
	@Autowired 
	SysUserService sysUserService;
	
	@Autowired
	SysUserServiceMul sysUserServiceMul;
	
	@Autowired
	private  RabbitProducer rabbitProducer;
	
	@GetMapping("/send")
	public String sendMsg(String msg) {
		
		rabbitProducer.sendDelayMessage(msg);
		return "发送成功";
	}

	@GetMapping("/send/delay")
	public String delay(String msg) {
		
		rabbitProducer.sendMsg("test_queue_1",msg);
		return "发送成功";
	}
}
