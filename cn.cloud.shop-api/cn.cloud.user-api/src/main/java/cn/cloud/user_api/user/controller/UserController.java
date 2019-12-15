package cn.cloud.user_api.user.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.common.model.User;
import cn.cloud.user_api.message.UserProducer;
import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.user.service.SysUserService;
// 换用 rabbitmq
@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Value("${server.port}")
	private String port;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
//	@Autowired 
//	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@Autowired
	private SysUserService SysUserService;
//	@Autowired
//	private UserProducer userProducer;
	
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
	
//	@GetMapping(value = "/queue/string")
//	public String SendMsg(String msg ) {
//
//		try {
//			userProducer.sendQueueMsg(msg);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			return "failed";
//		}
//		return "success";
//	}
	
//	@GetMapping(value = "/queue/tra")
//	public String transaction(String msg ) {
//		
//		try {
//			userProducer.sendMsgByName("mq:ta", msg);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			return "failed";
//		}
//		return "success";
//	}
//	@GetMapping(value = "/jms/tra")
//	public String jmsTransaction(String msg ) {
//		
//		try {
//			userProducer.sendMsgByName("mq:ta1", msg);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			return "failed";
//		}
//		return "success";
//	}
//	
//	@GetMapping(value = "/tra/msg")
//	public String jmsReceive( ) {
//		String msg = "11";
//		try {
//			msg = jmsMessagingTemplate.receiveAndConvert("transaction", String.class);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			return "failed";
//		}
//		return  msg;
//	}

}
