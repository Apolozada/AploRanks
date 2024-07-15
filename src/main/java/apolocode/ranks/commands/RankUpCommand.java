package apolocode.ranks.commands;

import apolocode.ranks.ApoloRanks;
import apolocode.ranks.configuration.MessagesConfiguration;
import apolocode.ranks.controller.UserController;
import apolocode.ranks.utils.ApoloUtils;
import lombok.val;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RankUpCommand implements CommandExecutor {

    private final MessagesConfiguration messagesConfiguration;

    private final Economy economy;

    public RankUpCommand(ApoloRanks main) {
        messagesConfiguration = main.getMessagesConfiguration();

        economy = main.getEconomy();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§c[ ! ]  O console não executa esse comando.");
            return true;
        }

        val player = (Player)sender;
        val user = UserController.findUserByName(player.getName());
        if (user == null) return true;

        val nextRank = user.getNextRank(user);
        if (nextRank == null) return true;

        if (user.isMaxRank(user)) {
            if (messagesConfiguration.isActionBar())
                ApoloUtils.sendActionBar(messagesConfiguration.getLastRank(), player);
            else
                ApoloUtils.sendMessage(player, messagesConfiguration.getLastRank());
            return true;
        }

        if (!economy.has(player, nextRank.getCust())) {
            if (messagesConfiguration.isActionBar())
                ApoloUtils.sendActionBar(messagesConfiguration.getNoHaveMoney().replace("{rank_name}", nextRank.getName()), player);
            else
                ApoloUtils.sendMessage(player, messagesConfiguration.getNoHaveMoney().replace("{rank_name}", nextRank.getName()));
            return true;
        }

        user.setRank(nextRank);
        economy.bankWithdraw(player.getName(), nextRank.getCust());
        ApoloUtils.runCommandList(player, nextRank.getCommands());

        if (messagesConfiguration.isActionBar())
            ApoloUtils.sendActionBar(messagesConfiguration.getUpNextRank().replace("{rank_name}", user.getRank().getName()), player);
        else
            ApoloUtils.sendMessage(player, messagesConfiguration.getUpNextRank().replace("{rank_name}", user.getRank().getName()));

        return false;
    }
}