package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Comment;
import kristiania.enterprise.exam.backend.entity.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RankService rankService;

    @Transactional
    public void createComment(String userEmail, Long itemId, String title, String content) {

        // leave a new comment if user has no rank on the item. Update otherwise.
        Rank rank = rankService.getRank(userEmail, itemId);
        if (hasCommented(rank)) {

            updateComment(rank, title, content);
        } else {

            createNewComment(rank, title, content);
        }
    }

    public Comment getComment(Rank rank) {

        Query query = entityManager.createNamedQuery(Comment.GET_COMMENT_BY_USER_EMAIL_AND_ITEM_ID, Comment.class);
        query.setParameter("userEmail", rank.getRankId().getUser().getEmail());
        query.setParameter("itemId", rank.getRankId().getItem().getId());

        try {

            return (Comment) query.getResultList().get(0);
        } catch (IndexOutOfBoundsException e) {

            return null; // comment does not exist
        }
    }

    private void createNewComment(Rank rank, String title, String content) {

        Comment comment = new Comment();
        comment.setTitle(title);
        comment.setContent(content);
        comment.setRank(rank);

        entityManager.persist(comment);
    }

    private void updateComment(Rank rank, String title, String content) {

        Comment comment = getComment(rank);
        comment.setTitle(title);
        comment.setContent(content);

        entityManager.merge(comment);
    }


    private boolean hasCommented(Rank rank) {

        Comment comment = getComment(rank);
        return comment != null;
    }

    public List<Comment> getCommentsByItem(Long itemId) {

        Query query = entityManager.createNamedQuery(Comment.GET_COMMENTS_BY_ITEM, Comment.class);
        query.setParameter("itemId", itemId);

        return  query.getResultList();
    }
}
