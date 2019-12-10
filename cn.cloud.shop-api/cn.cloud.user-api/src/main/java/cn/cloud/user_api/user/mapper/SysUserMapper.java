package cn.cloud.user_api.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cloud.user_api.user.entity.SysUser;
import cn.cloud.user_api.utils.base.MyMapper;

public interface SysUserMapper extends MyMapper<SysUser> {
	
	List<SysUser> selectUserByName(@Param("names")List<String> names);
	List<SysUser> selectUserByNameIn(@Param("names")List<String> names);
	List<SysUser> selectUserByParam(@Param("map") Map<String, String> names);
	
	void insertByMap(@Param("map") Map<String, String> map);
	
	Integer insertMul(@Param("list")List<SysUser> list);
}