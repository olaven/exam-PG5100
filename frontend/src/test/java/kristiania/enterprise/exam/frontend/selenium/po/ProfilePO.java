package kristiania.enterprise.exam.frontend.selenium.po;


import org.testcontainers.shaded.org.apache.commons.lang.NotImplementedException;

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
}
