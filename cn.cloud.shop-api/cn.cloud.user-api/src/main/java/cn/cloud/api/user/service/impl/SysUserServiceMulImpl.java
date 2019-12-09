package cn.cloud.api.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.cloud.api.user.entity.SysUser;
import cn.cloud.api.user.mapper.SysUserMapper;
import cn.cloud.api.user.service.SysUserServiceMul;

@Service
public class SysUserServiceMulImpl implements SysUserServiceMul {
	@Autowired
	private SysUserMapper sysUserMapper;
	private static ExecutorService executor  = Executors.newFixedThreadPool(4);
	class SysUserCallableNOTAUTO implements Callable<Integer>{

		private List<SysUser> users;
		private List<TransactionStatus> transactionStatuses;
		private int id;
		private  PlatformTransactionManager transactionManager;
		private CountDownLatch latch;
		public SysUserCallableNOTAUTO(List<SysUser> users,List<TransactionStatus> transactionStatuses,
				int id,PlatformTransactionManager transactionManager,CountDownLatch latch) {
			this.users = users;
			this.transactionStatuses = transactionStatuses;
			this.id = id;
			this.transactionManager = transactionManager;
			this.latch = latch;
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
				latch.countDown();
				if (id == 4) {
					Integer.parseInt( "oos");
				}
				num = sysUserMapper.insertMul(users);
				
			} catch (Exception e) {
				return num;
			}
			return num;
			
		}
		
	}

	@Override
	public Integer mulTHreadInsertON(List<SysUser> list,PlatformTransactionManager transactionManager) throws Exception {
		int sum = 0;
		
		List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<>());
		CountDownLatch latch = new CountDownLatch(1);
		List<FutureTask<Integer>> tasks = new ArrayList<>(); 
		try {
			FutureTask<Integer> task1 = new FutureTask<>(new SysUserCallableNOTAUTO(list.subList(0, 100), 
					transactionStatuses,1, transactionManager,latch));
//			FutureTask<Integer> task2 = new FutureTask<>(new SysUserCallableNOTAUTO(list.subList(100, 200), 
//					transactionStatuses,1, transactionManager,latch));
//			FutureTask<Integer> task3 = new FutureTask<>(new SysUserCallableNOTAUTO(list.subList(200, 300), 
//					transactionStatuses,1, transactionManager,latch));
			tasks.add(task1);
//			tasks.add(task2);
//			tasks.add(task3);			
			executor.submit(task1);
//			executor.submit(task2);
//			executor.submit(task3);			
			latch.await();
			for (FutureTask<Integer> futureTask : tasks) {
				sum+=futureTask.get();
			}
			if (sum !=100) {
				throw new RuntimeException();
			}
			
		} catch (Exception e) {
			
			for (TransactionStatus status : transactionStatuses) {
				transactionManager.rollback(status);
			}
		} finally {
			if (!transactionStatuses.isEmpty()) {
				for (TransactionStatus status : transactionStatuses) {
					if (status != null && status.isNewTransaction() 
	                        && !status.isCompleted()) {
						transactionManager.commit(status);
					}
				}
			}			
		}
		
		return sum;
	}
	

}
