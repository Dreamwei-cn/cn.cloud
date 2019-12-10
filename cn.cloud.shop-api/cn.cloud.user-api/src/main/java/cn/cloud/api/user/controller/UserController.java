package cn.cloud.api.user.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jms.Destination;
import javax.transaction.TransactionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;
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
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	private SysUserService SysUserService;
	
	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	
	@RequestMapping("/getuser")
	public String getUser(@RequestParam(value = "name" ,defaultValue = "dream")String name) {
		User user = new User();
		user.setAge(30);
		user.setPerName(name);
		user.setSex("1");
		return user.getPerName()+ " from  "+ port;
	}
	@GetMapping("/test")
	public String testMul(String tyString) {
		
		List<SysUser> list = new ArrayList<>();
		
		String name = "threadtest";
		for (int i = 0; i < 300; i++) {
			SysUser user = new SysUser();
			user.setName(name+ i);
			user.setLoginname(name+ i);
			user.setPersonid(Long.parseLong(""+i));
			list.add(user);
			
		}
		List<TransactionStatus> transactionStatuses = Collections.synchronizedList(
				new ArrayList<TransactionStatus>());
		String msg = "failed";
		try {
			SysUserService.mulThreadInsert(list,transactionStatuses,transactionManager);
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
	
	@RequestMapping("/jms")
	public String jmsTransaction(String msg) {
		jmsTemplate.convertAndSend( msg);
		return "";
	}

}
