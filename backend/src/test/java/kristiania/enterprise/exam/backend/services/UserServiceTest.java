package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest extends ServiceTestBase {

    //TODO: tests on all methods

    @Test
    public void testCanUpdateUser() {

        String newGivenName = "new given name";
        String newFamilyName = "new family name";

        String email = createTestUser();

        UserEntity before = userService.getUser(email);
        assertNotEquals(newGivenName, before.getGivenName());
        assertNotEquals(newFamilyName, before.getFamilyName());

        userService.updateUser(email, newGivenName, newFamilyName);

        UserEntity after = userService.getUser(email);
        assertEquals(newGivenName, after.getGivenName());
        assertEquals(newFamilyName, after.getFamilyName());
    }
}