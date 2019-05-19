package com.thoughtmechanix.company.repository;

import com.thoughtmechanix.company.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {

    public Company findByCompanyId(String companyId);
    public List<Company> findAll();
}
