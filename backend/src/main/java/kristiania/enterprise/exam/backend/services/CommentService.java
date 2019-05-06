package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Comment;
import kristiania.enterprise.exam.backend.entity.Rank;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class CommentService {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public Long leaveComment(Rank rank, String title, String content) {

        Comment comment = new Comment();
        comment.setTitle(title);
        comment.setContent(content);
        comment.setRank(rank);

        entityManager.persist(comment);
        return comment.getId();
    }

    @Transactional
    public void updateComment(Long id, String title, String content) {

        Comment comment = getComment(id);
        comment.setTitle(title);
        comment.setContent(content);

        entityManager.merge(comment);
    }

    public Comment getComment(Long id) {

        return entityManager.find(Comment.class, id);
    }
}
