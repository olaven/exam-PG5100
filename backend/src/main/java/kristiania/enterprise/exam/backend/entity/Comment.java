package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NamedQueries({
        @NamedQuery(
                name = Comment.GET_COMMENT_BY_USER_EMAIL_AND_ITEM_ID,
                query = "select comment from Comment comment where comment.rank.rankId.user.email = :userEmail and comment.rank.rankId.item.id = :itemId"
        ),
        @NamedQuery(name = Comment.GET_COMMENTS_BY_ITEM, query = "select comment from Comment comment where comment.rank.rankId.item.id = :itemId")
})
@Entity
public class Comment {

    public static final String GET_COMMENT_BY_USER_EMAIL_AND_ITEM_ID = "GET_COMMENT_BY_USER_EMAIL_AND_ITEM_ID";
    public static final String GET_COMMENTS_BY_ITEM = "GET_COMMENTS_BY_ITEM";
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Rank rank;

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String content;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
