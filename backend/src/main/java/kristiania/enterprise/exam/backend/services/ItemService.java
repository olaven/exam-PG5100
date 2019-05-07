package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.entity.Item;
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
