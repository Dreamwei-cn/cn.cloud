package cn.cloud.goods_api.goods.service;

import cn.cloud.goods_api.goods.entity.GoodInfo;

public interface GoodsServiceJta {

	int saveOrUpdate(GoodInfo goodInfo);
	int saveOrUpdateByCode(GoodInfo goodInfo);
	int saveOrUpdateNo(GoodInfo goodInfo);
}
