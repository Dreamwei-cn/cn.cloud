package cn.cloud.common.filter;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class CloodCorsFilter extends OncePerRequestFilter {

	private final UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
	private  CorsProcessor processor = new DefaultCorsProcessor();
	
	//需要注入map--- <url,CorsConfiguration>
	private java.util.LinkedHashMap<String,CorsConfiguration> corsConfigurations;
		
	public CloodCorsFilter(LinkedHashMap<String, CorsConfiguration> corsConfigurations) {
		this.corsConfigurations = corsConfigurations;
		configSource.setCorsConfigurations(corsConfigurations);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (CorsUtils.isCorsRequest(request)) {
			CorsConfiguration corsConfiguration = this.configSource.getCorsConfiguration(request);
			if (corsConfiguration != null) {
				boolean isValid = this.processor.processRequest(corsConfiguration, request, response);
				if ( !isValid || CorsUtils.isPreFlightRequest(request)) {
					return;
				}
			}
			
		}
		filterChain.doFilter(request, response);

	}

}
