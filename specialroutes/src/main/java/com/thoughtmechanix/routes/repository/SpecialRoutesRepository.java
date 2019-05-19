package com.thoughtmechanix.routes.repository;

import com.thoughtmechanix.routes.model.AbTestingRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialRoutesRepository extends CrudRepository<AbTestingRoute, String> {
    public AbTestingRoute findByServiceName(String serviceName);
}
