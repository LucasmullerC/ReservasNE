package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.LucasMullerC.ReservasNE.Regions;
import io.github.LucasMullerC.ReservasNE.Reservar;

public class claim implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("claim")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GOLD + "Inv�lido");
				return true;
			}
			else if (args[0]+args[1] != null) {
				Player player = (Player) sender;
				UUID id = player.getUniqueId();
				Regions Region = Reservar.checkplayer(id.toString(), args[0], args[1]);
				if (Region != null) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"region addowner " + Region.getNome() + " " + id.toString() + " -w TerraPreGenerated");
					Location L = Region.getPos();
					player.teleport(L);
					double[] coords = Reservar.toGeo(L.getX(),L.getZ());
					String result = "Coordenadas: " + coords[1] + ", " + coords[0];
					String link = + coords[1]+","+coords[0];
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +id.toString()+ " permission set worldedit.* worldguard:region="+Region.getNome());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"region setpriority " +Region.getNome()+ " 1 -w TerraPreGenerated");
					sender.sendMessage(ChatColor.GREEN + "Regi�o Reservada!");
					sender.sendMessage(ChatColor.GREEN + result);
					sender.sendMessage(ChatColor.BLUE + "http://www.google.com/maps/place/"	+link);
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "N�o foi possivel fazer a reserva!");
					sender.sendMessage(ChatColor.RED
							+ "Confira se voc� j� n�o tem uma �rea reservada ou escolheu uma dificuldade acima do seu Rank.");
					return true;
				}
			}
			else {
				sender.sendMessage(ChatColor.GOLD + "Inv�lido");
				return false;
			}
		}
		return false;
	}

}
