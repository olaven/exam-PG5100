package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/*
* NOTE: This file is adapted from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/18f764c3123f60339ab98167790aa223641e7559/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/db/UserEntity.java
*/

/**
 * Created by arcuri82 on 13-Dec-17.
 */
@Entity
public class UserEntity {

    @Id
    @Email
    @NotNull
    private String email;

    @NotBlank
    @Size(max = 55)
    private String givenName;

    @NotBlank
    @Size(max = 55)
    private String familyName;

    @NotBlank
    @Size(max = 340)// Long passwords are a good thing, but I still need to prevent attacks
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @NotNull
    private Boolean enabled;

    public UserEntity() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
