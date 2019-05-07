package kristiania.enterprise.exam.frontend.selenium.po;

import kristiania.enterprise.exam.backend.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AdminPO extends LayoutPO {

    public AdminPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Admin");
    }

    public void addItem(String title, String description, Category category) {

        Select select = new Select(getDriver().findElement(By.id("categorySelect")));
        
        setText("adminTitleInput", title);
        setText("adminDescriptionInput", description);
        select.selectByVisibleText(category.toString());

        clickAndWait("adminAddItemButton");
    }

    public void removeItemAt(int index) {

        WebElement button = getDriver()
                .findElements(By.className("adminRemoveItemButton"))
                .get(index);

        button.click();
        waitForPageToLoad();
    }

    public boolean addItemErrorDisplayed() {

        return elementIsOnPage("addItemError"); 
    }
}
