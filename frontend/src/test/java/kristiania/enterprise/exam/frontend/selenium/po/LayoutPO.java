package kristiania.enterprise.exam.frontend.selenium.po;

import kristiania.enterprise.exam.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
NOTE: This file is coped from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/LayoutPO.java
*/

public abstract class LayoutPO extends PageObject {

    public LayoutPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public LayoutPO(PageObject other) {
        super(other);
    }

    public SignUpPO toSignUp(){

        clickAndWait("signupBtnId");

        SignUpPO po = new SignUpPO(this);

        assertTrue(po.isOnPage());

        return po;
    }

    public IndexPO doLogout(){

        clickAndWait("logoutBtnId");

        IndexPO po = new IndexPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public boolean isLoggedIn(){

        return getDriver().findElements(By.id("logoutBtnId")).size() > 0 &&
                getDriver().findElements((By.id("signupBtnId"))).isEmpty();
    }
}

