package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RankService rankService;
    @Autowired
    private UserService userService;

    @Transactional
    public Long createItem(String title, String description, Category category) {

        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setCategory(category);
        item.setRanks(new ArrayList<>());

        entityManager.persist(item);
        return item.getId();
    }

    @Transactional
    public void removeItem(Long itemId) {

        Item item = entityManager.find(Item.class, itemId);

        /*
        NOTE: This solution of manually removing orphans is suboptimal.
        However, I stumbled upon many bugs/issues using orphanRemoval / cascade with @OneToMany.
        In the end, I decided that deleting explicitly was the cleanest, available solution.

        unsolved issue:
        https://hibernate.atlassian.net/browse/HHH-6709
        */
        item.getRanks().forEach(rank -> {
            if(rank.getComment() != null)
                entityManager.remove(rank.getComment());
            entityManager.remove(rank);
        });


        entityManager.remove(item);
    }

    public Item getItem(Long id) {

        return entityManager.find(Item.class, id);
    }

    public List<Item> getItemsSortedByAverageScore(Category category) {

        return getAllItems().stream()
                .filter(item -> {
                    if(category != null) {
                        return item.getCategory().equals(category);
                    }
                    return true;
                })
                .sorted((first, second) -> {

                    Double firstScore = rankService.getAverageRank(first.getId());
                    Double secondScore = rankService.getAverageRank(second.getId());
                    return secondScore.compareTo(firstScore);
                })
                .collect(Collectors.toList());
    }

    private List<Item> getAllItems() {

        Query query = entityManager.createNamedQuery(Item.GET_ALL_ITEMS, Item.class);
        return query.getResultList();
    }

    public List<Category> getAllCategories() {

        return Arrays.stream(Category.values())
                .collect(Collectors.toList());
    }
}
