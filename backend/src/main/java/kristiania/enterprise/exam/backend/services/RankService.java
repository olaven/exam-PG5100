package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class RankService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public RankId rankItem(Item item, UserEntity user, int score) {

        validateScore(score);

        if(hasRanked(item.getId(), user.getEmail())) {

            throw new IllegalStateException("user may not rank item twice");
        }

        return createRank(item, user, score);
    }

    @Transactional
    public void updateScore(RankId rankId, int score) {

        validateScore(score);

        Rank rank = entityManager.find(Rank.class, rankId);
        rank.setScore(score);
        entityManager.merge(rank);
    }

    public double getAverageRank(Long itemId) {

        Query query = entityManager.createNamedQuery(Rank.GET_AVERAGE_RANK_OF_ITEM, Double.class);
        query.setParameter("itemId", itemId);

        try {
            double result = (Double) query.getSingleResult();
            return result;
        } catch (NullPointerException e) {
            return 0; // in the case that there are no ranks registered on an item
        }
    }


    public Rank getRank(String userEmail, Long itemId) {

        Query query = entityManager.createNamedQuery(Rank.GET_BY_USER_EMAIL_AND_ITEM_ID, Rank.class);
        query.setParameter("email", userEmail);
        query.setParameter("itemId", itemId);

        List<Rank> results = (List<Rank>) query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public Rank getRank(RankId rankId) {

        return entityManager.find(Rank.class, rankId);
    }


    public boolean hasRanked(Long itemId, String userEmail) {

        Rank rank = getRank(userEmail,itemId);
        boolean ranked = rank != null;
        return ranked;
    }

    @Transactional
    public RankId createRank(Item item, UserEntity user, int score) {

        RankId rankId = new RankId();
        rankId.setItem(item);
        rankId.setUser(user);

        Rank rank = new Rank();
        rank.setRankId(rankId);
        rank.setScore(score);

        entityManager.persist(rank);
        return rankId;
    }

    private void validateScore(int score) {

        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("score has to be in range 1 to 5, inclusive");
        }
    }

}
