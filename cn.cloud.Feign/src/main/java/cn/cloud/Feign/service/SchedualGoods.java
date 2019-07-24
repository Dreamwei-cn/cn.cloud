package cn.cloud.Feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-goods",fallback = SchedualGoodsImpll.class)
public interface SchedualGoods {
	
	@RequestMapping(value = "/goods/getGoods",method = RequestMethod.GET)
	String sayHiFromClientOne(@RequestParam("name") String name);

}
