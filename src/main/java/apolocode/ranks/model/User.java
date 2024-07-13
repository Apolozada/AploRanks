package apolocode.ranks.model;

import apolocode.ranks.controller.RankController;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {

    private String user;
    private Rank rank;

    public Rank getNextRank(User user) {
        return RankController.findRankByPostion(user.getRank().getPosition() + 1);
    }

    public boolean isMaxRank(User user) {
        return getNextRank(user) == null;
    }
}