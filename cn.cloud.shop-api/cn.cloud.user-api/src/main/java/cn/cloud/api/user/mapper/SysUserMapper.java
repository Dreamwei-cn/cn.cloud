package cn.cloud.api.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cloud.api.user.entity.SysUser;
import cn.cloud.api.utils.base.MyMapper;

public interface SysUserMapper extends MyMapper<SysUser> {
	
	List<SysUser> selectUserByName(@Param("names")List<String> names);
	List<SysUser> selectUserByNameIn(@Param("names")List<String> names);
	
}