package cn.cloud.user_api.testUser.testUserService;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.user.service.SysUserServiceMul;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserTestService {
	@Autowired
	private SysUserServiceMul sysUserServiceMul;

	@Test
	public void testCache() {
		SysUser user = null;
		user = sysUserServiceMul.getById(101L);
		System.out.println(user.getName());
	}
}
