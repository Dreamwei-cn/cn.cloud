package cn.cloud.goods_api.goods.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.cloud.goods_api.goods.entity.GoodInfo;
import cn.cloud.goods_api.goods.mapper.GoodInfoMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsDao {
	
	@Autowired
	private GoodInfoMapper goodInfoMapper;
	@Test
	public void name() {
		
		GoodInfo goodInfo = new GoodInfo();
		goodInfo.setId(1L);
		goodInfo.setName("熊猫电视");
		goodInfo.setCreatedate(new Date());
		goodInfo.setCreator("admin");
		goodInfo.setEnabled("1");
		goodInfo.setMader("made in china");
		goodInfoMapper.insert(goodInfo);
	}

	
}
