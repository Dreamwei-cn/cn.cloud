package cn.cloud.Feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-user")
public interface SchedualSercieHi {
	
	@RequestMapping(value = "user/getuser",method = RequestMethod.GET)
	String sayHiFromClientOne(@RequestParam("name") String name);

}
