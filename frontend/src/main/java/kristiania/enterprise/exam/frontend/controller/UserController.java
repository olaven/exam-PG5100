package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import kristiania.enterprise.exam.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

/*
NOTE: This file is adapted from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/14f9b4274a9335c41cfe958833e32ee6bc01737c/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/jsf/UserInfoController.java
*/

@Named
@RequestScoped
public class UserController {

    @Autowired
    private UserService userService;

    private String currentGivenName;
    private String currentFamilyName;

    public String getEmail(){
        //NOTE: username is an email in application-logic
        return getUserDetails().getUsername();
    }

    public String getFullName() {

        UserEntity user = userService.getUser(getEmail(), false);
        return user.getGivenName() + " " + user.getFamilyName();
    }

    public boolean hasItemInCollection(Long itemId) {

        String email = getEmail();
        return userService.hasItemInCollection(email, itemId);
    }

    public String addToCollection(Long itemId) {

        String email = getEmail();
        userService.addToCollection(email, itemId);
        return String.format("/item.jsf?itemId=%d&faces-redirect=true", itemId);
    }

    public String removeFromCollection(Long itemId) {

        String email = getEmail();
        userService.removeFromCollection(email, itemId);
        return "/profile.jsf?faces-redirect=true";
    }

    public List<Item> getCollection() {

        String email = getEmail();
        return userService.getCollection(email);
    }

    public int getRankCount() {

        return userService.getRankCount(getEmail());
    }

    private UserDetails getUserDetails() {

        return (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }


    public String update() {

        String url = "/profile.jsf?faces-redirect=true";
        if (currentGivenName.isEmpty() || currentFamilyName.isEmpty()) {
            return url + "&error=true";
        }

        String email = getEmail();
        userService.updateUser(email, currentGivenName, currentFamilyName);

        return url;
    }

    public String getCurrentGivenName() {
        return currentGivenName;
    }

    public void setCurrentGivenName(String currentGivenName) {
        this.currentGivenName = currentGivenName;
    }

    public String getCurrentFamilyName() {
        return currentFamilyName;
    }

    public void setCurrentFamilyName(String currentFamilyName) {
        this.currentFamilyName = currentFamilyName;
    }
}