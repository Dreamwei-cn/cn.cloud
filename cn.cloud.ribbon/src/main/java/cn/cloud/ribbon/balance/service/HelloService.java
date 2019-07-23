package cn.cloud.ribbon.balance.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HelloService {

	@Autowired
	private RestTemplate restTemplate;
	
	public String userService(String name) {
		return restTemplate.getForObject("http://service-user/user/getuser?name="+name, String.class);
	}
	@HystrixCommand(fallbackMethod = "hiError")
	public String goodsService(String name) {
		return restTemplate.getForObject("http://service-goods/goods/getGoods?name="+name, String.class);
	}
	
	public String hiError(String name) {
	    return "hi,"+name+",sorry,error!";
	 }
	
	
}
