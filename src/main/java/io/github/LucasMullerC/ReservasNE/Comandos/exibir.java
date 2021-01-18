package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.LucasMullerC.ReservasNE.Reservar;

public class exibir implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("exibir")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + "/exibir rank");
					sender.sendMessage(ChatColor.GOLD + "/exibir Número_ da_Região");
					return true;
				}
				else if(isNumeric(args[0]) == true) {
					if (Integer.parseInt(args[0]) <= Reservar.AreaSize()) {
						sender.sendMessage(ChatColor.WHITE + Reservar.ExibirArea(args[0]));
						return true;
					}
				}
				else if (args[0].equalsIgnoreCase("rank")) {
					Player player = (Player) sender;
					UUID id = player.getUniqueId();
		    		sender.sendMessage(ChatColor.WHITE + Reservar.ExibirRank(id.toString()));
		    		return true;
				}
				else {
					sender.sendMessage("Não identificado.");
					return false;
				}
			} else {
				sender.sendMessage("Não identificado.");
				return false;
			}
		}
		return false;

	}
	public static boolean isNumeric(String str) {
		  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
		}
}
