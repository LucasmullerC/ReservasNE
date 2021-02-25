
package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.UUID;
import io.github.LucasMullerC.ReservasNE.Reservar;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class player implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("player")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "/player (Nick do jogador) (QtdEasy) (QtdMedium) (QtdHard) (QtdPRO) (Rank)");
                return true;
            }
            if (args[0].equalsIgnoreCase(Bukkit.getPlayer(args[0]).getName()) + args[1] + args[2] + args[3] + args[4] + args[5] != null) {
                final Player p = (Player)Bukkit.getOfflinePlayer(args[0]);
                final UUID id = p.getUniqueId();
                Reservar.addBuilder(id.toString(), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                sender.sendMessage(ChatColor.GREEN + "Builder adicionado!");
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Comando n\u00e3o identificado.");
        }
        return false;
    }
}
