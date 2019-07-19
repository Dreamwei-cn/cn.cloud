package cn.cloud.ribbon.balance.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;

@Configuration
public class RibbonConfig {
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
//    @Bean
//    public IRule myRule(){
//        //return new RoundRobinRule();//轮询
//        //return new RetryRule();//重试
//        return new BestAvailableRule();
//    }

}
