package cn.cloud.Feign.service;

import org.springframework.stereotype.Component;

@Component
public class SchedualGoodsImpll implements SchedualGoods {

	@Override
	public String sayHiFromClientOne(String name) {
		
		return "sorry  " + name;
	}

}
