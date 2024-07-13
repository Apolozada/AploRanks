package apolocode.ranks.inventories;

import apolocode.ranks.controller.RankController;
import apolocode.ranks.utils.DataManager;
import apolocode.ranks.utils.Scroller;
import lombok.val;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RanksInventory {

    private final FileConfiguration config = DataManager.getConfig("inventories");

    public void open(Player player) {
        val icons = new ArrayList<ItemStack>();
        RankController.getRanks().forEach(rank -> {
            icons.add(rank.getIcon());
        });

        val scroller = new Scroller.ScrollerBuilder().withName(config.getString("Inventories.Ranks.Name")).withSize(config.getInt("Inventories.Ranks.Size")).withAllowedSlots(config.getIntegerList("Inventories.Ranks.Slots")).withArrowsSlots(18, 26).withItems(icons).build();
        scroller.open(player);
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
    }
}