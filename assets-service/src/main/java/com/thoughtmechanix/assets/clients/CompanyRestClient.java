package com.thoughtmechanix.assets.clients;

import com.thoughtmechanix.assets.model.Company;
import com.thoughtmechanix.assets.repository.CompanyRedisRepository;
import com.thoughtmechanix.assets.utils.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
public class CompanyRestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CompanyRedisRepository companyRedisRepository;

    Logger logger = Logger.getLogger(CompanyRestClient.class.getName());


    public Company readFromRedis(String companyId){
        try {
            return companyRedisRepository.getCompany(companyId);
        } catch (Exception e) {
            logger.info("Company with id " + companyId + " not found in redis cache. Error " + e);
            return null;
        }
    }

    public void saveCompanyToRedis(Company company){
        try {
            companyRedisRepository.saveCompany(company);
        } catch (Exception e) {
            logger.info("Error caching company with id " + company.getCompanyId() + " " + e);
        }
    }

    public Company getCompanyRestTemplate(String companyId){

        Company company = null;

        if(readFromRedis(companyId) != null){
            company = readFromRedis(companyId);
            logger.info("Company with id " + companyId + " found in redis cache ");
            return company;
        }

        logger.info("Company with id " + companyId + " not found in redis cache ");

        company = restTemplate.exchange("http://zuul-service/company/v1/companys/{companyId}",
                HttpMethod.GET, null, Company.class, companyId).getBody();

        if(company != null){
            logger.info("Saving to redis company with id " + companyId);
            saveCompanyToRedis(company);
        }

        return company;
    }
}
