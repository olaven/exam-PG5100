package kristiania.enterprise.exam.frontend.selenium;

import kristiania.enterprise.exam.frontend.selenium.po.IndexPO;
import kristiania.enterprise.exam.frontend.selenium.po.ItemPO;
import kristiania.enterprise.exam.frontend.selenium.po.SignUpPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.atomic.AtomicInteger;

import static kristiania.enterprise.exam.frontend.selenium.po.PageObject.getUniqueId;
import static org.junit.jupiter.api.Assertions.*;

/*
NOTE: Parts of this file is adapted from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/SeleniumTestBase.java
*/

public abstract class SeleniumTestBase {


    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement() + "@mail.com";
    }


    private IndexPO home;


    private IndexPO createNewUser(String email, String givenName, String familyName, String password) {

        home.toStartingPage();

        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO = signUpPO.createUser(email, givenName, familyName, password);
        assertNotNull(indexPO);

        return indexPO;
    }


    @BeforeEach
    public void initTest() {

        /*
            we want to have a new session, otherwise the tests
            will share the same Session beans
         */
        getDriver().manage().deleteAllCookies();

        home = new IndexPO(getDriver(), getServerHost(), getServerPort());

        home.toStartingPage();

        assertTrue(home.isOnPage(), "Failed to start from Home Page");
    }

    // auth-tests:
    @Test
    public void testCreateAndLogoutUser() {

        assertFalse(home.isLoggedIn());

        String email = getUniqueId();
        String givenName = "given";
        String familyName = "family";
        String password = "123456789";
        home = createNewUser(email, givenName, familyName, password);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(email));

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(email));
    }


    // Item tests:
    @Test
    public void testDoesShowHeader() {

        ItemPO item = home.goToItemPage(0);

        String header = item.getHeaderText();
        String suffix = "- Item details";

        assertTrue(header.contains(suffix));
        assertTrue(header.length() > suffix.length());
    }

    @Test
    public void testCommentsVisibleWhenLoggedIn() {

        home = createNewUser(getUniqueId(), "given", "family", "1234");
        ItemPO item = home.goToItemPage(0);

        assertTrue(item.isLoggedIn());
        assertTrue(item.commentsAreDisplayed());
    }

    @Test
    public void testCommentsVisibleWhenLoggedOut() {

        ItemPO item = home.goToItemPage(0);

        assertFalse(item.isLoggedIn());
        assertTrue(item.commentsAreDisplayed());
    }

    @Test
    public void testScoringFormNotVisibleWhenLoggedOut() {

        ItemPO item = home.goToItemPage(0);

        assertFalse(item.isLoggedIn());
        assertFalse(item.scoreFormIsDisplayed());
    }

    @Test
    public void testScoringFormVisibleWhenLoggedIn() {

        home = createNewUser(getUniqueId(), "given", "family", "1234");
        ItemPO item = home.goToItemPage(0);

        assertTrue(item.isLoggedIn());
        assertTrue(item.scoreFormIsDisplayed());
    }

    @Test
    public void testCommentFormNotVisibleBeforeScore() {

        home = createNewUser(getUniqueId(), "given", "family", "12345");
        ItemPO item = home.goToItemPage(0);

        assertFalse(item.commentFormIsDisplayed());
    }

    @Test
    public void testCommentFormVisibleAfterScore() {

        home = createNewUser(getUniqueId(), "given", "family", "12345");
        ItemPO item = home.goToItemPage(0);
        item.setScore(3);

        assertTrue(item.commentFormIsDisplayed());
    }

    @Test
    public void testScoreGetsUpdated() {

        home = createNewUser(getUniqueId(), "given", "family", "12345");
        ItemPO item = home.goToItemPage(0);

        item.setScore(2);
        assertEquals(2, item.getDisplayedScore());

        item.setScore(4);
        assertEquals(4, item.getDisplayedScore());

        item.setScore(1);
        assertEquals(1, item.getDisplayedScore());
    }

    @Test
    public void testCommentGetsAdded() {

        home = createNewUser(getUniqueId(), "given", "family", "12345");
        ItemPO item = home.goToItemPage(0);

        int before = item.getCommentCount();
        //NOTE: The user is created above. Therefore, there is no existing rank

        item.setScore(1);
        item.createComment("My comment", "More content in comment");

        assertEquals(before + 1, item.getCommentCount());
    }

    @Test
    public void testUserBehindAverageGetsUpdated() {

        home = createNewUser(getUniqueId(), "given", "family", "12345");
        ItemPO item = home.goToItemPage(0);

        int before = item.getUserCountBehindAverage();
        item.setScore(5);
        int after = item.getUserCountBehindAverage();

        assertEquals(before + 1, after);
    }

    @Test
    public void testAverageScoreGetsUpdated() {

        home = createNewUser(getUniqueId(), "given", "family", "12345");
        ItemPO item = home.goToItemPage(0);

        double before = item.getAverageScore();
        item.setScore(5);
        double after = item.getAverageScore();

        assertNotEquals(before, after);
    }
}
