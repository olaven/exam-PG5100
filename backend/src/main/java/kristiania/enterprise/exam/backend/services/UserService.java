package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;

/*
 * NOTE: This file is copied from:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/18f764c3123f60339ab98167790aa223641e7559/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/db/UserService.java
 */

/**
 * Created by arcuri82 on 13-Dec-17.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    public boolean createUser(String email, String givenName, String familyName, String password) {

        return createUser(email, givenName, familyName, password, "USER");
    }

    public boolean createUser(String email, String givenName, String familyName, String password, String role) {

        String hashedPassword = passwordEncoder.encode(password);

        if (entityManager.find(UserEntity.class, email) != null) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setGivenName(givenName);
        user.setFamilyName(familyName);
        user.setPassword(hashedPassword);
        user.setRanks(new ArrayList<>());
        user.setRoles(Collections.singleton(role));
        user.setEnabled(true);

        entityManager.persist(user);
        return true;
    }

    public UserEntity getUser(String email) {

        return entityManager.find(UserEntity.class, email);
    }

    public int getRankCount(String email) {

        return getUser(email)
                .getRanks()
                .size();
    }

    public void updateUser(String email, String givenName, String familyName) {

        UserEntity user = getUser(email);
        user.setGivenName(givenName);
        user.setFamilyName(familyName);

        entityManager.merge(user);
    }
}
