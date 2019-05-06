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

    @Test
    public void testCanLeaveComment() {

        RankId rankId = createTestRank();

        Rank rankBefore = rankService.getRank(rankId);
        assertNull(rankBefore.getComment());

        commentService.leaveComment(rankBefore, "test title", "test content");

        Rank rankAfter = rankService.getRank(rankId);
        assertNotNull(rankAfter.getComment());
    }

    @Test
    public void testCanUpdateComment() {

        String originalTitle = "original title";
        String originalContent = "original content";

        Rank rank = rankService.getRank(createTestRank());
        Long id = commentService.leaveComment(rank, originalTitle, originalContent);

        assertEquals(originalTitle, commentService.getComment(id).getTitle());
        assertEquals(originalContent, commentService.getComment(id).getContent());

        // UPDATING:
        String updatedTitle = "updated title";
        String updatedContent = "updated content";

        commentService.updateComment(id, updatedTitle, updatedContent);

        assertEquals(updatedTitle, commentService.getComment(id).getTitle());
        assertEquals(updatedContent, commentService.getComment(id).getContent());
    }
}