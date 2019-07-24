package cn.cloud.Feign.service;

import org.springframework.stereotype.Component;

@Component
public class SchedualLogisticsImpl implements SchedualLogistics {

	@Override
	public String sayHiLogistics(String name) {

		return " sorry  " + name;
	}

}
