package com.thoughtmechanix.company.events.source;

import com.thoughtmechanix.company.events.model.CompanyChange;
import com.thoughtmechanix.company.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SimpleSourceBean {

    private Source source;

    Logger logger = Logger.getLogger(SimpleSourceBean.class.getName());

    @Autowired
    public SimpleSourceBean(Source source) {
        this.source = source;
    }

    public void sendCompanyChange(String action, String companyId){

        logger.info("Sending Kafka message for " + action + " with company Id " + companyId);

        CompanyChange companyChange = new CompanyChange(CompanyChange.class.getTypeName(),
                companyId, UserContext.getCorrelationId(), action);

        source.output().send(MessageBuilder.withPayload(companyChange).build());

        logger.info("debugging sending message");
    }
}
