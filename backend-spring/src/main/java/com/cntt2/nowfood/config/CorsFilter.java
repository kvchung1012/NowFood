package com.cntt2.nowfood.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 11:30 AM
 */@Component
public class CorsFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(CorsFilter.class);
    @Value("${endpoints.cors.allowed-methods}")
    private String methods;

    @Value("${localhost.path.client.user}")
    private String origins;

    @Value("${endpoints.cors.max-age}")
    private long maxAge;

    @Value("${endpoints.cors.allow-credentials}")
    private boolean allowCredentials;

    public CorsFilter() {
        log.info("SimpleCORSFilter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", methods);
        response.setHeader("Access-Control-Max-Age", maxAge + "");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
