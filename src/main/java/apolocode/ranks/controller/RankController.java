package apolocode.ranks.controller;

import apolocode.ranks.model.Rank;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class RankController {

    @Getter
    private final static List<Rank> ranks = new ArrayList<>();

    public static Rank findRankById(String rankId) {
        return ranks.stream().filter(rank -> rank.getId().equalsIgnoreCase(rankId)).findFirst().orElse(null);
    }

    public static Rank findRankByPostion(int position) {
        return ranks.stream().filter(rank -> rank.getPosition() == position).findFirst().orElse(null);
    }
}