package com.thoughtmechanix.security.repository;

import com.thoughtmechanix.security.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, String> {

    List<User> findAllByUserName(String userName);
}
