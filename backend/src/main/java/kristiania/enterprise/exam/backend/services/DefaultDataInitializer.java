package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.PlaceholderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializer {

    @Autowired
    private UserService userService;
    @Autowired
    private PlaceholderService placeholderService;

    @PostConstruct
    public void initialize() {

        insertDefaultUsers();
        insertDefaultPlaceholder();
    }

    private void insertDefaultPlaceholder() {

        PlaceholderEntity first = attempt(() -> placeholderService.createNew());
        PlaceholderEntity second = attempt(() -> placeholderService.createNew());
        PlaceholderEntity third = attempt(() -> placeholderService.createNew());

        incrementToSomeValue(first);
        incrementToSomeValue(second);
        incrementToSomeValue(third);

    }

    private void incrementToSomeValue(PlaceholderEntity placeholderEntity) {

        if (placeholderEntity != null) {

            int counter = new Random().nextInt(10);
            for (int i = 0; i < counter; i++) {
                placeholderService.increment(placeholderEntity.getId());
            }
        }
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