package kristiania.enterprise.exam.frontend.selenium.po;


import org.openqa.selenium.By;

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

    public int getDisplayedRankCount() {

        String displayedRankCount = getText("profileRankCount");
        return Integer.valueOf(displayedRankCount);
    }

    public int getCollectionCount() {

        return getDriver()
                .findElements(By.xpath("//div[@class='profileCollectionItem']"))
                .size();
    }

    public void updateUserDetails(String givenName, String familyName) {

        setText("profileInputGivenName", givenName);
        setText("profileInputFamilyName", familyName);

        clickAndWait("profileUpdateUserButton");
    }

    public void removeFromCollection(int index) {

        getDriver()
                .findElements(By.className("removeFromCollectionButton"))
                .get(index)
                .click();

        waitForPageToLoad();
    }

    public boolean errorDisplayed() {

        return elementIsOnPage("updateUserError");
    }

    public boolean emptyCollectionMessageDisplayed() {

        return elementIsOnPage("emptyCollectionMessage");
    }
}
