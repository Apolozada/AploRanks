package apolocode.ranks.listeners;

import apolocode.ranks.ApoloRanks;
import apolocode.ranks.dao.UserDAO;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final UserDAO userDAO;

    public PlayerJoinListener(ApoloRanks main) {
        userDAO = main.getUserDAO();

        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        val playerName = event.getPlayer().getName();

        userDAO.create(playerName);
    }
}