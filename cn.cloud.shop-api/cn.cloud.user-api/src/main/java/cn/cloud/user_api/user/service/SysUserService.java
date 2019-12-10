package cn.cloud.user_api.user.service;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import cn.cloud.user_api.user.entity.SysUser;

public interface SysUserService {
	public SysUser getSYsUserById(Long id);
	

	Integer mulThreadInsert(List<SysUser> userList, List<TransactionStatus> transactionStatuses,
			PlatformTransactionManager transactionManager) throws Exception;
	
	Integer mulThreadInsertNOTransaction(List<SysUser> userList) throws Exception;

	
	
}
