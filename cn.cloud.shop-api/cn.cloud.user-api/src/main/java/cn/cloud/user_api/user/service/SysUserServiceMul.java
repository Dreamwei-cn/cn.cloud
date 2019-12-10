package cn.cloud.user_api.user.service;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;

import cn.cloud.user_api.user.entity.SysUser;

public interface SysUserServiceMul {

	Integer mulTHreadInsertON(List< SysUser> list,PlatformTransactionManager transactionManager) throws Exception;
}
