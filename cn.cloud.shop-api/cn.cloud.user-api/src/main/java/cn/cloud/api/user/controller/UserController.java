package cn.cloud.api.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.common.model.User;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Value("${server.port}")
	private String port;
	@RequestMapping("/getuser")
	public String getUser(@RequestParam(value = "name" ,defaultValue = "dream")String name) {
		User user = new User();
		user.setAge(30);
		user.setPerName(name);
		user.setSex("1");
		return user.getPerName()+ " from  "+ port;
	}

}
