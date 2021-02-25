package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.LucasMullerC.ReservasNE.Reservar;

public class player implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("player")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GOLD + "/player (Nick do jogador) (QtdEasy) (QtdMedium) (QtdHard) (QtdPRO) (Rank)");
				return true;
			}
			else if (args[0].equalsIgnoreCase(Bukkit.getPlayer(args[0]).getName())+args[1]+args[2]+args[3]+args[4]+args[5] != null) {
				Player p= (Player) Bukkit.getOfflinePlayer(args[0]);
				UUID id = p.getUniqueId();
				Reservar.addBuilder(id.toString(),Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]),Integer.parseInt(args[5]));
				sender.sendMessage(ChatColor.GREEN + "Builder adicionado!");
				return true;
			}
			else {
				sender.sendMessage(ChatColor.GREEN + "Comando não identificado.");
			}
			
		}
			
		return false;
	}
	

}
