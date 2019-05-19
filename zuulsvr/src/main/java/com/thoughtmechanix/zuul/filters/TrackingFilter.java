package com.thoughtmechanix.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.logging.Logger;

@Component
public class TrackingFilter extends ZuulFilter {

    @Autowired
    FilterUtils filterUtils;

    @Autowired
    Tracer tracer;

    Logger logger = Logger.getLogger(TrackingFilter.class.getName());

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    public boolean isCorrelationIDPresent(){
        return filterUtils.getCorrelationId() != null;
    }

    @Override
    public Object run() {

        if(isCorrelationIDPresent()){
            logger.info("tmx-correaltion-id found in pre filter " + filterUtils.getCorrelationId());
        } else {
            filterUtils.setCorrelationId(tracer.getCurrentSpan().traceIdString());
            logger.info("tmx-correaltion-id generated " + filterUtils.getCorrelationId());
        }

        RequestContext context = RequestContext.getCurrentContext();

        logger.info("Inbound request is " + context.getRequest().getRequestURI());
        return null;
    }
}
