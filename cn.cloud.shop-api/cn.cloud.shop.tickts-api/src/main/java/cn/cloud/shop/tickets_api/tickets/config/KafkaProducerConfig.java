package cn.cloud.shop.tickets_api.tickets.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import cn.cloud.shop.tickets_api.tickets.kafka.common.MessageEntity;

@Configuration
@EnableKafka
public class KafkaProducerConfig {
	// 服务器地址
	@Value("${kafka.producer.servers}")
	private String server;
	
	// 重试次数
	@Value("${kafka.producer.retries}")
	private int retries;
	// 【批处理大小
	@Value("${kafka.producer.batch.size}")
	private int batchSize;
	//  多少时间内
	@Value("${kafka.producer.linger}")
	private int linger;
	// 缓存大小
	@Value("${kafka.producer.buffer.memory}")
	private int bufferMemory;

	
	private Map<String, Object> producerConfigs(){
		Map<String, Object> propMap = new HashMap<String, Object>();
		
		propMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
		propMap.put(ProducerConfig.RETRIES_CONFIG, retries);
		propMap.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		propMap.put(ProducerConfig.LINGER_MS_CONFIG, linger);
		propMap.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
		propMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		propMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return propMap;
	}
	
	private ProducerFactory<String, MessageEntity> producerFactory(){
		return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer()
				, new JsonSerializer<MessageEntity>());
	}
	
	@Bean
	public KafkaTemplate<String, MessageEntity> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
	
	
	
	
}
