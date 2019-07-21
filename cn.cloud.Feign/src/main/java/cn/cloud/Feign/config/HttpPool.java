package cn.cloud.Feign.config;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpPool {	
	
	@Bean
	public HttpClient httpClient() {
		//生成默认配置
		RequestConfig.Builder reBuilder = RequestConfig.custom();
		//超时时间
		reBuilder.setSocketTimeout(5*1000);
		// 连续时间
		reBuilder.setConnectTimeout(5*1000);
		
		RequestConfig defaultConfig = reBuilder.build();
		// 连接池配置
        // 长连接保持30秒
		final PoolingHttpClientConnectionManager poolingConnectionManager = 
				new PoolingHttpClientConnectionManager(30,TimeUnit.MILLISECONDS);
		// 总连接数
		poolingConnectionManager.setMaxTotal(500);
	     // 同路由的并发数
		poolingConnectionManager.setDefaultMaxPerRoute(250);
		// httpclient  配置
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		//保持场链接配置  需在头 添加keep-alive
		httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
		httpClientBuilder.setConnectionManager(poolingConnectionManager);
		httpClientBuilder.setDefaultRequestConfig(defaultConfig);
		
		HttpClient client = httpClientBuilder.build();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				poolingConnectionManager.closeExpiredConnections();
				poolingConnectionManager.closeIdleConnections(5, TimeUnit.SECONDS);
			}
		}, 10*1000, 5*1000);
		return client;
	}

}
