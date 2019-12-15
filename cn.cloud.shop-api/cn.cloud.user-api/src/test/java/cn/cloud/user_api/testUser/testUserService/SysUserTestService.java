package cn.cloud.user_api.testUser.testUserService;



import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.user.mapper.SysUserMapper;
import cn.cloud.user_api.user.service.SysUserServiceMul;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserTestService {
	@Autowired
	private SysUserServiceMul sysUserServiceMul;
	@Autowired
	private SysUserMapper sysUserMapper;

	@Test
	public void testCache() {
		SysUser user = null;
		user = sysUserServiceMul.getById(101L);
		
		SysUser user2 = sysUserServiceMul.getById(98L);
		user2.setCreator("testCache");
		
		SysUser user3 = sysUserServiceMul.getById(96L);
		
		SysUser userNew = sysUserServiceMul.update(user2);
		sysUserServiceMul.removeUser(95L);
		System.out.println(user.getName());
	}
	@Test
	public void testMapper() {
		SysUser user = new SysUser();
		user= sysUserServiceMul.getById(98L);
		
		user.setCreatedate(LocalDateTime.now().toDate());
		user.setCreator("1111111111");
		sysUserServiceMul.update(user);
		System.out.println("------------------------------");
		sysUserServiceMul.removeUser(95L);
	}
	
	@Test
	public void testMapperUpdate() {
		SysUser user = new SysUser();
		user= sysUserServiceMul.getById(98L);
		System.out.println(user.getCreator());
		
		user.setCreator("testUpdatecache222");
		SysUser user1= sysUserServiceMul.update(user);
		System.out.println(user1.getCreator());
		
		SysUser user2= sysUserServiceMul.getById(98L);
		System.out.println(user2.getCreator());
	}
}
