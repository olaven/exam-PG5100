package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class RankService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public RankId rankItem(Item item, UserEntity user, int score) {

        validateScore(score);

        if(hasRanked(user.getEmail(), item.getId())) {

            return updateScore(user.getEmail(), item.getId(), score);
        }

        return createRank(item, user, score);
    }

    @Transactional
    public RankId updateScore(String userEmail, Long  itemId, int score) {

        validateScore(score);

        Rank rank = (Rank) entityManager.createNamedQuery(Rank.GET_RANK_BY_USER_EMAIL_AND_ITEM_ID)
                .setParameter("userEmail", userEmail)
                .setParameter("itemId", itemId)
                .getResultList()
                .get(0);

        rank.setScore(score);
        entityManager.merge(rank);
        return rank.getRankId();
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

    public long getRankCount(Long itemId) {

        Query query = entityManager.createNamedQuery(Rank.GET_COUNT_BY_ITEM_ID, Long.class);
        query.setParameter("itemId", itemId);

        long result = (Long) query.getSingleResult();
        return result;
    }


    public Rank getRank(String userEmail, Long itemId) {

        Query query = entityManager.createNamedQuery(Rank.GET_RANK_BY_USER_EMAIL_AND_ITEM_ID, Rank.class);
        query.setParameter("userEmail", userEmail);
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


    public boolean hasRanked(String userEmail, Long itemId) {

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

    public int getCurrentScore(Long itemId, String userEmail) {

        Query query = entityManager.createNamedQuery(Rank.GET_SCORE_BY_ITEM_ID_AND_USER_EMAIL, Integer.class);
        query.setParameter("itemId", itemId);
        query.setParameter("userEmail", userEmail);

        int result = (Integer) query.getSingleResult();
        return result;
    }
}
