package apolocode.ranks.commands;

import apolocode.ranks.inventories.RanksInventory;
import lombok.val;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RanksCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§c[ ! ] O console não executa esse comando.");
            return true;
        }

        val player = (Player)sender;
        new RanksInventory().open(player);

        return false;
    }
}
