package apolocode.ranks.configuration;

import apolocode.ranks.utils.DataManager;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class MessagesConfiguration {

    private final FileConfiguration config = DataManager.getConfig("messages");

    private boolean actionBar;

    private String noHaveMoney, lastRank, upNextRank;

    public void loadConfig() {

        actionBar = config.getBoolean("Messages.Actionbar");

        noHaveMoney = getMessage("NoHaveMoney");
        lastRank = getMessage("LastRank");
        upNextRank = getMessage("UpNextRank");

    }

    private String getMessage( String key) {
        return config.getString("Messages.Players." + key);
    }
}