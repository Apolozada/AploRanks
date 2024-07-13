package apolocode.ranks.connections.transform;

import apolocode.ranks.ApoloRanks;
import apolocode.ranks.connections.model.IDatabase;
import apolocode.ranks.controller.RankController;
import apolocode.ranks.controller.UserController;
import apolocode.ranks.model.User;
import lombok.val;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTransform {

    protected ApoloRanks main;

    private final IDatabase iDatabase;

    public UserTransform(ApoloRanks main) {
        this.main = main;

        iDatabase = main.getIDatabase();
    }

    public User userTransform(ResultSet resultSet) throws SQLException {
        val user = resultSet.getString("user");
        val rankId = RankController.findRankById(resultSet.getString("rank_id"));

        return new User(user, rankId);
    }

    public void loadUsers() {
        try (val preparedStatement = iDatabase.getConnection().prepareStatement("SELECT * FROM `apoloranks_users`")) {
            val resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                val user = userTransform(resultSet);
                if (user == null) continue;

                UserController.getUsers().add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}