package kristiania.enterprise.exam.frontend.selenium.po;

import kristiania.enterprise.exam.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

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
        return getDriver().getTitle().contains("Welcome");
    }

    public boolean buttonToPlaceholderVisible() {

        try {
            return getDriver()
                    .findElement(By.xpath("//input[@id='goToCounterPageButton']"))
                    .isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void goToPlaceholderPage() {

        clickAndWait("goToCounterPageButton");
    }

}
