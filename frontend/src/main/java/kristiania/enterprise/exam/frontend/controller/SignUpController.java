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


    private String email;
    private String givenName;
    private String familyName;
    private String password;

    public String signUpUser(){

        boolean registered = false;
        try {
            registered = userService.createUser(email, givenName, familyName, password);
        } catch (Exception e){
            //nothing to do
        }

        if(registered){

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
