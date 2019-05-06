package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.entity.UserEntity;
import kristiania.enterprise.exam.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/*
NOTE: This file is adapted from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/14f9b4274a9335c41cfe958833e32ee6bc01737c/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/jsf/UserInfoController.java
*/

@Named
@RequestScoped
public class UserInfoController {

    @Autowired
    private UserService userService;

    public String getEmail(){
        //NOTE: username is an email in application-logic
        return getUserDetails().getUsername();
    }

    public String getFullName() {

        UserEntity user = userService.getUser(getEmail());
        return user.getGivenName() + " " + user.getFamilyName();
    }

    private UserDetails getUserDetails() {

        return (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}