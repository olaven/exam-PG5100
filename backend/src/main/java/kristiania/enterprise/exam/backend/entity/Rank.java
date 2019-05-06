package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NamedQueries(
        @NamedQuery(name = Rank.GET_BY_USER_EMAIL_AND_ITEM_ID, query = "select rank from Rank rank where rank.rankId.user.email = :email and rank.rankId.item.id = :id")
)
@Entity
public class Rank {

    public static final String GET_BY_USER_EMAIL_AND_ITEM_ID = "GET_BY_USER_EMAIL_AND_ITEM_ID";

    @EmbeddedId
    private RankId rankId;

    @Min(1)
    @Max(5)
    private Integer score;

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
}
