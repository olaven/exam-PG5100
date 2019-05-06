package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Rank;
import kristiania.enterprise.exam.backend.entity.RankId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CommentServiceTest extends ServiceTestBase {


    @Autowired
    private CommentService commentService;
    @Autowired
    private RankService rankService;

  /*  @Test
    public void testCanLeaveComment() {

        RankId rankId = createTestRank();

        Rank rankBefore = rankService.getRank(rankId);
        assertNull(rankBefore.getComment());

        commentService.createNewComment(rankBefore, "test title", "test content");

        Rank rankAfter = rankService.getRank(rankId);
        assertNotNull(rankAfter.getComment());
    }

    @Test
    public void testCanUpdateComment() {

        String originalTitle = "original title";
        String originalContent = "original content";

        Rank rank = rankService.getRank(createTestRank());
        commentService.createNewComment(rank, originalTitle, originalContent);

        assertEquals(originalTitle, commentService.getComment(rank).getTitle());
        assertEquals(originalContent, commentService.getComment(rank).getContent());

        // UPDATING:
        String updatedTitle = "updated title";
        String updatedContent = "updated content";

        commentService.updateComment(rank, updatedTitle, updatedContent);

        assertEquals(updatedTitle, commentService.getComment(rank).getTitle());
        assertEquals(updatedContent, commentService.getComment(rank).getContent());
    }
*/
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
}