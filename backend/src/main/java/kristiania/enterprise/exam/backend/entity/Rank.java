package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NamedQueries({
        @NamedQuery(name = Rank.GET_RANK_BY_USER_EMAIL_AND_ITEM_ID, query = "select rank from Rank rank where rank.rankId.user.email = :userEmail and rank.rankId.item.id = :itemId"),
        @NamedQuery(name = Rank.GET_AVERAGE_RANK_OF_ITEM, query = "select avg(rank.score) from Rank rank where rank.rankId.item.id = :itemId"),
        @NamedQuery(name = Rank.GET_COUNT_BY_ITEM_ID, query = "select count(rank) from Rank rank where rank.rankId.item.id = :itemId"),
        @NamedQuery(name = Rank.GET_SCORE_BY_ITEM_ID_AND_USER_EMAIL, query = "select rank.score from Rank rank where rank.rankId.item.id = :itemId and rank.rankId.user.email = :userEmail")
})
@Entity
public class Rank {

    public static final String GET_RANK_BY_USER_EMAIL_AND_ITEM_ID = "GET_RANK_BY_USER_EMAIL_AND_ITEM_ID";
    public static final String GET_AVERAGE_RANK_OF_ITEM = "GET_AVERAGE_RANK_OF_ITEM";
    public static final String GET_COUNT_BY_ITEM_ID = "GET_COUNT_BY_ITEM_ID";
    public static final String GET_SCORE_BY_ITEM_ID_AND_USER_EMAIL = "GET_SCORE_BY_ITEM_ID_AND_USER_EMAIL";

    @EmbeddedId
    private RankId rankId;

    @Min(1)
    @Max(5)
    private Integer score;

    @OneToOne(mappedBy = "rank")
    private Comment comment;

    public Rank() {
    }


    public RankId getRankId() {
        return rankId;
    }

    public void setRankId(RankId rankId) {
        this.rankId = rankId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
