package apolocode.ranks.connections;

import apolocode.ranks.connections.model.IDatabase;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite implements IDatabase {

    @Getter
    private Connection connection;

    public SQLite() {
        openConnection();
        createTables();
    }

    @Override
    public void openConnection() {
        val file = new File("plugins/ApoloRanks/cache/database.db");
        val url = "jdbc:sqlite:" + file;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            Bukkit.getConsoleSender().sendMessage("§e[ApoloRanks] §aA conexão com §eSQLite §afoi iniciada com sucesso.");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        if (connection == null)
            return;

        try {
            connection.close();
            Bukkit.getConsoleSender().sendMessage("§e[ApoloRanks] §aA conexão com §eSQLite §afoi fechada com sucesso.");

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