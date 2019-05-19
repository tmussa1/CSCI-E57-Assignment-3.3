package com.thoughtmechanix.zuul.filters;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.thoughtmechanix.zuul.model.AbTestingRoute;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class SpecialRoutesFilter extends ZuulFilter {

    @Autowired
    FilterUtils filterUtils;

    @Autowired
    RestTemplate restTemplate;

    private ProxyRequestHelper proxyRequestHelper = new ProxyRequestHelper();

    @Override
    public String filterType() {
        return FilterUtils.ROUTE_FILTER_TYPE;
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

        AbTestingRoute abTestingRoute = getAbRoutingInfo(filterUtils.getServiceId());

        if (abTestingRoute!=null && useSpecialRoute(abTestingRoute)) {
            String route = buildRoute(context.getRequest().getRequestURI(),
                    abTestingRoute.getEndpoint(),
                    context.get("serviceId").toString());

            forwardToSpecialRoute(route);
        }
        return null;
    }

    private AbTestingRoute getAbRoutingInfo(String serviceId) {
        ResponseEntity<AbTestingRoute> abTestingRouteResponseEntity = null;
        try {
            abTestingRouteResponseEntity = restTemplate.exchange(
                    "http://specialroutes-service/v1/routes/{serviceId}",
                    HttpMethod.GET,
                    null, AbTestingRoute.class, serviceId);
        }
        catch(HttpClientErrorException ex){
            if (ex.getStatusCode()== HttpStatus.NOT_FOUND) return null;
            throw ex;
        }
        return abTestingRouteResponseEntity.getBody();
    }

    private String buildRoute(String oldRoute, String newRoute, String serviceId){

        StringBuilder route = new StringBuilder();

        route.append(newRoute);
        route.append("/");
        route.append(oldRoute.substring(oldRoute.indexOf(serviceId), serviceId.length()));

        return route.toString();
    }

    private void forwardToSpecialRoute(String route) {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = context.getRequest();

        MultiValueMap<String , String > headers = this.proxyRequestHelper
                .buildZuulRequestHeaders(httpServletRequest);

        MultiValueMap<String, String> parameters = this.proxyRequestHelper
                .buildZuulRequestQueryParams(httpServletRequest);

        String httpVerb = httpServletRequest.getMethod().toUpperCase();

        InputStream body = null;

        try{
            body = httpServletRequest.getInputStream();
        } catch (IOException e) {
            e.getCause();
        }

        if(httpServletRequest.getContentLength() < 0){
            context.setChunkedRequestBody();
        }

        this.proxyRequestHelper.addIgnoredHeaders();

        CloseableHttpClient httpClient = null;
        HttpResponse httpResponse = null;

        try{
            httpClient  = HttpClients.createDefault();
            httpResponse = forward(httpClient, httpVerb, route, httpServletRequest, headers, parameters,
                    body);
            setResponse(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setResponse(HttpResponse httpResponse) throws IOException {
        this.proxyRequestHelper.setResponse(httpResponse.getStatusLine().getStatusCode(),
                httpResponse.getEntity() == null ? null : httpResponse.getEntity().getContent(),
                revertHeaders(httpResponse.getAllHeaders()));
    }

    private MultiValueMap<String,String> revertHeaders(Header[] allHeaders) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        for(Header header: allHeaders){
            String name = header.getName();

            if(!multiValueMap.containsKey(name)){
                multiValueMap.put(name, new ArrayList<>());
            }
            multiValueMap.get(name).add(header.getValue());
        }
        return multiValueMap;
    }

    private HttpResponse forward(HttpClient httpClient, String httpVerb, String route,
                                 HttpServletRequest httpServletRequest, MultiValueMap<String,String> headers,
                                 MultiValueMap<String,String> parameters, InputStream body) throws IOException {

        Map<String, Object> info = this.proxyRequestHelper.debug(httpVerb, route, headers, parameters,
                body);
        URL host = new URL(route);
        HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(), host.getProtocol());

        HttpRequest httpRequest;
        int contentLength = httpServletRequest.getContentLength();

        InputStreamEntity entity = new InputStreamEntity(body, contentLength,
                httpServletRequest.getContentType() != null
                        ? ContentType.create(httpServletRequest.getContentType()) : null);
        switch (httpVerb.toUpperCase()) {
            case "POST":
                HttpPost httpPost = new HttpPost(route);
                httpRequest = httpPost;
                httpPost.setEntity(entity);
                break;
            case "PUT":
                HttpPut httpPut = new HttpPut(route);
                httpRequest = httpPut;
                httpPut.setEntity(entity);
                break;
            case "PATCH":
                HttpPatch httpPatch = new HttpPatch(route);
                httpRequest = httpPatch;
                httpPatch.setEntity(entity);
                break;
            default:
                httpRequest = new BasicHttpRequest(httpVerb, route);

        }
        try {
            httpRequest.setHeaders(convertHeaders(headers));
            HttpResponse zuulResponse = httpClient.execute(httpHost, httpRequest);

            return zuulResponse;
        }
        finally {
        }

    }

    private Header[] convertHeaders(MultiValueMap<String,String> headers) {
        List<Header> headersList = new ArrayList<>();

        for(String key: headers.keySet()){
            for(String value : headers.get(key)){
                headersList.add(new BasicHeader(key, value));
            }
        }
        return headersList.toArray(new BasicHeader[0]);
    }

    private boolean useSpecialRoute(AbTestingRoute abTestingRoute) {
        Random random = new Random();

        if (abTestingRoute.getActive().equals("N")) return false;

        int value = random.nextInt(10)+ 1;

        if (abTestingRoute.getWeight() < value) return true;

        return false;
    }
}
