package com.thoughtmechanix.company.utils;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class UserContextFilter implements Filter {

    Logger logger = Logger.getLogger(UserContextFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        logger.info("Passing through company service with token " + httpServletRequest.getHeader("Auth"));

        UserContextHolder.getUserContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        UserContextHolder.getUserContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        UserContextHolder.getUserContext().setOrganizationId(httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));
        UserContextHolder.getUserContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));

        filterChain.doFilter(httpServletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
