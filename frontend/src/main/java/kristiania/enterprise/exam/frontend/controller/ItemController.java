package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@RequestScope
@Named
public class ItemController implements Serializable {

    @Autowired
    private ItemService itemService;
    private String selectedCategory;

    private List<Item> homePageItems;

    @PostConstruct
    private void init() {

        homePageItems = itemService.getItemsSortedByAverageScore(null);
    }


    public String filterItems() {

        // i.e. some category was chosen
        if (!selectedCategory.isEmpty()) {
            homePageItems = itemService.getItemsSortedByAverageScore(Category.valueOf(selectedCategory));
        } else {
            homePageItems = itemService.getItemsSortedByAverageScore(null);
        }

        return "/search.jsf?faces-redirect=true";
    }


    public String goToItemPage(Long id) {

        return String.format("item.jsf?itemId=%d&faces-redirect=true", id);
    }

    public Item getItem(Long id) {

        return itemService.getItem(id);
    }

    public List<Item> getAllItems() {

        return itemService.getItemsSortedByAverageScore(null);
    }

    public List<Item> getHomePageItems() {

        return homePageItems;
    }

    public String getSelectedCategory() {
        
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        
        this.selectedCategory = selectedCategory;
    }


    public List<Category> getAllCategories() {

        return itemService.getAllCategories();
    }
}
