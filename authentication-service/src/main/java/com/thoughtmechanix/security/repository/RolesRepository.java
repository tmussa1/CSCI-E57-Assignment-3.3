package com.thoughtmechanix.security.repository;

import com.thoughtmechanix.security.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends CrudRepository<Role, String> {

    List<Role> findAllByRoleId(String roleId);
}
