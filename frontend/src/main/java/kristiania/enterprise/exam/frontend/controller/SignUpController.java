package kristiania.enterprise.exam.frontend.controller;


import kristiania.enterprise.exam.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/*
* NOTE: This file is coped from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/18f764c3123f60339ab98167790aa223641e7559/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/jsf/SignUpController.java
*/

@Named
@RequestScoped
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    private String username;

    private String password;

    public String signUpUser(){

        boolean registered = false;
        try {
            registered = userService.createUser(username, password);
        } catch (Exception e){
            //nothing to do
        }

        if(registered){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    password,
                    userDetails.getAuthorities());

            authenticationManager.authenticate(token);

            if (token.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(token);
            }

            return "/index.jsf?faces-redirect=true";
        } else {
            return "/signup.jsf?faces-redirect=true&error=true";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
