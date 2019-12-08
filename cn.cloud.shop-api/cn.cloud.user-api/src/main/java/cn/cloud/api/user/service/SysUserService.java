package cn.cloud.api.user.service;

import java.util.List;

import org.springframework.transaction.TransactionStatus;

import cn.cloud.api.user.entity.SysUser;

public interface SysUserService {
	public SysUser getSYsUserById(Long id);
	

	Integer mulThreadInsert(List<SysUser> userList, List<TransactionStatus> transactionStatuses) throws Exception;

}
