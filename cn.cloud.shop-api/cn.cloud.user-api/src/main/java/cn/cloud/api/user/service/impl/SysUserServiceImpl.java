package cn.cloud.api.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.transaction.TransactionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.cloud.api.user.entity.SysUser;
import cn.cloud.api.user.mapper.SysUserMapper;
import cn.cloud.api.user.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	private static ExecutorService executor  = Executors.newFixedThreadPool(4);
	
	public SysUser getSYsUserById(Long id) {
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
		return sysUser;
	}

	/**
	 *  多线程插入数据
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Override
	@Transactional
	public Integer mulThreadInsert(List<SysUser> userList,List<TransactionStatus> transactionStatuses) throws Exception {
		SysUser userMain = new SysUser();
		userMain.setName("Main");
		userMain.setLoginname("Main");			
		sysUserMapper.insert(userMain);
		int size = 300;
		List<FutureTask<Integer>> results = new ArrayList<>();
		List<SysUser> users1 = userList.subList(0, 100);
		List<SysUser> users2 = userList.subList(100, 200);
		
		List<SysUser> users3 = userList.subList(200, 300);
		FutureTask<Integer> task1 = new FutureTask<>( new SysUserCallable(users1, transactionStatuses, 1, transactionManager));
		FutureTask<Integer> task2 = new FutureTask<>( new SysUserCallable(users2, transactionStatuses, 2, transactionManager));
		FutureTask<Integer> task3 = new FutureTask<>( new SysUserCallable(users3, transactionStatuses, 17, transactionManager));
		results.add(task1);
		results.add(task2);
		results.add(task3);
		
		executor.submit(task1);
		executor.submit(task2);
		executor.submit(task3);
		int sum = 0;
		for (FutureTask<Integer> result : results) {
			sum= sum + result.get();
		}
		if (size != sum ) {
			for (TransactionStatus status : transactionStatuses) {
				status.setRollbackOnly();
			}
			throw new RuntimeException("子线程插入异常");
		}
		
		Integer.parseInt("12ds");
		return sum;
	}
	
	
	
	/**
	 * @author Dream
	 * 
	 * 线程操作类
	 *
	 */
	class SysUserCallable implements Callable<Integer>{

		private List<SysUser> users;
		private List<TransactionStatus> transactionStatuses;
		private int id;
		private  PlatformTransactionManager transactionManager;
		
		public SysUserCallable(List<SysUser> users,List<TransactionStatus> transactionStatuses,
				int id,PlatformTransactionManager transactionManager) {
			this.users = users;
			this.transactionStatuses = transactionStatuses;
			this.id = id;
			this.transactionManager = transactionManager;
		}
		@Override
		public Integer call(){
			int num = 0;
			try {
				
				DefaultTransactionDefinition defaultTransactionDefinition =
						new DefaultTransactionDefinition();
				
				defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
				
				TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);
				
				transactionStatuses.add(status);
				if (id == 17) {
					Integer.parseInt( "oos");
				}
				num = sysUserMapper.insertMul(users);
			} catch (Exception e) {
				return num;
			}
			return num;
			
		}
		
	}
	
	
}
