
package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.UUID;
import io.github.LucasMullerC.ReservasNE.Reservar;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class exibir implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            if (!cmd.getName().equalsIgnoreCase("exibir")) {
                sender.sendMessage("N\u00e3o identificado.");
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "/exibir rank");
                sender.sendMessage(ChatColor.GOLD + "/exibir N\u00famero_ da_Regi\u00e3o");
                return true;
            }
            if (isNumeric(args[0])) {
                if (Integer.parseInt(args[0]) <= Reservar.AreaSize()) {
                    sender.sendMessage(ChatColor.WHITE + Reservar.ExibirArea(args[0]));
                    return true;
                }
            }
            else {
                if (args[0].equalsIgnoreCase("rank")) {
                    final Player player = (Player)sender;
                    final UUID id = player.getUniqueId();
                    sender.sendMessage(ChatColor.WHITE + Reservar.ExibirRank(id.toString()));
                    return true;
                }
                sender.sendMessage("N\u00e3o identificado.");
                return false;
            }
        }
        return false;
    }
    
    public static boolean isNumeric(final String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
