package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.entity.PlaceholderEntity;
import kristiania.enterprise.exam.backend.services.PlaceholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.SessionScope;

import javax.inject.Named;

@Named
@SessionScope
public class PlaceholderController {

    @Autowired
    private PlaceholderService placeholderService;

    public String goToPlaceHolderPage() {
        return "/placeholderPage.jsf?faces-redirect=true";
    }

    public String increment(Long id) {

        placeholderService.increment(id);
        return redirectToPlaceholderPage();
    }

    public String createNew() {

        placeholderService.createNew();
        return redirectToPlaceholderPage();
    }


    public Iterable<PlaceholderEntity> getAll() {
        return placeholderService.getAll();
    }

    public String delete(Long id) {

        placeholderService.delete(id);
        return redirectToPlaceholderPage();
    }

    private String redirectToPlaceholderPage() {

        return "/placeholderPage.jsf?faces-redirect=true";
    }
}
