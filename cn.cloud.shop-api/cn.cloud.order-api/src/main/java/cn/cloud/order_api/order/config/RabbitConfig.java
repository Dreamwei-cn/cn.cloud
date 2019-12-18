package cn.cloud.order_api.order.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@ComponentScan("cn.cloud.api.*")
public class RabbitConfig {
	
//	@Bean
//	public ConnectionFactory connectionFactory() {
//		CachingConnectionFactory cachingFactory = new CachingConnectionFactory();
//		
//		cachingFactory.setAddresses("localhost:5672");
//		cachingFactory.setUsername("guest");
//		cachingFactory.setPassword("guest");
//		cachingFactory.setVirtualHost("/");
//		
//		return cachingFactory;
//	}
//
//	@Bean
//	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//		rabbitAdmin.setAutoStartup(true);
//		return null;
//	}
}
