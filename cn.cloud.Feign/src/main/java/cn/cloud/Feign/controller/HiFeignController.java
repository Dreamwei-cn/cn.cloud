package cn.cloud.Feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.Feign.service.SchedualSercieHi;

@RestController
public class HiFeignController {

	@Autowired
	private SchedualSercieHi schedualSercieHi;
	@RequestMapping(value = "/hi",method = RequestMethod.GET)
	public String sayHi(@RequestParam String name) {
		return schedualSercieHi.sayHiFromClientOne(name);
	}
	@RequestMapping(value = "/hi",method = RequestMethod.GET)
	public String sayHiOrder(@RequestParam String name) {
		return schedualSercieHi.sayHiFromClientOne(name);
	}
}
