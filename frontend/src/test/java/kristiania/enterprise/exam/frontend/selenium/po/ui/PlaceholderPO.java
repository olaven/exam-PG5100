package kristiania.enterprise.exam.frontend.selenium.po.ui;

import kristiania.enterprise.exam.frontend.selenium.PageObject;
import kristiania.enterprise.exam.frontend.selenium.po.LayoutPO;
import org.openqa.selenium.By;

public class PlaceholderPO extends LayoutPO {

    public PlaceholderPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Placeholder page");
    }

    public void addNew() {

        clickAndWait("createButton");
    }

    public void delete(int position) {

       getDriver()
               .findElements(By.xpath("//td/form/input[@class='deleteButton btn btn-danger']"))
               .get(position)
               .click();

       waitForPageToLoad();
    }

    public void increment(int position) {

        getDriver()
                .findElements(By.xpath("//input[@class='incrementButton btn btn-primary']"))
                .get(position)
                .click();

        waitForPageToLoad();
    }

    public int getCounterOf(int position) {


        String text = getDriver()
                .findElements(By.xpath("//td/span[@class='counter']"))
                .get(position)
                .getText();

        return Integer.valueOf(text);
    }

    public int getDisplayedCount() {

        return getDriver()
                .findElements(By.xpath("//table[@id='placeholderTable']/tbody/tr"))
                .size();
    }

}
