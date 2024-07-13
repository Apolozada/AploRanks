package apolocode.ranks;

import apolocode.ranks.commands.RankUpCommand;
import apolocode.ranks.commands.RanksCommand;
import apolocode.ranks.configuration.MessagesConfiguration;
import apolocode.ranks.connections.MySQL;
import apolocode.ranks.connections.SQLite;
import apolocode.ranks.connections.model.IDatabase;
import apolocode.ranks.connections.transform.UserTransform;
import apolocode.ranks.controller.UserController;
import apolocode.ranks.dao.UserDAO;
import apolocode.ranks.listeners.PlayerJoinListener;
import apolocode.ranks.listeners.inventories.ClickInventoryListener;
import apolocode.ranks.setup.RankSetup;
import apolocode.ranks.utils.DataManager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import apolocode.ranks.placeholderapi.RanksHook;

@Getter
public final class ApoloRanks extends JavaPlugin {

    @Getter
    private static ApoloRanks Instance;

    private MessagesConfiguration messagesConfiguration;

    private IDatabase iDatabase;
    private UserDAO userDAO;

    private Economy economy;

    @Override
    public void onEnable() {
        Instance = this;
        registerYaml();
        registerConnections();
        registerEvents();
        loadPlaceholder();
        setupEconomy();
        registerCommands();

        sendMessage();
    }

    @Override
    public void onDisable() {
        UserController.getUsers().forEach(userDAO::save);
        iDatabase.closeConnection();
    }

    private void registerCommands() {
        getCommand("rankup").setExecutor(new RankUpCommand(this));
        getCommand("ranks").setExecutor(new RanksCommand());
    }

    private void registerConnections() {
        iDatabase = getConfig().getBoolean("MySQL.Ativar") ? new MySQL(this) : new SQLite();
        userDAO = new UserDAO(iDatabase);

        new UserTransform(this).loadUsers();
    }

    private void registerEvents() {
        new PlayerJoinListener(this);

        new ClickInventoryListener(this);
    }

    private void loadPlaceholder() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new RanksHook(this);
            Bukkit.getConsoleSender().sendMessage("§e[ApoloRanks] §aA integração com §ePlaceholderAPI §afoi feita com sucesso.");
    }

    private void registerYaml() {
        DataManager.createFolder("cache");

        messagesConfiguration = new MessagesConfiguration();
        messagesConfiguration.loadConfig();

        DataManager.createConfig("inventories");
        DataManager.createConfig("messages");
        DataManager.createConfig("ranks");
        saveDefaultConfig();

        new RankSetup(this);
    }

    private void sendMessage() {
        Bukkit.getConsoleSender().sendMessage("§e[ApoloRanks] §fCriado por §e[Apolo]");
        Bukkit.getConsoleSender().sendMessage("§e[ApoloRanks] §aO plugin foi iniciado com sucesso.");
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> econ = getServer().getServicesManager().getRegistration(Economy.class);
        if (econ == null)
            return;

        economy = econ.getProvider();
    }
}