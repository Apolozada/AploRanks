package apolocode.ranks.dao;

import apolocode.ranks.connections.model.IDatabase;
import apolocode.ranks.controller.RankController;
import apolocode.ranks.controller.UserController;
import apolocode.ranks.model.User;
import lombok.val;
import org.bukkit.Bukkit;

public class UserDAO {

    private final IDatabase iDatabase;

    public UserDAO(IDatabase iDatabase) {
        this.iDatabase = iDatabase;
    }

    public void create(String userName) {
        val users = UserController.findUserByName(userName);
        if (users != null) return;

        val firstRank = RankController.findRankByPostion(1);
        val rank = firstRank == null ? "null" : firstRank.getId();

        if (rank == null) return;

        val user = new User(userName, firstRank);
        UserController.getUsers().add(user);
        try {
            iDatabase.executeUpdate("INSERT INTO `apoloranks_users` (user, rank_id) VALUES (?,?)", userName, rank);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("§e[ApoloRanks] §cOuve um erro ao criar a conta do jogador." + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void save(User user) {
        try {
            iDatabase.executeUpdate("UPDATE `apoloranks_users` SET rank_id = ? WHERE user = ?", user.getRank().getId(), user.getUser());
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("§e[ApoloRanks] §cOuve um erro ao salvar as contas dos jogadores." + exception.getMessage());
            exception.printStackTrace();
        }
    }
}