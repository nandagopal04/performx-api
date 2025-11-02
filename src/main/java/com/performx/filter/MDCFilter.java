package com.performx.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class MDCFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			MDC.put("requestId", UUID.randomUUID().toString());
			MDC.put("clientIp", request.getRemoteAddr());
			MDC.put("userAgent", ((HttpServletRequest) request).getHeader("User-Agent"));
			chain.doFilter(request, response);
		} finally {
			MDC.clear();
		}
	}

}
