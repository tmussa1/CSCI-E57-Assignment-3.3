package com.thoughtmechanix.company.service;

import com.thoughtmechanix.company.events.source.SimpleSourceBean;
import com.thoughtmechanix.company.model.Company;
import com.thoughtmechanix.company.repository.CompanyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class CompanyService {

    Logger logger = Logger.getLogger(CompanyService.class.getName());


    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    SimpleSourceBean simpleSourceBean;
;
    public Company getCompany(String id){
        return companyRepository.findByCompanyId(id);
    }

    public List<Company> getAllCompanies(){

        return companyRepository.findAll();
    }

    public void addCompany(Company company){

        simpleSourceBean.sendCompanyChange("CREATE", company.getCompanyId());

        logger.info("saving");

        companyRepository.save(company);

        logger.info("saved");
    }

    public Company updateCompany(Company companyOld, Company companyNew){

        BeanUtils.copyProperties(companyNew, companyOld);

        simpleSourceBean.sendCompanyChange("UPDATE", companyOld.getCompanyId());

        return companyRepository.save(companyOld);
    }

    public void deleteCompany(Company company){

        simpleSourceBean.sendCompanyChange("DELETE", company.getCompanyId());

        companyRepository.delete(company);
    }
}
