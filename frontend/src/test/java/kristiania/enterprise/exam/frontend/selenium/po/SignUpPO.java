package kristiania.enterprise.exam.frontend.selenium.po;

import kristiania.enterprise.exam.frontend.selenium.PageObject;

/*
NOTE: This file is coped from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/SignUpPO.java
*/

public class SignUpPO extends LayoutPO{

    public SignUpPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {

        return getDriver().getTitle().contains("Sign Up");
    }

    public IndexPO createUser(String userName, String password){

        setText("usernameTextId", userName);
        setText("passwordTextId", password);
        clickAndWait("submitBtnId");

        IndexPO po = new IndexPO(this);
        if(po.isOnPage()){
            return po;
        }

        return null;
    }
}
