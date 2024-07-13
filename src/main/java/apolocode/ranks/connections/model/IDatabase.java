package apolocode.ranks.connections.model;

import java.sql.Connection;

public interface IDatabase {

    Connection getConnection();

    void openConnection();

    void closeConnection();

    void executeUpdate(String query, Object... values);

    String getValue(String table, String column, Object value, int index);

    void createTables();

}