package com.thoughtmechanix.routes.controllers;

import com.thoughtmechanix.routes.model.AbTestingRoute;
import com.thoughtmechanix.routes.service.SpecialRoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="v1/routes/")
public class SpecialRoutesController {

    @Autowired
    SpecialRoutesService specialRoutesService;

    @RequestMapping(value= "/{serviceId}" , method = RequestMethod.GET)
    public ResponseEntity<AbTestingRoute> getRoute(@PathVariable("serviceId") String serviceName){
        return ResponseEntity.ok(specialRoutesService.getRoute(serviceName));
    }

    @RequestMapping(value = "/{serviceId}" , method = RequestMethod.POST)
    public ResponseEntity<AbTestingRoute> saveRoute(@PathVariable("serviceId") String serviceName,
                                    @RequestBody AbTestingRoute route){
        route.setServiceName(serviceName);
        specialRoutesService.saveRoute(route);

        return ResponseEntity.ok(route);
    }


}
