package cn.cloud.api.testUser;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.cloud.api.user.entity.SysUser;
import cn.cloud.api.user.mapper.SysUserMapper;
import cn.cloud.api.user.service.SysUserService;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUserDao {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserService sysuserService;
	
	@Test
	public void name() {
		Long id = 10L;
		
		SysUser user = new SysUser();
		user.setName("dream");
		SysUser sysUser =sysUserMapper.selectByPrimaryKey(id);
		
		System.out.println(sysUser.getName()+":"+ sysUser.getPersonid());
	}
	@Test
	public void testUserService() {
		SysUser sysUser = sysuserService.getSYsUserById(10L);
		System.out.println(sysUser.getName());
	}
	
	@Test
	public void testUserDao() {
		List< String> names = new ArrayList<>();
		names.add("name");
		names.add("dream1");
		List< SysUser> users = sysUserMapper.selectUserByNameIn(names);
		System.out.println(users.size());
	}

}
