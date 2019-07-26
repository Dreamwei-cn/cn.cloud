package cn.cloud.zuul.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
@Component
public class ZuulFilter extends com.netflix.zuul.ZuulFilter {

	private static final Logger log = LoggerFactory.getLogger(ZuulFilter.class);


	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctContext = RequestContext.getCurrentContext();
		HttpServletRequest request = ctContext.getRequest();
		log.debug("%s >>>> %s",request.getMethod(),request.getRequestURL().toString());
		Object accessToken = request.getParameter("taken");
		if (accessToken == null) {
			log.warn("taken is null");
			ctContext.setSendZuulResponse(false);
			ctContext.setResponseStatusCode(401);
			try {
				ctContext.getResponse().getWriter().write("taken is empty");
			} catch (IOException e) {
				e.printStackTrace();
				
			}
			return null;
		}
		log.info("ok");
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
