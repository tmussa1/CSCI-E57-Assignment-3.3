package com.thoughtmechanix.routes.service;

import com.thoughtmechanix.routes.exception.BadRequest;
import com.thoughtmechanix.routes.model.AbTestingRoute;
import com.thoughtmechanix.routes.repository.SpecialRoutesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialRoutesService {

    @Autowired
    SpecialRoutesRepository specialRoutesRepository;

    public AbTestingRoute getRoute(String serviceName){
        AbTestingRoute route = specialRoutesRepository.findByServiceName(serviceName);

        if(route == null){
            throw new BadRequest();
        }

        return route;
    }

    public void saveRoute(AbTestingRoute route){
        specialRoutesRepository.save(route);
    }

    public void deleteRoute(AbTestingRoute route){
        specialRoutesRepository.delete(route);
    }
}
