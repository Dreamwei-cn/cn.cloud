package cn.cloud.Feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.Feign.service.SchedualGoods;
import cn.cloud.Feign.service.SchedualLogistics;
import cn.cloud.Feign.service.SchedualSercieHi;

@RestController
@RequestMapping("/hi")
public class HiFeignController {

	@Autowired
	private SchedualSercieHi schedualSercieHi;
	@Autowired
	private  SchedualGoods schedualGoods;
	@Autowired
	private SchedualLogistics schedualLogistics;
	
	@RequestMapping(value = "/user",method = RequestMethod.GET)
	public String sayHi(@RequestParam String name) {
		return schedualSercieHi.sayHiFromClientOne(name);
	}
	@RequestMapping(value = "/goods",method = RequestMethod.GET)
	public String sayHiOrder(@RequestParam String name) {
		return schedualGoods.sayHiFromClientOne(name);
	}
	
	@RequestMapping(value = "/logistics",method = RequestMethod.GET)
	public String sayHiLogistics(@RequestParam String name) {
		return schedualLogistics.sayHiLogistics(name);
	}
}
