package kristiania.enterprise.exam.backend.services;

import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.function.Supplier;


@Service
public class DefaultDataInitializer {

    @Autowired
    Environment environment;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize() {

        insertCommonData();

        boolean test = Arrays.asList(environment.getActiveProfiles()).contains("test");
        if (test) {

            insertTestData();
        }
    }

    private void insertCommonData() {

        attempt(() ->   attempt(() -> userService.createUser("admin@mail.com", "amdin-given", "admin-family", "admin", "ADMIN")));
    }

    private void insertTestData() {

        System.out.println("ADDING TEST DATA");
        
        // USERS
        attempt(() -> userService.createUser("dev@mail.com", "dev-given", "dev-family", "dev"));
        attempt(() -> userService.createUser("none@booked.com", "givennothing", "familynothing", "dev"));
        attempt(() -> userService.createUser("iron@man.com", "Tony", "Stark", "suit"));
        attempt(() -> userService.createUser("spider@man.com", "Peter", "Parker", "nyc"));
        attempt(() -> userService.createUser("black@widow.com", "Scarlet", "Johanson", "secret"));
        attempt(() -> userService.createUser("thor@valhal.com", "Thor", "Odins Son", "rainbowroad"));
        attempt(() -> userService.createUser("hulk@green.com", "Robert", "Banner", "sumo-goblin"));
        attempt(() -> userService.createUser("captain@america.com", "Steven", "Rogers", "Betsy Ross"));
        attempt(() -> userService.createUser("hawk@eye.com", "Clinton", "Barton", "arrow-to-the-knee"));
    }

    private<T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}
