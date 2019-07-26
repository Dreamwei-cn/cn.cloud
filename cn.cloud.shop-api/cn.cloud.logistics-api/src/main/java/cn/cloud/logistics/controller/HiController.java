package cn.cloud.logistics.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//这里面的属性有可能会更新的，git中的配置中心变化的话就要刷新，没有这个注解内，配置就不能及时更新
@RefreshScope
@RequestMapping("/logistics")
public class HiController {
	
	@Value("${server.port}")
	private String port;
	
	@RequestMapping("/getLogistics")
	public String getLogistics(@RequestParam String name) {
		
		
		return name + " come from " + port;
	}
	@Value("${namelogis}")
	private String name;
	
	@RequestMapping("/hi")
	public String hiConfig() {
		return name;
	}

}
