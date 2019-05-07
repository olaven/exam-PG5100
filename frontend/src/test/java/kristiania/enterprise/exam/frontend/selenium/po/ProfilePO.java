package kristiania.enterprise.exam.frontend.selenium.po;


public class ProfilePO extends LayoutPO {

    public ProfilePO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Profile");
    }

    public String getDisplayedEmail() {

        return getText("profileEmail");
    }

    public String getDisplayedFullName() {

        return getText("profileFullName");
    }

    public void updateUserDetails(String givenName, String familyName) {

        setText("profileInputGivenName", givenName);
        setText("profileInputFamilyName", familyName);

        clickAndWait("profileUpdateUserButton");
    }

    public boolean errorDisplayed() {

        return elementIsOnPage("updateUserError");
    }


    public int getDisplayedRankCount() {

        String displayedRankCount = getText("profileRankCount");
        return Integer.valueOf(displayedRankCount);
    }
}
