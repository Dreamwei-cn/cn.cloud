package cn.cloud.user_api.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
@EnableRabbit
public class RabbitMQConfig {
	
//	@Value("${user.delay.exchange}")
//	private String delayExchangeName;
//	
//	@Value("${user.delay.queue}")
//	private String queueName;
//	
//	@Value("${user.delay.key}")
//	private String delayKey;
//	
//	@Bean
//	public CustomExchange delayExchange() {
//		
//		Map<String, Object> args = new HashMap<String, Object>();
//		args.put("x-delayed-type", "direct");
//		return new CustomExchange(delayExchangeName, "x-delayed-message", true, false, args);
//	}
//
//	@Bean
//	public Queue queue() {
//		Queue queue = new Queue(queueName,true);
//		return queue;
//	}
//	
//	@Bean
//	public Binding binding() {
//		return BindingBuilder.bind(queue()).to(delayExchange()).with(delayKey).noargs();
//	}
//	
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("test_exchange", "x-delayed-message",true, false,args);
    }

    @Bean
    public Queue queue() {
        Queue queue = new Queue("test_queue_1", true);
        return queue;
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(delayExchange()).with("test_queue_1").noargs();
    }

	
	
}
