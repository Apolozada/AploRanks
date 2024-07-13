package apolocode.ranks.setup;

import apolocode.ranks.ApoloRanks;
import apolocode.ranks.controller.RankController;
import apolocode.ranks.model.Rank;
import apolocode.ranks.utils.DataManager;
import apolocode.ranks.utils.Format;
import apolocode.ranks.utils.ItemBuilder;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public class RankSetup {

    protected ApoloRanks main;

    private final FileConfiguration config = DataManager.getConfig("ranks");

    public RankSetup(ApoloRanks main) {
        this.main = main;

        loadRanks();
    }

    private void loadRanks() {
        int position = 1;
        val section = config.getConfigurationSection("Ranks");
        for (val path : section.getKeys(false)) {
            val key = config.getConfigurationSection("Ranks." + path);

            val name = key.getString("Name").replace("&", "§");

            val material = Material.valueOf(key.getString("Icon.Material").split(":")[0]);
            val data = Integer.parseInt(key.getString("Icon.Material").split(":")[1]);

            val cust = key.getDouble("Cust");

            val commands = key.getStringList("Commands");
            List<String> lore = key.getStringList("Icon.Lore");
            lore = lore.stream().map(l -> l.replace("{cust}", Format.formatNumber(cust))).collect(Collectors.toList());


            val icon = new ItemBuilder(material, 1, data).setName(name).setLore(lore).build();
            val rank = new Rank(path, name, icon, commands, cust, position);
            RankController.getRanks().add(rank);
            position++;
        }
    }
}