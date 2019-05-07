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

    @Test
    public void testCanCreateUser() {

        String email = "test@mail.com";
        String givenName = "test user given name";
        String familyName = "test user family name";
        String password = "super-secret-password";

        userService.createUser(email, givenName, familyName, password);
        UserEntity user = userService.getUser(email, false);

        assertEquals(email, user.getEmail());
        assertEquals(givenName, user.getGivenName());
        assertEquals(familyName, user.getFamilyName());
    }

    @Test
    public void testDefaultUserHasUserRole() {

        UserEntity user = userService.getUser(createTestUser(), false);
        assertTrue(user.getRoles().contains("USER"));
        assertFalse(user.getRoles().contains("ADMIN"));
    }

    @Test
    public void testCanCreateAdminUser() {

        String email = "admin@test.com";
        userService.createUser(email, "admin given", "admin family", "password", "ADMIN");
        UserEntity user = userService.getUser(email, false);

        assertTrue(user.getRoles().contains("ADMIN"));
    }


    @Test
    public void testInvalidEmailThrowsException() {

        String invalidEmail = "NOT_AN_EMAIL";
        assertThrows(Exception.class, () -> {
            userService.createUser(invalidEmail, "admin given", "admin family", "password", "USER");
        });
    }

    @Test
    public void testReturnsFalseOnUserCreatedTwice() {

        String email = "twice@email.com";

        boolean createdFirst = userService.createUser(email, "admin given", "admin family", "password", "ADMIN");
        boolean createdSecond = userService.createUser(email, "admin given", "admin family", "password", "ADMIN");

        assertTrue(createdFirst);
        assertFalse(createdSecond);
    }

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