package com.thoughtmechanix.company.controller;

import com.thoughtmechanix.company.model.Company;
import com.thoughtmechanix.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value="v1/companys")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    Logger logger = Logger.getLogger(CompanyController.class.getName());

    @RequestMapping(value="/{companyId}", method=RequestMethod.GET)
    public ResponseEntity<Company> getCompany(@PathVariable("companyId") String companyId){
        logger.info("Looking up company " + companyId);
        Company company = companyService.getCompany(companyId);
        company.setCompanyName("OLD::" + company.getCompanyName());

        if(company == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(company);
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<List<Company>> getAllCompanies(){

        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @RequestMapping(value="/{companyId}", method = RequestMethod.POST)
    public ResponseEntity addCompany(@PathVariable("companyId") String companyId, @RequestBody Company company){

        company.setCompanyId(companyId);
        companyService.addCompany(company);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value="/{companyId}", method = RequestMethod.PUT)
    public ResponseEntity updateCompany(@PathVariable("companyId") String companyId, @RequestBody Company companyNew){
        Company companyOld = companyService.getCompany(companyId);

        if(companyOld == null){
            //Create it if it doesn't exist
            companyService.addCompany(companyNew);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }

        //Modify it if it exists
        companyService.updateCompany(companyOld, companyNew);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(path = "/{companyId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCompany(@PathVariable("companyId") String companyId){

        //It may not be found or some DB error hence the try/catch
        try{

            Company company = companyService.getCompany(companyId);
            companyService.deleteCompany(company);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
