package com.thoughtmechanix.assets.clients;

import com.thoughtmechanix.assets.model.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient("company-service")
public interface CompanyFeignClient {

    @RequestMapping(method = RequestMethod.GET, value="/v1/companys/{companyId}", consumes="application/json")
    public Company getCompany(@PathVariable("companyId") String companyId);
}
