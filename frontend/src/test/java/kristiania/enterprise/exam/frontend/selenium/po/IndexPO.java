package kristiania.enterprise.exam.frontend.selenium.po;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.entity.Item;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;

/*
NOTE: Parts of this file is adapted from:
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
        return getDriver().getTitle().contains("Welcome");
    }

    public ItemPO toItemPage(int index) {

        getDriver()
                .findElements(By.xpath("//div[@class='homeItem']//input[@type='submit']"))
                .get(index)
                .click();

        waitForPageToLoad();
        ItemPO po = new ItemPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public boolean displayedItemsMatches(List<Item> items) {

        List<String> displayed = extractItemTitles();
        List<String> expected = items.stream().map(Item::getTitle).collect(Collectors.toList());

        return displayed.equals(expected);
    }

    public boolean itemsInSameOrderAs(List<Item> allItems) {

        List<String> displayed = extractItemTitles();
        for (int i = 0; i < displayed.size(); i++) {

            String expected = allItems.get(i).getTitle();
            String actual = displayed.get(i);

            if(!actual.equals(expected)) {
                return false;
            }
        }

        return true;
    }

    public boolean itemsSortedByScore() {

        List<Double> scores = extractItemScores();
        return scores.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList())
                .equals(scores);
    }

    public void selectCategory(Category category) {

        Select select = new Select(getDriver().findElement(By.id("categorySelect")));
        select.selectByVisibleText(category.toString());

        doFilter();
    }

    public void doFilter() {
        clickAndWait("doFilter");
    }

    private List<String> extractItemTitles() {

        return getDriver()
                .findElements(By.xpath("//div[@class='homeItem']//h2"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private List<Double> extractItemScores() {

        return getDriver()
                .findElements(By.xpath("//span[@class='homeAverageScore']"))
                .stream()
                .map(WebElement::getText)
                .map(Double::valueOf)
                .collect(Collectors.toList());
    }

}
