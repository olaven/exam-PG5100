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
    private String getUniqueId(){
        return "foo_UserServiceTest_" + counter.getAndIncrement();
    }

    @Test
    public void testCanCreateAUser(){

        String user = getUniqueId();
        String password = "password";

        boolean created = userService.createUser(user,password);
        assertTrue(created);
    }


    @Test
    public void testNoTwoUsersWithSameId(){

        String user = getUniqueId();

        boolean created = userService.createUser(user,"a");
        assertTrue(created);

        created = userService.createUser(user,"b");
        assertFalse(created);
    }


}
