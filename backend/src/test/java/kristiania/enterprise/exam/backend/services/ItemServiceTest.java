package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ItemServiceTest extends ServiceTestBase {

    @Test
    public void testCanGetItemsSortedByRank() {

        Item popular = itemService.getItem(createTestItem());
        Item moderate = itemService.getItem(createTestItem());
        Item unpopular = itemService.getItem(createTestItem());

        for (int i = 0; i < 5; i++) {

            UserEntity user = userService.getUser(createTestUser());
            rankService.rankItem(popular, user, 5);
        }

        for (int i = 0; i < 3; i++) {

            UserEntity user = userService.getUser(createTestUser());
            rankService.rankItem(moderate, user, 3);
        }

        List<Item> items = itemService.getItemsSortedByScore(null);

        assertEquals(3, items.size());
        assertEquals(popular.getId(), items.get(0).getId());
        assertEquals(unpopular.getId(), items.get(2).getId());
    }

    //TODO: test for category
}