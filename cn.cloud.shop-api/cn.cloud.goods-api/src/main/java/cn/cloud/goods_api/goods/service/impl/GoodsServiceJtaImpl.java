package cn.cloud.goods_api.goods.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

import cn.cloud.goods_api.goods.entity.GoodInfo;
import cn.cloud.goods_api.goods.mapper.GoodInfoMapper;
import cn.cloud.goods_api.goods.service.GoodsServiceJta;
@Service
public class GoodsServiceJtaImpl implements GoodsServiceJta {

	@Autowired
	private GoodInfoMapper goodInfoMapper;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	private static final Logger log = LoggerFactory.getLogger(GoodsServiceJtaImpl.class);

	@Transactional
	@Override
	public int saveOrUpdate(GoodInfo goodInfo) {
		
		Assert.notNull(goodInfo,"商品信息不能為空");
		int num = 0;
		
		if (goodInfo.getId() !=null) {
			num = goodInfoMapper.updateByPrimaryKey(goodInfo);
		}else {
			num = goodInfoMapper.insert(goodInfo);
		}
		
		
		return num;
	}

	@Override
	public int saveOrUpdateByCode(GoodInfo goodInfo) {
		
		Assert.notNull(goodInfo,"商品信息不能為空");
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		definition.setTimeout(15);
		TransactionStatus status = transactionManager.getTransaction(definition);
		int num = 0;
		try {
					
			if (goodInfo.getId() !=null) {
				num = goodInfoMapper.updateByPrimaryKey(goodInfo);
			}else {
				num = goodInfoMapper.insert(goodInfo);
			}
			jmsTemplate.convertAndSend("customer:goods:replay",goodInfo.getName() + " 创建成功");
			transactionManager.commit(status);
		} catch (Exception e) {
			log.info(" 创建失败  事务回滚 ");
			transactionManager.rollback(status);
		}
		return num;
		

	}

	@Override
	public int saveOrUpdateNo(GoodInfo goodInfo) {
		Assert.notNull(goodInfo,"商品信息不能為空");
		int num = 0;
		
		if (goodInfo.getId() !=null) {
			num = goodInfoMapper.updateByPrimaryKey(goodInfo);
		}else {
			num = goodInfoMapper.insert(goodInfo);
		}
		if (goodInfo.getName().contains("error")) {
			log.debug(" 产生异常");
			throw new RuntimeException("创建异常");
		}
		
	
		return num;
	}
	
	

	
	
}
