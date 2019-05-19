package com.thoughtmechanix.assets.clients;

import com.thoughtmechanix.assets.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Component
public class CompanyDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    public Company getCompanyDiscovery(String companyId){

        List<ServiceInstance> instances = discoveryClient.getInstances("company-service");
        RestTemplate restTemplate = new RestTemplate();

        StringBuilder uri = new StringBuilder();
        uri.append(instances.get(0).getUri().toString());
        uri.append("/v1/companys/");
        uri.append(companyId);

       return restTemplate.exchange(uri.toString(), GET, null, Company.class, companyId).getBody();
    }
}
