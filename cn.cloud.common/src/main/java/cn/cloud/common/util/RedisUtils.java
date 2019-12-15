package cn.cloud.common.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Dream
 *	redis 工具类
 */
public class RedisUtils {

	@Autowired
	private  StringRedisTemplate sRedisTemplate;
	
	
	
	/**
	 * @param key
	 * @return  get Long timeout
	 * 
	 */
	public Long getExpire(String key) {
		return sRedisTemplate.getExpire(key);
	}
	
	/**
	 * @param key
	 * @param timeout
	 * @return true false
	 */
	public boolean setExpire(String key,Long timeout){
		return sRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}
	
	/** incr  key add 
	 * @param key
	 * @param delta
	 * @return 
	 */
	public Long incr(String key,Long delta) {
		return sRedisTemplate.opsForValue().increment(key, delta);
	}
	
	/** key --
	 * @param key
	 * @param delta
	 * @return
	 */
	public Long decr(String key,Long delta) {
		return  sRedisTemplate.opsForValue().decrement(key, delta);
	}
	/**
	 * @param pattern all key like  pattern like   name*
	 * @return keys
	 */
	public Set<String> keys(String pattern) {
		
		return sRedisTemplate.keys(pattern);
	}
	
	/**
	 * @param key
	 * delete  key and value from redisDb
	 */
	public Boolean del(String key) {
		return sRedisTemplate.delete(key);
	}
	
	
	/*	String 类型操作
		*/
	
	
	
	/** insert into redisDB  String without timeout
	 * @param key
	 * @param value
	 */
	public void set(String key,String value) {
		sRedisTemplate.opsForValue().set(key, value);
	}
	
	/**
	 * insert into redisDB  String with timeout /seconds
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void set(String key,String value,Long timeout) {
		sRedisTemplate.opsForValue().set(key, value,timeout,TimeUnit.SECONDS);
	}
	
	/**
	 * get string value  by key 
	 * @param key
	 * @return value 
	 */
	public String get(String key) {
		return sRedisTemplate.opsForValue().get(key);
	}
	
	/** get old value and  set new value
	 * @param key
	 * @param value
	 * @return
	 */
	public String getSet(String key,String value) {
		return sRedisTemplate.opsForValue().getAndSet(key, value);
	}
	
	
	/** value 的长度
	 * @param key
	 * @return
	 */
	public Long size(String key) {
		return sRedisTemplate.opsForValue().size(key);
	}
	
	
	/**
	 * @param keyList
	 * @return list value
	 */
	public List<String> getValues(List<String> keyList) {
		return sRedisTemplate.opsForValue().multiGet(keyList);
	}
	
	
	// hashMap
	
	/** put  hashMap
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hSet(String key,Object hashKey,Object value) {
		sRedisTemplate.opsForHash().put(key, hashKey, value);
	}
	
	/** get hashMap
	 * @param key
	 * @param hashKey
	 * @return 
	 */
	public Object hGet(String key,Object hashKey) {
		return sRedisTemplate.opsForHash().get(key, hashKey);
	}
	
	/**
	 * @param key
	 * @param hashKeys
	 * @return  get  values
	 */
	public List<Object> hMget(String key,List<Object> hashKeys) {
		return sRedisTemplate.opsForHash().multiGet(key, hashKeys);
	}
	
	/**
	 * @param key
	 * @return get all of  hashKeys and values  
	 */
	public Map<Object, Object> hGetAll(String key) {
		return sRedisTemplate.opsForHash().entries(key);
	}
	
	
	/**
	 * @param key
	 * @param hashKeys
	 * 删除
	 */
	public void Hdel(String key,Object ...hashKeys) {
		sRedisTemplate.opsForHash().delete(key, hashKeys);
	}
	
	/**
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public boolean hExists(String key,Object hashKey) {
		return sRedisTemplate.opsForHash().hasKey(key, hashKey);
	}
	
	
	public Long hINCR( String key,Object hashKey,long delta) {
		return sRedisTemplate.opsForHash().increment(key, hashKey, delta);
	}
	
	public Double hINCR( String key,Object hashKey,Double delta) {
		return sRedisTemplate.opsForHash().increment(key, hashKey, delta);
	}
	
	/**
	 * @param key
	 * @return size  hashMap
	 */
	public Long hLen(String key) {
		return sRedisTemplate.opsForHash().size(key);
	}
	
	
	/*
	 * 
	 * List
	 * 
	 */
	
	/**
	 * @param key
	 * @param value
	 * @return  add  value  on   left of list 
	 */
	public Long bPush(String key,String value) {
		
		return sRedisTemplate.opsForList().leftPush(key,value);
	}
	
	public Long rPush(String key,String value) {
		
		return sRedisTemplate.opsForList().rightPush(key, value);
	}
	
	/**
	 * @param key
	 * @return remove and  get  first  left values
	 */
	public String lPop(String key) {
		return sRedisTemplate.opsForList().leftPop(key);
	}
	public String rPop(String key) {
		return sRedisTemplate.opsForList().rightPop(key);
	}
	
	/**
	 * @param key
	 * @param index
	 * @return get value by index
	 */
	public String getValue(String key ,Long index) {
		return sRedisTemplate.opsForList().index(key, index);
	}
	
	/**
	 * @param key
	 * @return list size
	 */
	public Long LLen(String key) {
		return sRedisTemplate.opsForList().size(key);
	}
	
	
	/*
	 * 
	 * set   
	 * 
	 */
	
	public Long sAdd(String key,String ...values) {
		return sRedisTemplate.opsForSet().add(key, values);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
