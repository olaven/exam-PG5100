package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;

@RequestScope
@Named
public class AdminController {

    @Autowired
    private ItemService itemService;

    private String currentTitle;
    private String currentDescription;
    private String currentCategory;


    public String addItem() {

        String url = "/admin.jsf?faces-redirect=true";

        if (
            currentTitle.isEmpty() ||
            currentDescription.isEmpty() ||
            currentCategory.isEmpty()) {

            return url + "&error=true";
        } else {

            Category category = Category.valueOf(currentCategory);
            itemService.createItem(currentTitle, currentDescription, category);
            return url;
        }
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }

    public String getCurrentDescription() {
        return currentDescription;
    }

    public void setCurrentDescription(String currentDescription) {
        this.currentDescription = currentDescription;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    public String removeItem(Long itemId) {

        itemService.removeItem(itemId);
        return "/admin.jsf?faces-redirect=true";
    }
}
