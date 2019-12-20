package cn.cloud.user_api.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

import cn.cloud.user_api.redis.RedisReceiver;

/**
 * @author Dream
 *
 */
@EnableCaching
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);

		FastJson2JsonRedisSerializer<Object> fastJson2JsonRedisSerializer = 
				new FastJson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL,Visibility.ANY);
		objectMapper.enableDefaultTyping(DefaultTyping.NON_FINAL);
		fastJson2JsonRedisSerializer.setObjectMapper(objectMapper);
		
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer);
		return redisTemplate;
	}
	

	@Bean
	public RedisSerializer<Object> fastJson2JsonRedisSerializer(){
		FastJson2JsonRedisSerializer<Object> serializer = 
				new FastJson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL,Visibility.ANY);
		objectMapper.enableDefaultTyping(DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(objectMapper);
		return serializer;
	}
	
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		
		RedisCacheConfiguration configuration  = RedisCacheConfiguration.defaultCacheConfig();
		configuration.entryTtl(Duration.ofDays(1))
			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
					new StringRedisSerializer()))
			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
					fastJson2JsonRedisSerializer()))
			//不缓存空值
			.disableCachingNullValues();
		//设置初始化的缓存空间 集合
		Set<String> cacheNames = new HashSet<>();
		cacheNames.add("timeGroup");
		cacheNames.add("user");
		
		Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
		configMap.put("timeGroup", configuration);
		configMap.put("user", configuration.entryTtl(Duration.ofHours(10)));
		
		RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
				.initialCacheNames(cacheNames)
				.withInitialCacheConfigurations(configMap)
				.build();
		
		
		return cacheManager;
	}
	@Bean
	MessageListenerAdapter  userListenerAdapter(RedisReceiver receiver) {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "receiveUserMessage");
		FastJson2JsonRedisSerializer< ?> fastJson2JsonRedisSerializer = 
				(FastJson2JsonRedisSerializer<?>) this.fastJson2JsonRedisSerializer();
		messageListenerAdapter.setSerializer(fastJson2JsonRedisSerializer);
		return messageListenerAdapter;
	}
	//  redis 消息发布监听
	
	/**
	 * @param receiver
	 * @return
	 */
	@Bean
	MessageListenerAdapter  orderListenerAdapter(RedisReceiver receiver) {
		// 消费者 不继承 MessageListener 接口      在代理器里添加    
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "receiveOrderMessage");
		FastJson2JsonRedisSerializer< ?> fastJson2JsonRedisSerializer = 
				(FastJson2JsonRedisSerializer<?>) this.fastJson2JsonRedisSerializer();
		messageListenerAdapter.setSerializer(fastJson2JsonRedisSerializer);
		return messageListenerAdapter;
	}
	
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
											MessageListenerAdapter userListenerAdapter,
											MessageListenerAdapter orderListenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(userListenerAdapter,new PatternTopic("user_topic"));
		container.addMessageListener(orderListenerAdapter,new PatternTopic("order_topic"));
		return container;
	}
	
	
	
	
	
	
	
}
