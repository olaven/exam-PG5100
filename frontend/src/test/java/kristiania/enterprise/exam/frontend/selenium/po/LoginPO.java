package kristiania.enterprise.exam.frontend.selenium.po;

public class LoginPO extends LayoutPO {

    public LoginPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Login");
    }

    public IndexPO loginUser(String email, String password) {

        setText("username", email);
        setText("password", password);

        clickAndWait("submitBtnId");

        IndexPO index = new IndexPO(this);
        if(index.isOnPage()) {
            return index;
        }

        return null;
    }
}
