package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.RankId;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class ServiceTestBase {

    @Autowired
    private ResetService resetService;

    @Autowired
    protected UserService userService;
    @Autowired
    protected ItemService itemService;
    @Autowired
    protected RankService rankService;

    @BeforeEach
    public void resetDatabase() {

        resetService.resetDatabase();
    }

    /*
        Note: by using unique ids here, I do not need to care about
        cleaning the database at each test
     */
    protected String getUniqueEmail(){

        int id = new Random().nextInt(); // may cause failure due to random chance, but highly unlikely
        return "foo_UserServiceTest_" + id + "@test.com";
    }

    protected String createTestUser() {

        String email = getUniqueEmail();
        String givenName = "test given";
        String familyName = "test family";
        String password = "password";

        boolean created = userService.createUser(email, givenName, familyName, password);

        if (!created) {

            return null;
        }

        return email;
    }

    protected Long createTestItem() {

        String title = "test title";
        String description = "test description";
        Category category = Category.RED;

        return itemService.createItem(title, description, category);
    }

    protected RankId createTestRank() {

        Item item = itemService.getItem(createTestItem());
        UserEntity user = userService.getUser(createTestUser(), false);
        int score = new Random().nextInt(5) + 1;

        return rankService.createRank(item, user, score);
    }
}
