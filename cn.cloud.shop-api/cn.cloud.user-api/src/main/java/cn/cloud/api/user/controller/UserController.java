package cn.cloud.api.user.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.api.user.entity.SysUser;
import cn.cloud.api.user.service.SysUserService;
import cn.cloud.common.model.User;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Value("${server.port}")
	private String port;
	
	@Autowired
	private SysUserService SysUserService;
	
	@RequestMapping("/getuser")
	public String getUser(@RequestParam(value = "name" ,defaultValue = "dream")String name) {
		User user = new User();
		user.setAge(30);
		user.setPerName(name);
		user.setSex("1");
		return user.getPerName()+ " from  "+ port;
	}
	@GetMapping("/test")
	public String testMul(List<SysUser> userList) {
		List<TransactionStatus> transactionStatuses = (List<TransactionStatus>) Collections.synchronizedCollection(
				new ArrayList<TransactionStatus>());
		String msg = "failed";
		try {
			SysUserService.mulThreadInsert(userList,transactionStatuses);
			msg  = "success";

		} catch (Exception e) {
			if (!transactionStatuses.isEmpty()) {
				for (TransactionStatus transactionStatus : transactionStatuses) {
					transactionStatus.setRollbackOnly();
				}
			}
		}
		return msg;
	}

}
