package cn.cloud.user_api.testUser.testControler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.user.mapper.SysUserMapper;
import cn.cloud.user_api.user.service.SysUserService;
import cn.cloud.user_api.user.service.SysUserServiceMul;
import cn.cloud.user_api.user.service.impl.SysUserServiceMulImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserController {
	
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired 
	SysUserServiceMul sysUserServiceMul;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Test
	public void testSysUserServiceMul() {
		List<SysUser> list = new ArrayList<>();
		
		String name = "thread";
		for (int i = 0; i < 300; i++) {
			SysUser user = new SysUser();
			user.setName(name+ i);
			user.setLoginname(name+ i);
			list.add(user);
			
		}
		
		System.out.println(list.size() + ">>  list 大小  ");
		List<TransactionStatus> transactionStatuses = Collections.synchronizedList(
				new ArrayList<TransactionStatus>());
		String msg = "failed";
		try {
			sysUserService.mulThreadInsert(list,transactionStatuses,transactionManager);
			msg  = "success";
			System.out.println(transactionStatuses.size() + "  status  大小");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			if (!transactionStatuses.isEmpty()) {
				for (int i = transactionStatuses.size(); i >0; i--) {
					transactionManager.rollback(transactionStatuses.get(i-1));
				}
			}
			System.out.println(">>>>>> catch  " + msg);
		}
		System.out.println(">>>>>>" + msg);
		if (!transactionStatuses.isEmpty()) {
			if (!transactionStatuses.isEmpty()) {
				for (int i = transactionStatuses.size(); i >0; i--) {
					transactionManager.commit(transactionStatuses.get(i-1));
					System.out.println(transactionStatuses.get(i-1).isCompleted() + "  是否完成");
				}
			}
		}
		
		
		System.out.println(">>>>>>" + msg);
		
	}
	@Test
	public void testget() {
		SysUser user = sysUserService.getSYsUserById(10L);
		System.out.println(user.getName());
	}
	@Test
	public void testList() {
		List<SysUser> list = new ArrayList<>();
		
		String name = "thread";
		for (int i = 0; i < 300; i++) {
			SysUser user = new SysUser();
			user.setName(name+ i);
			user.setLoginname(name+ i);
			list.add(user);
			
		}
		List<SysUser> users = list.subList(0, 10);
		sysUserMapper.insertMul(users);
		
	}
	
	@Test
	public void testNO() {
		List<SysUser> list = new ArrayList<>();
		
		String name = "NOthread";
		for (int i = 0; i < 300; i++) {
			SysUser user = new SysUser();
			user.setName(name+ i);
			user.setLoginname(name+ i);
			list.add(user);
			
		}
		
		System.out.println(list.size() + ">>  list 大小");
		try {
			sysUserService.mulThreadInsertNOTransaction(list);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出现异常");
		}
	}
	
	@Test
	public void testONTransaction() {
		List<SysUser> list = new ArrayList<>();
		
		String name = "ON_thread_";
		for (int i = 0; i < 300; i++) {
			SysUser user = new SysUser();
			user.setName(name+ i);
			user.setLoginname(name+ i);
			list.add(user);
			
		}
		
		System.out.println(list.size() + ">>  list 大小");
		List<TransactionStatus> transactionStatuses = new ArrayList<>();
		try {
			sysUserService.mulThreadInsert(list, transactionStatuses, transactionManager);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出现异常");
		}
	}
	
	@Test
	public void testSysUserMul() {
		List<SysUser> list = new ArrayList<>();
		
		String name = "ON_thread_";
		for (int i = 0; i < 300; i++) {
			SysUser user = new SysUser();
			user.setName(name+ i);
			user.setLoginname(name+ i);
			list.add(user);
			
		}
		
		System.out.println(list.size() + ">>  list 大小");
		try {
			sysUserServiceMul.mulTHreadInsertON(list, transactionManager);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出现异常");
		}
	}
	
	
}
