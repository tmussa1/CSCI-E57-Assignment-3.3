package com.thoughtmechanix.assets.events.changeHandler;

import com.thoughtmechanix.assets.events.model.CompanyChange;
import com.thoughtmechanix.assets.repository.CompanyRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.logging.Logger;

@EnableBinding(Sink.class)
public class CompanyChangeHandler {

    Logger logger = Logger.getLogger(CompanyChangeHandler.class.getName());

    @Autowired
    CompanyRedisRepository companyRedisRepository;

    @StreamListener(Sink.INPUT)
    public void processCompanyChange(CompanyChange companyChange){

        logger.info("Received alert with company id " + companyChange.getCompanyId());

        switch(companyChange.getAction()){
            case "CREATE":
                logger.info("Creating company with id " + companyChange.getCompanyId());
                companyRedisRepository.deleteCompany(companyChange.getCompanyId());
                break;
            case "UPDATE":
                logger.info("Updating company with id " + companyChange.getCompanyId());
                companyRedisRepository.deleteCompany(companyChange.getCompanyId());
                break;
            case "DELETE":
                logger.info("Deleting company with id " + companyChange.getCompanyId());
                break;
            default:
                logger.info("Unaccepatable operation for company with id " + companyChange.getCompanyId());
                break;
        }
    }
}
