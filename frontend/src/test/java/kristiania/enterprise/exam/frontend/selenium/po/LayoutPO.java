package kristiania.enterprise.exam.frontend.selenium.po;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
NOTE: This file is adapted from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/LayoutPO.java
*/

public abstract class LayoutPO extends PageObject {

    public LayoutPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public LayoutPO(PageObject other) {
        super(other);
    }


    public IndexPO doLogout() {

        clickAndWait("logoutBtnId");

        IndexPO po = new IndexPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public boolean isLoggedIn() {

        return getDriver().findElements(By.id("logoutBtnId")).size() > 0 &&
                getDriver().findElements((By.id("signupBtnId"))).isEmpty();
    }

    public SignUpPO toSignUp() {

        SignUpPO po = goToPage("signupBtnId", new SignUpPO(this));
        return po;
    }

    public LoginPO toLogin() {

        LoginPO po = goToPage("loginBtnId", new LoginPO(this));
        return po;
    }

    public IndexPO toHome() {

        IndexPO po = goToPage("goHomeButton", new IndexPO(this));
        return po;
    }

    public ProfilePO toProfile() {

        ProfilePO po = goToPage("goToProfilePageButton", new ProfilePO(this));
        return po;
    }

    public AdminPO goToAdmin() {

        AdminPO po = goToPage("goToAdminPageButton", new AdminPO(this));
        return po;
    }

    private<T extends PageObject> T goToPage(String buttonId, T po) {

        clickAndWait(buttonId);
        assertTrue(po.isOnPage());
        return po;
    }

    protected boolean elementIsOnPage(String id) {

        try {

            getDriver()
                    .findElement(By.id(id));

            return true;
        } catch (NoSuchElementException e) {

            return false;
        }
    }
}

