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
public class UserServiceTest extends ServiceTestBase {

    @Test
    public void testCanCreateAUser(){

        String email = createTestUser();
        assertNotNull(email);
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
