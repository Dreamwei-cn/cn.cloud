package cn.cloud.logistics.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logistics")
public class HiController {
	
	@Value("${server.port}")
	private String port;
	
	@RequestMapping("/getLogistics")
	public String getLogistics(@RequestParam String name) {
		
		
		return name + " come from " + port;
	}
	

}
