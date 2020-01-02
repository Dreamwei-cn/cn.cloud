package cn.cloud.shop.tickets_api.tickets.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import cn.cloud.shop.tickets_api.tickets.kafka.common.MessageEntity;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${kafka.consumer.servers}")
	private String servers;
	//是否自动提交
	@Value("${kafka.consumer.enable.auto.commit}")
	private boolean  enableAutoCommit;
	// 自动提交的间隔
	@Value("${kafka.consumer.auto.commit.interval}")
	private String autoCommitInterval;
	//session 的超时时间
	@Value("${kafka.consumer.session.timeout}")
	private String sessionTimeout;
	@Value("${kafka.consumer.group.id}")
	private String groupId;
	// 自动重置 offset 的位置
	@Value("${kafka.consumer.auto.offset.reset}")
	private String autoOffsetReset;
	//并发数据
	@Value("${kafka.consumer.concurrency}")
	private int concurrency;
	
	
	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String
		, MessageEntity>> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, MessageEntity> factory = new
				ConcurrentKafkaListenerContainerFactory<>();
		
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(concurrency);
		factory.getContainerProperties().setPollTimeout(1500L);
		
		return factory;
		
	}
	
	private ConsumerFactory<String, MessageEntity> consumerFactory(){
		return new DefaultKafkaConsumerFactory<>(consumerConfigs()
				, new StringDeserializer()
				, new JsonDeserializer<>(MessageEntity.class));
	}
	
	private Map<String, Object> consumerConfigs(){
		Map<String, Object> propMap = new HashMap<>();
		propMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		propMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		propMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
		
		propMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
		propMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		propMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		propMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		
		return propMap;
	}
}
