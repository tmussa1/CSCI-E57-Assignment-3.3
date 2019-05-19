package com.thoughtmechanix.assets.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.thoughtmechanix.assets.model.Company;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class CompanyRedisRepositoryImpl implements CompanyRedisRepository {

    private static final String HASH_NAME = "company";

    private RedisTemplate<String, Company> redisTemplate;
    private HashOperations hashOperations;


    public CompanyRedisRepositoryImpl() {
        super();
    }

    @Autowired
    public CompanyRedisRepositoryImpl(RedisTemplate<String, Company> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void populate(){
       this.hashOperations = redisTemplate.opsForHash();
    }

    public Company getCompany(String companyId) {
        return (Company) hashOperations.get(HASH_NAME, companyId);
    }

    @Override
    public void saveCompany(Company company) {
        hashOperations.put(HASH_NAME, company.getCompanyId(), company);
    }

    @Override
    public void updateCompany(Company company) {
        hashOperations.put(HASH_NAME, company.getCompanyId(), company);
    }

    @Override
    public void deleteCompany(String companyId) {
        hashOperations.delete(HASH_NAME, companyId);
    }
}
