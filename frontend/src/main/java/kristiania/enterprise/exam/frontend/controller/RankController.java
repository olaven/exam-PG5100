package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.services.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;

@RequestScope
@Named
public class RankController {

    @Autowired
    private RankService rankService;

    public double getGetAverageScoreOf(Long itemId) {

        return rankService.getAverageRank(itemId);
    }
}
