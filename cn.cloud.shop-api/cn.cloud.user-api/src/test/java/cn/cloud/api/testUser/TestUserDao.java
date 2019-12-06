package cn.cloud.api.testUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@Test
	public void testUserDao1() {
//		Map< String, String> map = new HashMap<>();
//		map.put("name", "dream1");
//		map.put("name1", "dream2");
//		List< SysUser> users = sysUserMapper.selectUserByParam(map);;
//		System.out.println(users.size());
//		Map<String, String> map2 = new HashMap<>();
//		map2.put("test1", "test1");
//		map2.put("name1", "name1");
//		sysUserMapper.insertByMap(map2);
		List<SysUser> users = new ArrayList<>();
		SysUser user = new SysUser();		
		conUser(user, "mul1");
		SysUser user1 = new SysUser();		
		conUser(user1, "mul11");
		SysUser user2 = new SysUser();		
		conUser(user2, "mul12");
		users.add(user);
		users.add(user1);
		users.add(user2);
		
		sysUserMapper.insertMul(users);
	}
	
	private void  conUser(SysUser user , String name) {
		
		user.setName(name);
		user.setLoginname(name);
	}
	
	
	

}
