package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.entity.Comment;
import kristiania.enterprise.exam.backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;
import java.util.List;

@RequestScope
@Named
public class CommentController {

    @Autowired
    private UserController userController;

    @Autowired
    private CommentService commentService;


    private String currentTitle;
    private String currentContent;


    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }

    public String getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(String currentContent) {
        this.currentContent = currentContent;
    }

    public String createNewComment(Long itemId) {

        String userEmail = userController.getEmail();
        String url = String.format("/item.jsf?itemId=%d&faces-redirect=true", itemId);
        if(currentTitle.isEmpty() || currentContent.isEmpty()) {
            return url + "&error=true";
        }

        commentService.createComment(userEmail, itemId, currentTitle, currentContent);
        return url;
    }

    public List<Comment> getCommentByItem(Long itemId) {

        return commentService.getCommentsByItem(itemId);
    }
}
