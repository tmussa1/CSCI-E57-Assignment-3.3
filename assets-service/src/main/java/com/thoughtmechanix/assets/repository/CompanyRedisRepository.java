package com.thoughtmechanix.assets.repository;

import com.thoughtmechanix.assets.model.Company;

public interface CompanyRedisRepository {

    Company getCompany(String companyId);
    void saveCompany(Company company);
    void updateCompany(Company company);
    void deleteCompany(String companyId);
}
