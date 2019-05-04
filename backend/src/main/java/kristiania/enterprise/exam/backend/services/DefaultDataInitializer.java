package kristiania.enterprise.exam.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializer {

    @Autowired
    private UserService userService;

    @PostConstruct
    @Profile("test")
    public void initialize() {

        insertDefaultUsers();
    }


    private void insertDefaultUsers() {

        attempt(() -> userService.createUser("dev", "dev"));
        attempt(() -> userService.createUser("Test user 1", "test1"));
        attempt(() -> userService.createUser("Test user 2", "test2"));
    }

    private<T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}