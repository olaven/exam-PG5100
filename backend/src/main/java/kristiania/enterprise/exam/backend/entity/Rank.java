package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = Rank.GET_BY_USER_EMAIL_AND_ITEM_ID, query = "select rank from Rank rank where rank.rankId.user.email = :email and rank.rankId.item.id = :itemId"),
        @NamedQuery(name = Rank.GET_AVERAGE_RANK_OF_ITEM, query = "select avg(rank.score) from Rank rank where rank.rankId.item.id = :itemId")
})
@Entity
public class Rank {

    public static final String GET_BY_USER_EMAIL_AND_ITEM_ID = "GET_BY_USER_EMAIL_AND_ITEM_ID";
    public static final String GET_AVERAGE_RANK_OF_ITEM = "GET_AVERAGE_RANK_OF_ITEM";

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
