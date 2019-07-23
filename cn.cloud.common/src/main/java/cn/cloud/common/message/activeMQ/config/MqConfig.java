package cn.cloud.common.message.activeMQ.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

@Configuration
public class MqConfig {
	
	
	
	private static final Logger log = LoggerFactory.getLogger(MqConfig.class);

	@Value("${queueName}")
	private String queueName;
	@Value("${topicName}")
	private String topicName;
	
    @Value("${spring.activemq.user}")
    private String userName;

    @Value("${spring.activemq.password}")
    private  String password;

    @Value("${spring.activemq.broker-url}")
    private  String brokerURL;
	
    @Bean
	public ActiveMQConnectionFactory connectionFactory() {
    	System.out.println(userName+ " "+ password + " "+ brokerURL);
		return new ActiveMQConnectionFactory(userName, password, brokerURL);
	}
	
	@Bean
	public JmsListenerContainerFactory<?> myJmsContainerFactoryTopic(ActiveMQConnectionFactory  connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		connectionFactory.setUseAsyncSend(true);
		connectionFactory.setMaxThreadPoolSize(5);
		factory.setConnectionFactory(connectionFactory);
		factory.setPubSubDomain(true);
		factory.setErrorHandler(t ->{
			 log.error("Error in listener!", t);
		});
		return factory;
	}
	
	@Bean
	public JmsListenerContainerFactory<?> myJmsContainerFactoryQueue(ActiveMQConnectionFactory  connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		connectionFactory.setUseAsyncSend(true);
		connectionFactory.setMaxThreadPoolSize(5);
		factory.setConnectionFactory(connectionFactory);
		factory.setErrorHandler(t -> {
			log.error("Error in listener!",t);
		});
		return factory;
	}
	
	@Bean	
	public JmsListenerContainerFactory<?> myJmsDefaultContainerFactoryQueue(ActiveMQConnectionFactory  connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setErrorHandler(t ->{log.error("Error in listener!",t);});
		return factory;
	}
	@Bean
	public JmsListenerContainerFactory<?> myJmsDefaultContainerFactoryTopic(ActiveMQConnectionFactory  connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setPubSubDomain(true);
		factory.setErrorHandler(t ->{log.error("Error in listener!",t);});
		return factory;
	}
	
	
	
	
	
	

}
