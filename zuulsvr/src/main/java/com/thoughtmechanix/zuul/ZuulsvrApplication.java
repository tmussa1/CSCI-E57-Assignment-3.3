package com.thoughtmechanix.zuul;

import com.thoughtmechanix.zuul.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy
public class ZuulsvrApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulsvrApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        List interceptors = restTemplate.getInterceptors();

        if(interceptors == null){
            restTemplate.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        } else {
            interceptors.add(new UserContextInterceptor());
            restTemplate.setInterceptors(interceptors);
        }

        return restTemplate;
    }

}
