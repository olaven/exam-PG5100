package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class ItemController implements Serializable {

    @Autowired
    private ItemService itemService;
    private String selectedCategory;

    private List<Item> homePageItems;

    @PostConstruct
    private void init() {

        homePageItems = itemService.getItemsSortedByScore(null);
    }


    public List<Item> getHomePageItems() {

        return homePageItems;
    }

    public String goToItemPage(Long id) {

        return String.format("item.jsf?id=%d&faces-redirect=true", id);
    }

    public Item getItem(Long id) {

        return itemService.getItem(id);
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String filterItems() {

        // i.e. some category was chosen
        if (!selectedCategory.isEmpty()) {
            homePageItems = itemService.getItemsSortedByScore(Category.valueOf(selectedCategory));
        } else {
            homePageItems = itemService.getItemsSortedByScore(null);
        }
        
        return "/search.jsf?faces-redirect=true";
    }

    public List<Category> getAllCategories() {

        return itemService.getAllCategories();
    }
}
