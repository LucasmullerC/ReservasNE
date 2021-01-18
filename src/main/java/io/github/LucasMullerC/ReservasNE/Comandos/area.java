package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.LucasMullerC.ReservasNE.Reservar;

public class area implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("area")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GOLD + "/area [add|remover] (Nome da área)");
				return true;
			}
			else if (args[0].equalsIgnoreCase("add")) {
				if (args[0].equalsIgnoreCase("add")+args[1] != null) {
					Location pos;
					Player player = (Player) sender;
					UUID id = player.getUniqueId();
					pos = player.getLocation();
					Reservar.AddArea4(args[1],pos);
					sender.sendMessage(ChatColor.GOLD + "Área adicionada com sucesso!");	
					return true;
				}
			}
			else if (args[0].equalsIgnoreCase("remover")+args[1] != null) {
				Reservar.RemoverArea(args[1]);
				sender.sendMessage(ChatColor.GOLD + "Área removida com sucesso!");	
				return true;
			}
			else {
				sender.sendMessage(ChatColor.GOLD + "/area [add|remover] (Nome da área)");
				return true;
			}
			
		}
		else {
			sender.sendMessage(ChatColor.GOLD + "/area [add|remover] (Nome da área)");
			return false;
		}
		return false;
	}
	

}
