package apolocode.ranks.placeholderapi;

import apolocode.ranks.ApoloRanks;
import apolocode.ranks.controller.UserController;
import lombok.val;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class RanksHook extends PlaceholderExpansion {

    protected ApoloRanks main;

    public RanksHook(ApoloRanks main) {
        this.main = main;
    }

    @Override
    public String getIdentifier() {
        return main.getName();
    }

    @Override
    public String getAuthor() {
        return "Apolozz";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        val user = UserController.findUserByName(player.getName());
        if (user == null) return "null";

        switch (params) {
            case "rank":
                if (user.isMaxRank(user))
                    return user.getRank().getName();

                return user.getRank().getName();
            case "next_rank":
                if (user.isMaxRank(user))
                    return main.getConfig().getString("Scoreboard.Score").replace("&", "§");

                return user.getNextRank(user).getName();
        }
        return "§7-/-";
    }
}