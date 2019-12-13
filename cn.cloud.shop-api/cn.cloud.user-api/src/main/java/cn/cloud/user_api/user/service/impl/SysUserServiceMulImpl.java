package cn.cloud.user_api.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.user.mapper.SysUserMapper;
import cn.cloud.user_api.user.service.SysUserServiceMul;

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
			DefaultTransactionDefinition defaultTransactionDefinition =
					new DefaultTransactionDefinition();
			
			defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			
			TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);
			
			transactionStatuses.add(status);
			try {
				
				
				latch.countDown();
				if (id == 4) {
					Integer.parseInt( "oos");
				}
				num = sysUserMapper.insertMul(users);
				transactionManager.commit(status);
			} catch (Exception e) {
				transactionManager.rollback(status);
				return num;
			}
			return num;
			
		}
		
	}

	@Override
	@Transactional
	public Integer mulTHreadInsertON(List<SysUser> list,PlatformTransactionManager transactionManager) throws Exception {
		int sum = 0;
		
		List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<>());
		CountDownLatch latch = new CountDownLatch(1);
		List<FutureTask<Integer>> tasks = new ArrayList<>(); 
		
			FutureTask<Integer> task1 = new FutureTask<>(new SysUserCallableNOTAUTO(list.subList(0, 100), 
					transactionStatuses,1, transactionManager,latch));
			FutureTask<Integer> task2 = new FutureTask<>(new SysUserCallableNOTAUTO(list.subList(100, 200), 
					transactionStatuses,1, transactionManager,latch));
			FutureTask<Integer> task3 = new FutureTask<>(new SysUserCallableNOTAUTO(list.subList(200, 300), 
					transactionStatuses,1, transactionManager,latch));
			tasks.add(task1);
			tasks.add(task2);
			tasks.add(task3);			
			executor.submit(task1);
			executor.submit(task2);
			executor.submit(task3);			
			latch.await();
			for (FutureTask<Integer> futureTask : tasks) {
				sum+=futureTask.get();
			}
			if (sum !=100) {
				throw new RuntimeException();
			}
			
		
		
		return sum;
	}

	@Override
	public SysUser addSysUser(SysUser sysUser) {
		sysUserMapper.insert(sysUser);
		return sysUser;
	}

	@Cacheable(value = "user", key = "#root.args[0]", unless = "#result eq null")
	@Override
	public SysUser getById(Long id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	/**
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     * 
     */
	@CachePut(value = "user", key = "#root.args[0]", unless = "#sysUser eq null") 
	@Override
	public SysUser update(SysUser sysUser) {
		
		sysUserMapper.insert(sysUser);
		return sysUser;
	}


	/**
	 * 
	 */
	@CacheEvict(value="user",key = "#root.args[0]",condition = "#result eq true")
	@Override
	public Boolean removeUser(Long id) {
		int num =  sysUserMapper.deleteByPrimaryKey( id);
		
		return num > 0 ? true :false;
	}
	

}
