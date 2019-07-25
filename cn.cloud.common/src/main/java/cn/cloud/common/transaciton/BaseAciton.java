package cn.cloud.common.transaciton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class BaseAciton {
	
	@Autowired
	private PlatformTransactionManager txManager;
	
	public void buyTicket() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(definition);
		
		try {
			
			//业务
			txManager.commit(status);
		} catch (Exception e) {
			txManager.rollback(status);
		}
		
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
	public void buyTicket2() {

		
	}
}
