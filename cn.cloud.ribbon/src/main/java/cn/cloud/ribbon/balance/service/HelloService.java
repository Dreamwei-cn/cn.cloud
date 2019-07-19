package cn.cloud.ribbon.balance.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

	@Autowired
	private RestTemplate restTemplate;
	
	public String userService(String name) {
		return restTemplate.getForObject("http://service-user/user/getuser?name="+name, String.class);
	}
}
