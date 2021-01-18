package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.LucasMullerC.ReservasNE.Gerenciador;
import io.github.LucasMullerC.ReservasNE.Grupos;
import io.github.LucasMullerC.ReservasNE.Reservar;

public class cancelar implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			UUID id = player.getUniqueId();
			Gerenciador G = Reservar.getPlayer(id.toString());
			Grupos GP = reserva.getGruposPos(id.toString());
			if (GP != null) {
				int cont = 1;
				do {
					if (GP.getPlayer1() != null) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"region removeowner " + G.getRegiao() + " " + GP.getPlayer1() + " -w TerraPreGenerated");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +GP.getPlayer1()+ " permission unset worldedit.* worldguard:region="+G.getRegiao());
						boolean Stats = Reservar.Cancelar(GP.getPlayer1());
						GP.setPlayer1(null);
						cont++;
					}
					else if (GP.getPlayer2() != null) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"region removeowner " + G.getRegiao() + " " + GP.getPlayer2() + " -w TerraPreGenerated");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +GP.getPlayer1()+ " permission unset worldedit.* worldguard:region="+G.getRegiao());
						boolean Stats = Reservar.Cancelar(GP.getPlayer2());
						GP.setPlayer2(null);
						cont++;
					}
					else if (GP.getPlayer3() != null) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"region removeowner " + G.getRegiao() + " " + GP.getPlayer3() + " -w TerraPreGenerated");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +GP.getPlayer1()+ " permission unset worldedit.* worldguard:region="+G.getRegiao());
						boolean Stats = Reservar.Cancelar(GP.getPlayer3());
						GP.setPlayer3(null);
						cont++;
					}
				}while (cont == 4);
			}
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"region removeowner " + G.getRegiao() + " " + id.toString() + " -w TerraPreGenerated");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +id.toString()+ " permission unset worldedit.* worldguard:region="+G.getRegiao());
			boolean Stats = Reservar.Cancelar(id.toString());
			if (Stats == true) {
				sender.sendMessage(ChatColor.GREEN + "Região cancelada, Você pode reservar outra região.");
				return true;
			}
			else {
				sender.sendMessage(ChatColor.RED + "Você não tem nenhuma região reservada!");
				return true;
			}
		}
		return false;
	}

}
