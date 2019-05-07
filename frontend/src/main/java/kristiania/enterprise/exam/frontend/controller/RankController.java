package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import kristiania.enterprise.exam.backend.services.ItemService;
import kristiania.enterprise.exam.backend.services.RankService;
import kristiania.enterprise.exam.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;
import java.io.Serializable;

@RequestScope
@Named
public class RankController implements Serializable {

    @Autowired
    private RankService rankService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserInfoController userInfoController;



    public boolean hasRanked(Long itemId) {

        String userEmail = userInfoController.getEmail();
        return rankService.hasRanked(userEmail, itemId);
    }

    public String removeRank(Long itemId) {

        String userEmail = userInfoController.getEmail();
        rankService.removeRank(userEmail, itemId);
        return String.format("/item.jsf?itemId=%d&faces-redirect=true", itemId);
    }

    public String setNewScore(int score, Long itemId) {

        String userEmail = userInfoController.getEmail();

        UserEntity user = userService.getUser(userEmail);
        Item item = itemService.getItem(itemId);
        rankService.rankItem(item, user, score);

        return String.format("/item.jsf?itemId=%d&faces-redirect=true", itemId);
    }

    public double getGetAverageScoreOf(Long itemId) {

        return rankService.getAverageRank(itemId);
    }

    public long getGetRateCountOf(Long itemId) {

        return rankService.getRankCount(itemId);
    }

    public int getCurrentScore(Long itemId) {

        String userEmail = userInfoController.getEmail();
        return rankService.getCurrentScore(itemId, userEmail);
    }

}
