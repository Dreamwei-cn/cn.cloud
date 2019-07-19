package cn.cloud.common.message.activeMQ;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@Configuration
@EnableJms
public class ActiveMqConfig {
	public static final String QUEUENAME ="cn.cloud.queue";
	public static final String TOPICNAME = "cn.cloud.topic";
	
	@Bean
	public Queue queue() {
		return new ActiveMQQueue(QUEUENAME);
	}
	
	@Bean
	public Topic topic() {
		return new ActiveMQTopic(TOPICNAME);
	}
	
	@Bean
	public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory activeMQConnectionFactory){
		DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
		containerFactory.setPubSubDomain(true);
		activeMQConnectionFactory.setAlwaysSyncSend(true);
		containerFactory.setConnectionFactory(activeMQConnectionFactory);
		return containerFactory;
	}
	
	
	
	

}
