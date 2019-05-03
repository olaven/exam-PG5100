package kristiania.enterprise.exam.frontend.selenium;

import kristiania.enterprise.exam.frontend.selenium.po.IndexPO;
import kristiania.enterprise.exam.frontend.selenium.po.SignUpPO;
import kristiania.enterprise.exam.frontend.selenium.po.ui.PlaceholderPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

/*
NOTE: Parts of this file is copied from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/SeleniumTestBase.java
*/

public abstract class SeleniumTestBase {


    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement();
    }


    private IndexPO home;
    private PlaceholderPO placeholderPO;


    private IndexPO createNewUser(String username, String password) {

        home.toStartingPage();

        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO = signUpPO.createUser(username, password);
        assertNotNull(indexPO);

        return indexPO;
    }

    private void goToPlaceholderPage() {

        createNewUser(getUniqueId(), "password123");
        home.goToPlaceholderPage();
    }

    @BeforeEach
    public void initTest() {

        /*
            we want to have a new session, otherwise the tests
            will share the same Session beans
         */
        getDriver().manage().deleteAllCookies();

        home = new IndexPO(getDriver(), getServerHost(), getServerPort());
        placeholderPO = new PlaceholderPO(home);

        home.toStartingPage();

        assertTrue(home.isOnPage(), "Failed to start from Home Page");
    }

    @Test
    public void testCreateAndLogoutUser() {

        assertFalse(home.isLoggedIn());

        String username = getUniqueId();
        String password = "123456789";
        home = createNewUser(username, password);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(username));

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(username));
    }

    @Test
    public void placeholderButtonVisibleWhenLoggedIn() {

        assertFalse(home.buttonToPlaceholderVisible());
        createNewUser(getUniqueId(), "password");
        assertTrue(home.buttonToPlaceholderVisible());
    }

    @Test
    public void canGoToPlachoderPage() {

        goToPlaceholderPage();
        assertTrue(placeholderPO.isOnPage());
    }

    @Test
    public void canAddItem() {

        goToPlaceholderPage();

        int before = placeholderPO.getDisplayedCount();
        placeholderPO.addNew();
        int after = placeholderPO.getDisplayedCount();

        assertEquals(before + 1, after);
    }

    @Test
    public void canDeleteItem() {

        goToPlaceholderPage();

        placeholderPO.addNew();
        int before = placeholderPO.getDisplayedCount();

        placeholderPO.delete(0);
        int after = placeholderPO.getDisplayedCount();

        assertEquals(before - 1, after);
    }

}
