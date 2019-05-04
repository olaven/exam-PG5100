package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private static final AtomicInteger counter = new AtomicInteger(0);

    /*
        Note: by using unique ids here, I do not need to care about
        cleaning the database at each test
     */
    protected String getUniqueEmail(){
        return "foo_UserServiceTest_" + counter.getAndIncrement() + "@test.com";
    }

    private boolean createTestUser() {

        String email = getUniqueEmail();
        String givenName = "test given";
        String familyName = "test family";
        String password = "password";

        boolean created = userService.createUser(email, givenName, familyName, password);
        return created;
    }

    @Test
    public void testCanCreateAUser(){

        boolean created = createTestUser();
        assertTrue(created);
    }


    @Test
    public void testNoTwoUsersWithSameId(){

        String email = getUniqueEmail();

        boolean created = userService.createUser(email,"ag", "af", "ap");

        assertTrue(created);

        created = userService.createUser(email,"bg", "bf", "bp");
        assertFalse(created);
    }


}
