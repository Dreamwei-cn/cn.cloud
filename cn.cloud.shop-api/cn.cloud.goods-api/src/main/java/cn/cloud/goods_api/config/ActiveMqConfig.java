package cn.cloud.goods_api.config;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configurable
@EnableJms
public class ActiveMqConfig {

		
		private static final Logger log = LoggerFactory.getLogger(ActiveMqConfig.class);

	    @Bean
	    public JmsTemplate initJmsTemplate(ConnectionFactory connectionFactory) {
	    	log.debug("init jms template with converter.");
	        JmsTemplate template = new JmsTemplate();
	        template.setConnectionFactory(connectionFactory); // JmsTemplate使用的connectionFactory跟JmsTransactionManager使用的必须是同一个，不能在这里封装成caching之类的。
	        return template;
	    }

	    // 这个用于设置 @JmsListener使用的containerFactory
	    @Bean
	    public JmsListenerContainerFactory<?> msgFactory(ConnectionFactory connectionFactory,
	                                                     DefaultJmsListenerContainerFactoryConfigurer configurer,
	                                                     PlatformTransactionManager transactionManager) {
	        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	        factory.setTransactionManager(transactionManager);
	        factory.setCacheLevelName("CACHE_CONNECTION");
	        factory.setReceiveTimeout(10000L);
	        configurer.configure(factory, connectionFactory);
	        return factory;
	    }

	    @Bean("jmsTransactionManager")
	    public PlatformTransactionManager transactionManager(ConnectionFactory connectionFactory) {
	        return new JmsTransactionManager(connectionFactory);
	    }

}
