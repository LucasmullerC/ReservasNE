package io.github.LucasMullerC.ReservasNE.Comandos;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.LucasMullerC.ReservasNE.Reservar;

public class salvar implements CommandExecutor{
	Reservar R;
	reserva GP;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("salvar")) {
			Player player = (Player) sender;
			R.pendente.save();
			R.area.save();
			R.player.save();
			R.ASR.save();
			R.AC.save();
			R.AF.save();
			GP.grupo.save();
			player.sendMessage(ChatColor.GREEN + "ReservasNE Salvo!");
		}
		return false;
	}
	

}
