package cn.cloud.goods_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import tk.mybatis.spring.annotation.MapperScan;
@SpringBootApplication
@MapperScan(basePackages ="cn.cloud.goods_api.goods.mapper")
@EnableEurekaClient
public class AppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
		
	}

}
