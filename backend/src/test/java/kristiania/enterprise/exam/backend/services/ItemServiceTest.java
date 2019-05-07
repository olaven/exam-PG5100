package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.RankId;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ItemServiceTest extends ServiceTestBase {

    @Test
    public void testCanGetItemsSortedByAverageScore() {

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

        List<Item> items = itemService.getItemsSortedByAverageScore(null);

        assertEquals(3, items.size());
        assertEquals(popular.getId(), items.get(0).getId());
        assertEquals(unpopular.getId(), items.get(2).getId());
    }

    @Test
    public void testCanFilterByCategory() {

        itemService.getItem(
                itemService.createItem("blue 1", "blue item", Category.BLUE)
        );
        itemService.getItem(
                itemService.createItem("blue 2", "blue item", Category.BLUE)
        );
        itemService.getItem(
                itemService.createItem("red", "red item", Category.RED)
        );



        List<Item> allItems = itemService.getItemsSortedByAverageScore(null);
        assertEquals(3, allItems.size());

        List<Item> filtered = itemService.getItemsSortedByAverageScore(Category.BLUE);
        assertEquals(2, filtered.size());
        filtered.forEach(item -> assertEquals(Category.BLUE, item.getCategory()));
    }

    @Test
    public void testDoesReturnAllCategories() {

        List<Category> expected = Arrays.stream(Category.values()).collect(Collectors.toList());
        List<Category> actual = itemService.getAllCategories();

        actual.forEach(category -> assertTrue(expected.contains(category)));
    }

    @Test
    public void testCanRemoveItem() {

        Long id = createTestItem();
        assertNotNull(itemService.getItem(id));

        itemService.removeItem(id);
        assertNull(itemService.getItem(id));
    }

    @Test
    public void testRemovingGetsRidOfOrphans() {

        Item item = itemService.getItem(createTestItem());
        UserEntity user = userService.getUser(createTestUser());

        RankId rankId = rankService.createRank(item, user, 3);

        assertNotNull(rankService.getRank(rankId));
        itemService.removeItem(item.getId());
        assertNull(rankService.getRank(rankId));
    }
}