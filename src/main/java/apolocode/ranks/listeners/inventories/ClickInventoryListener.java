package apolocode.ranks.listeners.inventories;

import apolocode.ranks.ApoloRanks;
import apolocode.ranks.utils.DataManager;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickInventoryListener implements Listener {

    private final FileConfiguration config = DataManager.getConfig("inventories");

    public ClickInventoryListener(ApoloRanks main) {
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    void onClick(InventoryClickEvent event) {
        val title = event.getView().getTitle();
        if (!title.equals(config.getString("Inventories.Ranks.Name"))) return;
        event.setCancelled(true);
    }
}