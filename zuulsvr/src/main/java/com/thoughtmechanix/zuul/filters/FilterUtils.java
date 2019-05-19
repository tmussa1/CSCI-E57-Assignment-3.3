package com.thoughtmechanix.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FilterUtils {

    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    public String getCorrelationId() {
        RequestContext context = RequestContext.getCurrentContext();

        if(context.getRequest().getHeader(CORRELATION_ID) != null){
            return context.getRequest().getHeader(CORRELATION_ID);
        } else {
            return context.getZuulRequestHeaders().get(CORRELATION_ID);
        }
    }

    public void  setCorrelationId(String correlationId) {
        RequestContext context = RequestContext.getCurrentContext();
        context.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }

    public String getServiceId(){
        RequestContext ctx = RequestContext.getCurrentContext();

        if (ctx.get("serviceId")==null) return StringUtils.EMPTY;
        return ctx.get("serviceId").toString();
    }
}
