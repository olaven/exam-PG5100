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

        UserEntity before = userService.getUser(email, false);
        assertNotEquals(newGivenName, before.getGivenName());
        assertNotEquals(newFamilyName, before.getFamilyName());

        userService.updateUser(email, newGivenName, newFamilyName);

        UserEntity after = userService.getUser(email, false);
        assertEquals(newGivenName, after.getGivenName());
        assertEquals(newFamilyName, after.getFamilyName());
    }

    @Test
    public void testCanAddItemToCollection() {

        Long itemId = createTestItem();
        String userEmail = createTestUser();

        int before = userService.getCollection(userEmail).size();
        userService.addToCollection(userEmail, itemId);
        int after = userService.getCollection(userEmail).size();

        assertEquals(before + 1, after);
    }

    @Test
    public void cannotAddItemTwice() {

        Long itemId = createTestItem();
        String userEmail = createTestUser();

        userService.addToCollection(userEmail, itemId);
        assertThrows(IllegalArgumentException.class, () -> {
            userService.addToCollection(userEmail, itemId);
        });
    }

    @Test
    public void testCanRemoveFromCollection() {

        Long itemId = createTestItem();
        String userEmail = createTestUser();
        userService.addToCollection(userEmail, itemId);

        int before = userService.getCollection(userEmail).size();
        userService.removeFromCollection(userEmail, itemId);
        int after = userService.getCollection(userEmail).size();

        assertEquals(before - 1, after);
    }

    @Test
    public void returnsTrueIfInCollection() {

        String userEmail = createTestUser();
        Long itemId = createTestItem();

        userService.addToCollection(userEmail, itemId);
        assertTrue(userService.hasItemInCollection(userEmail, itemId));
    }

    @Test
    public void returnsFalseIfNotInCollection() {

        String userEmail = createTestUser();
        Long itemId = createTestItem();

        //NOTE: never added
        assertFalse(userService.hasItemInCollection(userEmail, itemId));
    }
}