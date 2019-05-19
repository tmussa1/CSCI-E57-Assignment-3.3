package com.thoughtmechanix.security.repository;

import com.thoughtmechanix.security.model.UserOrganization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrganizationRepository extends CrudRepository<UserOrganization, String> {

    List<UserOrganization> findAllByUserName(String userName);
}
