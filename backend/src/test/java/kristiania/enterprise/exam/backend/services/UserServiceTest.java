package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Item;
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
    public void testCanCreateAUser() {

        String email = createTestUser();
        assertNotNull(email);
    }


    @Test
    public void testNoTwoUsersWithSameId() {

        String email = getUniqueEmail();

        boolean created = userService.createUser(email,"ag", "af", "ap");

        assertTrue(created);

        created = userService.createUser(email,"bg", "bf", "bp");
        assertFalse(created);
    }

    @Test
    public void testCanGetRankCount() {

        String email = createTestUser();
        assertEquals(0, userService.getRankCount(email));

        UserEntity user = userService.getUser(email);

        Item item1 = itemService.getItem(createTestItem());
        Item item2 = itemService.getItem(createTestItem());
        Item item3 = itemService.getItem(createTestItem());
        Item item4 = itemService.getItem(createTestItem());

        rankService.rankItem(item1, user, 1);
        rankService.rankItem(item2, user, 2);
        rankService.rankItem(item3, user, 3);
        rankService.rankItem(item4, user, 3);

        assertEquals(4, userService.getRankCount(email));
    }


}
