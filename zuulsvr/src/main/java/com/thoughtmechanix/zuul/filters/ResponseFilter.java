package com.thoughtmechanix.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ResponseFilter extends ZuulFilter {

    Logger logger = Logger.getLogger(ResponseFilter.class.getName());

    @Autowired
    FilterUtils filterUtils;

    @Autowired
    Tracer tracer;

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext context = RequestContext.getCurrentContext();

        context.getResponse().addHeader("tmx-correlation-id", tracer.getCurrentSpan().traceIdString());

        logger.info("Oubound request " + context.getRequest().getRequestURI());

        return null;
    }
}
