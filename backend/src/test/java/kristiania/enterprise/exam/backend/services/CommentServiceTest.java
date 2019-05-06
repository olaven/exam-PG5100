package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Comment;
import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.Rank;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CommentServiceTest extends ServiceTestBase {


    @Autowired
    private CommentService commentService;
    @Autowired
    private RankService rankService;

    @Test
    public void testCreatesNewCommentIfNotPresent() {

        Rank rank = rankService.getRank(createTestRank());
        assertNull(commentService.getComment(rank));

        // slightly convoluted, but using userEmail and itemId makes for cleaner frontend-controller
        String userEmail = rank.getRankId().getUser().getEmail();
        Long itemId = rank.getRankId().getItem().getId();

        commentService.createComment(userEmail, itemId, "test title", "test content");
        assertNotNull(commentService.getComment(rank));
    }

    @Test
    public void testUpdatesCommentIfAlreadyPresent() {

        Rank rank = rankService.getRank(createTestRank());
        String userEmail = rank.getRankId().getUser().getEmail();
        Long itemId = rank.getRankId().getItem().getId();


        String updatedTitle = "updated title";
        String updatedContent = "updatedContent";


        commentService.createComment(userEmail, itemId, "original title", "original content");
        assertNotEquals(updatedTitle, commentService.getComment(rank).getTitle());
        assertNotEquals(updatedContent, commentService.getComment(rank).getContent());

        commentService.createComment(userEmail, itemId, updatedTitle, updatedContent);
        assertEquals(updatedTitle, commentService.getComment(rank).getTitle());
        assertEquals(updatedContent, commentService.getComment(rank).getContent());
    }

    @Test
    public void testCanGetCommentByItem() {

        int n = 3;
        Long itemId = createTestItem();
        Item item = itemService.getItem(itemId);

        for (int i = 0; i < n; i++) {

            String userEmail = createTestUser();
            UserEntity user = userService.getUser(userEmail);

            rankService.rankItem(item, user, 4);
            commentService.createComment(userEmail, itemId, "comment title", "comment content");
        }

        assertEquals(n, commentService.getCommentsByItem(itemId).size());
    }
}