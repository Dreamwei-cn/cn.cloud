package cn.cloud.user_api.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.activemq.util.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.user.mapper.SysUserMapper;
import cn.cloud.user_api.user.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
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
	public Integer mulThreadInsert(List<SysUser> userList,List<TransactionStatus> transactionStatuses,
			PlatformTransactionManager transactionManager) throws Exception {
		
		DefaultTransactionDefinition defMain = new DefaultTransactionDefinition();
		TransactionStatus statusMain = transactionManager.getTransaction(defMain);
		transactionStatuses.add(0, statusMain);
		int sum = 0;

			SysUser userMain = new SysUser();
			userMain.setName("MainON");
			userMain.setLoginname("MainON");			
			sysUserMapper.insert(userMain);
			int size = 300;
			List<FutureTask<Integer>> results = new ArrayList<>();
			List<SysUser> users1 = userList.subList(0, 100);
			List<SysUser> users2 = userList.subList(100, 200);		
			List<SysUser> users3 = userList.subList(200, 300);			
			FutureTask<Integer> task1 = new FutureTask<>( new SysUserCallable(users1, transactionStatuses, 1, transactionManager));
			FutureTask<Integer> task2 = new FutureTask<>( new SysUserCallable(users2, transactionStatuses, 2, transactionManager));
			FutureTask<Integer> task3 = new FutureTask<>( new SysUserCallable(users3, transactionStatuses, 3, transactionManager));			
//			FutureTask<Integer> task1 = new FutureTask<>(new SysUserNOTransaction(1, users1));
//			FutureTask<Integer> task2 = new FutureTask<>(new SysUserNOTransaction(2, users2));
//			FutureTask<Integer> task3 = new FutureTask<>(new SysUserNOTransaction(1, users3));					
			results.add(task1);
			results.add(task2);
			results.add(task3);			
			executor.submit(task1);
			executor.submit(task2);
			executor.submit(task3);			
			Thread.sleep(3000);

			for (;;) {
				if (task1.isDone() && task2.isDone() && task3.isDone()) {
					break;
				}
			}
			for (FutureTask<Integer> result : results) {				
				System.out.println("  task : num  " + result.get());
				sum= sum + result.get();				
			}			
			System.out.println(" 子线程插入 个数： " + sum  );
			if (size != sum ) {
				transactionManager.rollback(transactionStatuses.get(3));
				transactionManager.rollback(transactionStatuses.get(2));
				transactionManager.rollback(transactionStatuses.get(1));
				throw new RuntimeException("子线程插入异常");
			}
//			Integer.parseInt("12ds");
			
			transactionManager.commit(transactionStatuses.get(3));
			transactionManager.commit(transactionStatuses.get(2));
			transactionManager.commit(transactionStatuses.get(1));
			transactionManager.commit(transactionStatuses.get(0));
			
//		Integer.parseInt("12ds");
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
				if (id == 3) {
					Integer.parseInt( "oos");
				}
				num = sysUserMapper.insertMul(users);
				
			} catch (Exception e) {
				return num;
			}
			return num;
			
		}
		
	}
	class SysUserNOTransaction implements Callable<Integer>{

		private List<SysUser> list;
		private int id;
		
		public  SysUserNOTransaction( int id , List<SysUser> list ) {
			this.id = id;
			this.list = list;
		}
		@Override
		public Integer call() throws Exception {
			
			int num = 0;
			try {
				if (id==3) {
					throw new RuntimeException("  子线程 报错  ");
				}
				num = sysUserMapper.insertMul(list);
			} catch (Exception e) {
				return num;
			}
			return num;
		}
		
	}
	@Override
	@Transactional
	public Integer mulThreadInsertNOTransaction(List<SysUser> userList) throws Exception {
		SysUser userMain = new SysUser();
		userMain.setName("Main12NO");
		userMain.setLoginname("Main11");			
		sysUserMapper.insert(userMain);
		int size = 300;
		List<FutureTask<Integer>> results = new ArrayList<>();
		List<SysUser> users1 = userList.subList(0, 100);
		List<SysUser> users2 = userList.subList(100, 200);		
		List<SysUser> users3 = userList.subList(200, 300);
		
		FutureTask<Integer> task1 = new FutureTask<>(new SysUserNOTransaction(1, users1));
		FutureTask<Integer> task2 = new FutureTask<>(new SysUserNOTransaction(2, users2));
		FutureTask<Integer> task3 = new FutureTask<>(new SysUserNOTransaction(1, users3));
		results.add(task1);
		results.add(task2);
		results.add(task3);
		
		executor.submit(task1);
		executor.submit(task2);
		executor.submit(task3);
		int sum = 0;
		for (FutureTask<Integer> result : results) {
			
			System.out.println("  task : num  " + result.get());
			sum= sum + result.get();
			
		}
		
		System.out.println(" 子线程插入 个数： " + sum  );
		if (size != sum ) {

			throw new RuntimeException("子线程插入异常");
		}
		
//		Integer.parseInt("12ds");
		return sum;
	}
	
	
	
	
	

	
}
