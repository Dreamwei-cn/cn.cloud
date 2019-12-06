package cn.cloud.api.user.service.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cloud.api.user.entity.SysUser;
import cn.cloud.api.user.mapper.SysUserMapper;
import cn.cloud.api.user.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	private static ExecutorService executor  = Executors.newFixedThreadPool(4);
	
	public SysUser getSYsUserById(Long id) {
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
		return sysUser;
	}

	@Override
	public Integer mulThreadInsert() {
		
		
		return null;
	}
	
	class SysUserCallable implements Callable<Integer>{

		private List<SysUser> users;
		
		public SysUserCallable(List<SysUser> users) {
			this.users = users;
		}
		@Override
		public Integer call() throws Exception {
			
			return null;
		}
		
	}
	
	
}
