package kristiania.enterprise.exam.backend.entity;

import kristiania.enterprise.exam.backend.Category;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Item.GET_ALL_ITEMS, query = "select item from Item item")
})
@Entity
public class Item {

    public static final String GET_ALL_ITEMS = "GET_ALL_ITEMS";

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 55)
    private String title;

    @NotBlank
    @Size(max = 355)
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rankId.item")
    private List<Rank> ranks;

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }
}
