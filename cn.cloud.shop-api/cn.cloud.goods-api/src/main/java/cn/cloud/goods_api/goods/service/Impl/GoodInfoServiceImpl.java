package cn.cloud.goods_api.goods.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cloud.goods_api.goods.entity.GoodInfo;
import cn.cloud.goods_api.goods.mapper.GoodInfoMapper;
import cn.cloud.goods_api.goods.service.GoodInfoService;


@Service
public class GoodInfoServiceImpl implements GoodInfoService{
	
	@Autowired
	private GoodInfoMapper goodInfoMapper;
	
	@Transactional
	@Override
	public int addGoodInfo(GoodInfo goodInfo) {
		
		int num = 0;
		num =goodInfoMapper.insert(goodInfo);
		simpleException(goodInfo.getName());
		
		return num;
	}
	
	private void simpleException(String string) {
		
		if (string.contains("wrong")) {
			throw new  RuntimeException(" 产生错误信息 ");
		}
	}

}
