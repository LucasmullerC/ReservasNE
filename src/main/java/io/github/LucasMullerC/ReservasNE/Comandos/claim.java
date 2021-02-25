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
	Reservar R;
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("claim")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GOLD + "Inválido");
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
					double[] coords = Reservar.toGeo(L.getX(),L.getZ());
					player.chat("/tpll "+coords[1]+ " " +coords[0]);
					String result = "Coordenadas: " + coords[1] + ", " + coords[0];
					String link = + coords[1]+","+coords[0];
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +id.toString()+ " permission set worldedit.* worldguard:region="+Region.getNome());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"region setpriority " +Region.getNome()+ " 1 -w TerraPreGenerated");
					player.sendMessage(ChatColor.GREEN + "Região Reservada | Código = "+Region.getNome());
					player.sendMessage(ChatColor.GREEN + result);
					player.sendMessage(ChatColor.BLUE + "http://www.google.com/maps/place/"	+link);
					Reservar.ASRtoAC(Region.getNome(), player.getName());
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "Não foi possivel fazer a reserva!");
					player.sendMessage(ChatColor.RED
							+ "Confira se você já não tem uma área reservada ou escolheu uma dificuldade acima do seu Rank.");
					return true;
				}
			}
			else {
				sender.sendMessage(ChatColor.GOLD + "Inválido");
				return false;
			}
		}
		return false;
	}

}
