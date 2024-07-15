package apolocode.ranks.connections;

import apolocode.ranks.ApoloRanks;
import apolocode.ranks.connections.model.IDatabase;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL implements IDatabase {
    @Getter
    private Connection connection;

    private final FileConfiguration config;

    public MySQL(ApoloRanks main) {
        config = main.getConfig();

        openConnection();
        createTables();
    }

    @Override
    public void openConnection() {
        val host = config.getString("MySQL.Host");
        val user = config.getString("MySQL.User");
        val database = config.getString("MySQL.Database");
        val password = config.getString("MySQL.Password");
        val url = "jdbc:mysql://" + host + "/" + database + "?autoReconnect=true";

        try {
            connection = DriverManager.getConnection(url, user, password);
            Bukkit.getConsoleSender().sendMessage("§e[ApoloRanks] §aA conexão com §eMySQL §afoi iniciada com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        if (connection == null)
            return;

        try {
            connection.close();
            Bukkit.getConsoleSender().sendMessage("§e[ApoloRanks] §aA conexão com §eMySQL §afoi fechada com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void executeUpdate(String query, Object... params) {
        try (val preparedStatement = connection.prepareStatement(query)) {
            if (params != null && params.length > 0)
                for (int index = 0; index < params.length; index++)
                    preparedStatement.setObject(index + 1, params[index]);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getValue(String table, String column, Object value, int index) {
        String valueFinnaly = null;
        try (val preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + column + "='" + value + "'")) {
            val result = preparedStatement.executeQuery();
            if (result.next())
                valueFinnaly = result.getString(index);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valueFinnaly;
    }

    @Override
    public void createTables() {
        executeUpdate("CREATE TABLE IF NOT EXISTS `apoloranks_users` (user VARCHAR(24) NOT NULL, rank_id TEXT NOT NULL)");
    }
}