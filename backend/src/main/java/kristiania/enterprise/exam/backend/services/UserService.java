package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

    public boolean createUser(String username, String password) {

        String hashedPassword = passwordEncoder.encode(password);

        if (entityManager.find(UserEntity.class, username) != null) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRoles(Collections.singleton("USER"));
        user.setEnabled(true);

        entityManager.persist(user);

        return true;
    }
}
