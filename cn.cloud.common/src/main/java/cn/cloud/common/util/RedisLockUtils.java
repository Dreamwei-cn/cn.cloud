package cn.cloud.common.util;

import java.util.Collections;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.netflix.eureka.registry.Key;

public class RedisLockUtils {
	@Autowired
	private StringRedisTemplate sTemplate;
	
	// 静态方法可用
	private static RedisLockUtils that;

	private static DefaultRedisScript<Long> redisScript;
	private static RedisSerializer<String> argsSerializer;
	private static RedisSerializer resultSerializer;
	
	private static final Long EXEC_RESULT_LONG = 1L;
	
	
	private static Long expireTime = 120L;
	
	
	@PostConstruct
	public void init() {
		that = this;
		redisScript = new DefaultRedisScript<>();
		redisScript.setResultType(Long.class);
		argsSerializer = new StringRedisSerializer();
		resultSerializer = new StringRedisSerializer();
		
		RedisSerializer<?> stringSerializer = new StringRedisSerializer();
		sTemplate.setKeySerializer(stringSerializer);
		sTemplate.setValueSerializer(stringSerializer);
	}
	
	
	public static String lock(String key) {
		String requestId = UUID.randomUUID().toString().replace("-", "");
		boolean lockTag = lock(key, requestId, expireTime);
		if (lockTag) {
			return requestId;
		}
		return null;
	}
	
	public static String realTimeLock(String key) {
		String requestId = UUID.randomUUID().toString().replace("-", "");
		boolean lockTag = realTimeLock(key, requestId, expireTime);
		if (lockTag) {
			return requestId;
		}
		return null;
	}
	
	
	
	
	
	public static boolean lock(String key, String requestId) {
		return lock(key, requestId, expireTime);
	}
	
	public static boolean realTimeLock(String key, String requestId) {
		return realTimeLock(key, requestId, expireTime);
	}
	
	
	
	
	
	
	
	/**
	 * 等待 10s  获取锁
	 * @param key
	 * @param requestId
	 * @param expireTime
	 * @return
	 */
	
	public static boolean lock(String key, String requestId,Long expireTime) {
		key +="_lock";
		StringBuilder script = new StringBuilder("if redis.call('setNx',KEYS[1],ARGV[1]) then\n") ;
        script.append("    if redis.call('get',KEYS[1])==ARGV[1] then\n" );
        script.append("    return redis.call('expire',KEYS[1],ARGV[2])\n") ;
        script.append("    else\n" );
        script.append("    return 0\n" );
        script.append("    end\n" );
        script.append(" end");
		redisScript.setScriptText(script.toString());
		//获取纳秒
		long nano = System.nanoTime();
		// 尝试 10s
		long timeOut = 10 * 1000 * 1000 * 1000L ;
		try {
			while (System.nanoTime() -nano < timeOut) {
				Object result = that.sTemplate.execute(redisScript,Collections.singletonList(key)
						, requestId, expireTime.toString());
				
				if (EXEC_RESULT_LONG.equals(result)) {
					return true;
				}
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	 /**
	  *  实时锁 ，不等待
	 * @param key
	 * @param requestId
	 * @param expireTime
	 * @return
	 */
	public static boolean realTimeLock(String key, String requestId, Long expireTime) {
	        key += "_lock";
	        StringBuilder script = new StringBuilder("if redis.call('setNx',KEYS[1],ARGV[1]) then\n") ;
	        script.append("    if redis.call('get',KEYS[1])==ARGV[1] then\n" );
	        script.append("    return redis.call('expire',KEYS[1],ARGV[2])\n") ;
	        script.append("    else\n" );
	        script.append("    return 0\n" );
	        script.append("    end\n" );
	        script.append(" end");
	        redisScript.setScriptText(script.toString());
	        try{
	        	Object result = that.sTemplate.execute(redisScript,Collections.singletonList(key), requestId, expireTime.toString());
	            if (EXEC_RESULT_LONG.equals(result)) {
	                return true;
	            }
	        }catch (Exception ex){
	         ex.printStackTrace();
	        }
	        return false;
	  
	 }
	
	
	public static boolean unlock(String key, String requestId) {
		if (key == null || requestId == null) {
			return false;
		}	
		key +="_lock";
		StringBuilder script = new StringBuilder(" String script = \"if redis.call('get', KEYS[1]) == ARGV[1] ");
		script.append("then return redis.call('del', KEYS[1]) else return '0' end\"");
		redisScript.setScriptText(script.toString());
		Object result = null;
		try {
			result = that.sTemplate.execute(redisScript, Collections.singletonList(key), requestId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (EXEC_RESULT_LONG.equals(result)) {
			return true;
		}
		return false;
	}
	
	
}
