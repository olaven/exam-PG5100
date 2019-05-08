package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ItemService itemService;



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
        user.setCollection(new ArrayList<>());
        user.setRoles(Collections.singleton(role));
        user.setEnabled(true);

        entityManager.persist(user);
        return true;
    }

    public UserEntity getUser(String email, boolean withCollection) {

        UserEntity user = entityManager.find(UserEntity.class, email);
        if (withCollection) {
            user.getCollection().size();
        }

        return user;
    }

    public void addToCollection(String email, Long itemId) {

        UserEntity user = getUser(email, true);
        Item item = entityManager.find(Item.class, itemId);

        List<Item> collection = user.getCollection();
        if (collection.contains(item)) {
            throw new IllegalArgumentException("Item already added to collection.");
        }

        collection.add(item);
        entityManager.merge(user);
    }

    public void removeFromCollection(String email, Long itemId) {

        UserEntity user = getUser(email, true);
        Item item = itemService.getItem(itemId);

        user.getCollection().remove(item);
        entityManager.merge(user);
    }

    public List<Item> getCollection(String email) {

        UserEntity user = getUser(email, true);
        return user.getCollection();
    }

    public boolean hasItemInCollection(String email, Long itemId) {

        UserEntity user = getUser(email, true);
        return user.getCollection().stream()
                .anyMatch(item -> item.getId().equals(itemId));
    }

    public int getRankCount(String email) {

        return getUser(email, false)
                .getRanks()
                .size();
    }

    public void updateUser(String email, String givenName, String familyName) {

        UserEntity user = getUser(email, false);
        user.setGivenName(givenName);
        user.setFamilyName(familyName);

        entityManager.merge(user);
    }

    public List<UserEntity> getAllUsers() {

        Query query = entityManager.createNamedQuery(UserEntity.GET_ALL_USERS, UserEntity.class);
        return query.getResultList();
    }
}
