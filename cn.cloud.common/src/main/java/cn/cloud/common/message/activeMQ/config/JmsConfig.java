package cn.cloud.common.message.activeMQ.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

@Configuration
@EnableJms
public class JmsConfig {
	
	
	private static final Logger log = LoggerFactory.getLogger(JmsConfig.class);

    @Bean("jmsQueueListenerContainerFactory")
    public JmsListenerContainerFactory<?> jmsQueueListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置连接数
        factory.setConcurrency("3-10");
        //重连间隔时间
        factory.setRecoveryInterval(1000L);
        factory.setPubSubDomain(false);
        factory.setErrorHandler(t ->{
        	log.error("Error in listener!",t);
        });
        return factory;

    }

    @Bean("jmsTopicListenerContainerFactory")
    public JmsListenerContainerFactory<?>  jmsTopicListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //重连间隔时间
        factory.setRecoveryInterval(1000L);
        factory.setPubSubDomain(true);
        factory.setErrorHandler(t ->{
        	log.error("Error in listener!",t);
        });
        return factory;

    }


   
	
	//重发机制
	
	@Bean
	public RedeliveryPolicy redeliveryPolicy() {
		RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		 //是否在每次尝试重新发送失败后,增长这个等待时间
		redeliveryPolicy.setUseExponentialBackOff(true);
		//设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
		redeliveryPolicy.setMaximumRedeliveryDelay(-1);
		//第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
		redeliveryPolicy.setBackOffMultiplier(2);
		
		
		//重发次数 默认 6次
		redeliveryPolicy.setMaximumRedeliveries(10);
		// 时间间隔默认 1秒
		redeliveryPolicy.setInitialRedeliveryDelay(1);

		//是否避免消息碰撞
		redeliveryPolicy.setUseCollisionAvoidance(false);

		return redeliveryPolicy;
	}
	
	
	@Bean	
	public JmsListenerContainerFactory<?> jmsDefaultContainerFactoryQueueRe(ActiveMQConnectionFactory  connectionFactory,RedeliveryPolicy redeliveryPolicy) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		
		connectionFactory.setRedeliveryPolicy(redeliveryPolicy);
		connectionFactory.setAlwaysSessionAsync(true);
		
		
		factory.setConnectionFactory(connectionFactory);
        //设置连接数
        factory.setConcurrency("1-10");
        //重连间隔时间
        factory.setRecoveryInterval(1000L);
        //手动确认
        factory.setSessionAcknowledgeMode(4);
		factory.setErrorHandler(t ->{log.error("Error in listener!",t);});
		factory.setSessionAcknowledgeMode(4);

		return factory;
	}
	
	 

//	@Bean
//	public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory activeMQConnectionFactory) {
//		JmsMessagingTemplate jmsMessagingTemplate = new JmsMessagingTemplate();
//		activeMQConnectionFactory.setAlwaysSessionAsync(true);
//		jmsMessagingTemplate.setConnectionFactory(activeMQConnectionFactory);
//		return jmsMessagingTemplate;
//	}


}
