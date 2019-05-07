package kristiania.enterprise.exam.frontend.selenium.po;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ItemPO extends LayoutPO {

    public ItemPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Item");
    }

    public String getHeaderText() {

        return getText("itemHeader");
    }

    public double getAverageScore() {

        String displayedRating = getText("itemRating");
        String average = displayedRating.split(" ")[1];
        return Double.valueOf(average);
    }

    public int getUserCountBehindAverage() {

        String displayedRating = getText("itemRating");
        String userCount = displayedRating.split(" ")[4];
        return Integer.valueOf(userCount);
    }

    public int getDisplayedScore() {

        String displayedScore = getText("itemScore");
        String score = displayedScore.split("Your current score: ")[1];
        return Integer.valueOf(score);
    }


    public int getCommentCount() {

        return getDriver()
                .findElements(By.className("itemComment"))
                .size();
    }

    public void setScore(int score) {

        List<WebElement> buttons = getDriver().findElements(By.className("scoreFormButton"));
        WebElement button = buttons.get(score - 1);

        button.click();
        waitForPageToLoad();
    }


    public void createComment(String title, String content) {

        setText("commentFormTitle", title);
        setText("commentFormContent", content);
        clickAndWait("commentFormButton");
    }


    public boolean scoreFormIsDisplayed() {

        return elementIsOnPage("scoreForm");
    }

    public boolean commentFormIsDisplayed() {

        return elementIsOnPage("commentForm");
    }

    public boolean commentsAreDisplayed() {

        return elementIsOnPage("itemComments");
    }

    private boolean elementIsOnPage(String id) {

        try {

            getDriver()
                    .findElement(By.id(id));

            return true;
        } catch (NoSuchElementException e) {

            return false;
        }
    }

}
