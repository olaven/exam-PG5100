package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.entity.Item;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class ItemService {

    @PersistenceContext
    private EntityManager entityManager;

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
}
