package cn.cloud.user_api.config;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.QosSettings;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
//@EnableJms
public class ActiveMQConfig {
	
	@Value("${queueName}")
	private String queueName;
	@Value("${topicName}")
	private String topicName;
	@Value("${listenerQueue}")
	private String lisQueue;
	@Value("${listenerTopic}")
	private String lisTopic;
	
    @Value("${spring.activemq.user}")
    private String userName;

    @Value("${spring.activemq.password}")
    private  String password;

    @Value("${spring.activemq.broker-url}")
    private  String brokerURL;
	
    

	
	private static final Logger log= LoggerFactory.getLogger(ActiveMQConfig.class);
	
//	@Bean
	public  Queue queue() {
		return new ActiveMQQueue(queueName);
	}
	
//	@Bean 
	public Topic Topic() {
		return new ActiveMQTopic(topicName);
	}
	
//	 @Bean
	 public RedeliveryPolicy redeliveryPolicy(){
	            RedeliveryPolicy  redeliveryPolicy=   new RedeliveryPolicy();
	            //是否在每次尝试重新发送失败后,增长这个等待时间
	            redeliveryPolicy.setUseExponentialBackOff(true);
	            //重发次数,默认为6次   这里设置为10次
	            redeliveryPolicy.setMaximumRedeliveries(6);
	            //重发时间间隔,默认为1秒
	            redeliveryPolicy.setInitialRedeliveryDelay(1);
	            //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
	            redeliveryPolicy.setBackOffMultiplier(2);
	            //是否避免消息碰撞
	            redeliveryPolicy.setUseCollisionAvoidance(false);
	            //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
	            redeliveryPolicy.setMaximumRedeliveryDelay(-1);
	            return redeliveryPolicy;
	    }
	
//	@Bean
	public ActiveMQConnectionFactory connectionFactory(RedeliveryPolicy redeliveryPolicy) {
		log.info(" create  activeMqConnectionFactory");
		ActiveMQConnectionFactory con = new ActiveMQConnectionFactory(userName, password, brokerURL);
		con.setRedeliveryPolicy(redeliveryPolicy);
		con.setMaxThreadPoolSize(5);
		con.setAlwaysSessionAsync(true);
		return con;
	}
	
//	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		log.info(" create  activeMqConnectionFactory");
		ActiveMQConnectionFactory con = new ActiveMQConnectionFactory(userName, password, brokerURL);
		con.setMaxThreadPoolSize(5);
		con.setAlwaysSessionAsync(true);
		return con;
	}
	
	
//	@Bean
	public JmsListenerContainerFactory<?> jmsContainerFactoryTopic(ActiveMQConnectionFactory  connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();

		factory.setConnectionFactory(connectionFactory);
		//topic  模式
		factory.setPubSubDomain(true);
		return factory;
	}
//	@Bean
	public JmsListenerContainerFactory<?> jmsContainerFactoryQueue(ActiveMQConnectionFactory  connectionFactory
			,PlatformTransactionManager transactionManager
			) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// 异步
		
		factory.setConnectionFactory(connectionFactory);
		// 连接数
		factory.setConcurrency("1-10");
		// 重发间隔时间

		factory.setTransactionManager(transactionManager);
		factory.setRecoveryInterval(5000L);

		factory.setSessionAcknowledgeMode(4);
		return factory;
	}
	
//	@Bean
//	public JmsListenerContainerFactory<?> jmsContainerFactoryQueueNOJmsTransaction(ActiveMQConnectionFactory  connectionFactory
//			,PlatformTransactionManager transactionManager
//			) {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		// 异步
//		
//		factory.setConnectionFactory(connectionFactory);
//		// 连接数
//		factory.setConcurrency("1-10");
//		// 重发间隔时间
//
////		factory.setTransactionManager(transactionManager);
//		factory.setRecoveryInterval(5000L);
//
//		factory.setSessionAcknowledgeMode(4);
//		return factory;
//	}
//	
//	
//
//	@Bean
//	public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory activeMQConnectionFactory,Queue queue) {
//		JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory);
//		//进行持久化配置 1表示非持久化，2表示持久
//		jmsTemplate.setDeliveryMode(2);
//		jmsTemplate.setDefaultDestination(queue);
//		//客户端签收模式     事务关闭
//		jmsTemplate.setSessionAcknowledgeMode(4);
//		
//		return new JmsMessagingTemplate(jmsTemplate);
//	}
//	
//    @Bean
//    public PlatformTransactionManager transactionManager(ActiveMQConnectionFactory connectionFactory) {
//        return new JmsTransactionManager(connectionFactory);
//    }

}
