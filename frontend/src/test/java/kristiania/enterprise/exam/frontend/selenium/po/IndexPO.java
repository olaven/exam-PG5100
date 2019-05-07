package kristiania.enterprise.exam.frontend.selenium.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

/*
NOTE: Parts of this file is copied from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/IndexPO.java
*/

public class IndexPO extends LayoutPO {


    public IndexPO(PageObject other) {
        super(other);
    }

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public void toStartingPage(){
        getDriver().get(host + ":" + port);
    }

    @Override
    public boolean isOnPage() {
        String string = getDriver().getTitle();
        System.out.println(string);
        return getDriver().getTitle().contains("Welcome");
    }

    public ItemPO goToItemPage(int index) {

        getDriver()
                .findElements(By.xpath("//div[@class='homeItem']//input[@type='submit']"))
                .get(index)
                .click();

        waitForPageToLoad();
        ItemPO po = new ItemPO(this);
        assertTrue(po.isOnPage());

        return po;
    }
}
