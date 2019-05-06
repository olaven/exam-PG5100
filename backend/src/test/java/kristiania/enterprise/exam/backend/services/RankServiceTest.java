package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.Rank;
import kristiania.enterprise.exam.backend.entity.RankId;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RankServiceTest extends ServiceTestBase {

    @Autowired
    private RankService rankService;
    @Autowired
    private ItemService itemService;

    @Test
    public void testCanRankItem() {

        String userEmail = createTestUser();
        Long itemId = itemService.createItem("test item", "test item desc", Category.BLUE);

        Rank before = rankService.getRank(userEmail, itemId);
        assertNull(before);

        UserEntity user = userService.getUser(userEmail);
        Item item = itemService.getItem(itemId);

        rankService.rankItem(item, user, 3);


        Rank after = rankService.getRank(userEmail, itemId);
        assertNotNull(after);
    }

    @Test
    public void testThrowsIfScoreIsTooGreat() {

        String userEmail = createTestUser();
        Long itemId = itemService.createItem("test item", "test item desc", Category.BLUE);

        UserEntity user = userService.getUser(userEmail);
        Item item = itemService.getItem(itemId);

        assertThrows(IllegalArgumentException.class, () -> {
                                    // note: score is > 5
            rankService.rankItem(item, user, 6);
        });
    }

    @Test
    public void testThrowsIfScoreIsTooSmall() {

        String userEmail = createTestUser();
        Long itemId = itemService.createItem("test item", "test item desc", Category.BLUE);

        UserEntity user = userService.getUser(userEmail);
        Item item = itemService.getItem(itemId);

        assertThrows(IllegalArgumentException.class, () -> {
                                // note: score is < 1
            rankService.rankItem(item, user, 0);
        });
    }

    @Test
    public void testCanUpdateScore() {

        RankId rId = createTestRank();

        rankService.updateScore(rId.getUser().getEmail(), rId.getItem().getId(), 2);

        Rank rank = rankService.getRank(rId);
        assertEquals(Integer.valueOf(2), rank.getScore());
    }

    @Test
    public void testThrowsIfUpdatingToInvalidScore() {

        RankId rId = createTestRank();

        assertThrows(IllegalArgumentException.class, () -> {

            rankService.updateScore(rId.getUser().getEmail(), rId.getItem().getId(), 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {

            rankService.updateScore(rId.getUser().getEmail(), rId.getItem().getId(), 6);
        });
    }

    @Test
    public void testCanGetAverageRankOfItem() {

        Item item = itemService.getItem(createTestItem());

        UserEntity user1 = userService.getUser(createTestUser());
        UserEntity user2 = userService.getUser(createTestUser());
        UserEntity user3 = userService.getUser(createTestUser());


        rankService.rankItem(item, user1, 1);
        rankService.rankItem(item, user2, 3);
        rankService.rankItem(item, user3, 5);

        // (1 + 3 + 5) / 3 = 3
        double average = rankService.getAverageRank(item.getId());
        assertEquals(3.0, average);
    }

    @Test
    public void testCanGetCountOfRanks() {

        Item item = itemService.getItem(createTestItem());

        UserEntity user1 = userService.getUser(createTestUser());
        UserEntity user2 = userService.getUser(createTestUser());

        rankService.rankItem(item, user1, 1);
        rankService.rankItem(item, user2, 3);

        long count = rankService.getRankCount(item.getId());
        assertEquals(2, count);
    }

    @Test
    public void testCanGetCurrentScore() {

        Item item = itemService.getItem(createTestItem());
        UserEntity user = userService.getUser(createTestUser());

        rankService.rankItem(item, user, 3);
        assertEquals(3, rankService.getCurrentScore(item.getId(), user.getEmail()));

        rankService.rankItem(item ,user, 2);
        assertEquals(2, rankService.getCurrentScore(item.getId(), user.getEmail()));
    }
}