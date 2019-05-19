package com.thoughtmechanix.security.controllers;

import com.thoughtmechanix.security.model.Role;
import com.thoughtmechanix.security.model.User;
import com.thoughtmechanix.security.model.UserOrganization;
import com.thoughtmechanix.security.repository.RolesRepository;
import com.thoughtmechanix.security.repository.UserOrganizationRepository;
import com.thoughtmechanix.security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthenticationController {

    @Autowired
    UserOrganizationRepository userOrganizationRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping(value = {"/"} , produces = "application/json")
    public Map<String, Object> myInfo(OAuth2Authentication oAuth2Authentication){
        Map<String, Object> myInfo = new HashMap<>();

        myInfo.put("user", oAuth2Authentication.getUserAuthentication().getPrincipal());
        myInfo.put("authorities", AuthorityUtils.authorityListToSet(oAuth2Authentication
                .getUserAuthentication().getAuthorities()));

        return myInfo;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user){

        usersRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(@PathVariable("userName") String userName){

        List<User> users = usersRepository.findAllByUserName(userName);

        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/userorgs" ,method = RequestMethod.POST)
    public ResponseEntity<UserOrganization> addUserOrganization(@RequestBody UserOrganization userOrganization){

        userOrganizationRepository.save(userOrganization);

        return ResponseEntity.ok(userOrganization);
    }

    @RequestMapping(value = "/userorgs/{userName}", method = RequestMethod.GET)
    public ResponseEntity<List<UserOrganization>> getUserOrganizations(@PathVariable("userName") String userName){

        List<UserOrganization> userOrganizations = userOrganizationRepository.findAllByUserName(userName);

        return ResponseEntity.ok(userOrganizations);
    }

    @RequestMapping(value = "/userroles", method = RequestMethod.POST)
    public ResponseEntity<Role> addRole(@RequestBody Role role){

        rolesRepository.save(role);

        return ResponseEntity.ok(role);
    }

    @RequestMapping(value = "/userroles/{roleId}", method = RequestMethod.GET)
    public ResponseEntity<List<Role>> getRoles(@PathVariable("roleId") String roleId){

        List<Role> roles = rolesRepository.findAllByRoleId(roleId);

        return ResponseEntity.ok(roles);
    }
}
