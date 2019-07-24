package cn.cloud.Feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-logistics",fallback = SchedualLogisticsImpl.class)
public interface SchedualLogistics {
	
	@RequestMapping(value = "/logistics/getLogistics" ,method = RequestMethod.GET)
	String sayHiLogistics(@RequestParam("name") String name);
	
}
