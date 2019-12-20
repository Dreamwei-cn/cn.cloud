package cn.cloud.user_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Dream
 *   filter 实现跨域访问
 *   单个请求实现跨域访问用  
 *   @CrossOrigin("http://127.0.0.1:8080")
	public String hello( ){
　　		return "Hello World springboot";
		}
 *   
 */
@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.setAllowCredentials(true);
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.addExposedHeader("*");

		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration("/**", config);

		return new CorsFilter(configSource);
	}
}
