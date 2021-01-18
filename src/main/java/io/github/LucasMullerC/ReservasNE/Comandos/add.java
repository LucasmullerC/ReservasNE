package io.github.LucasMullerC.ReservasNE.Comandos;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.LucasMullerC.ReservasNE.Reservar;

public class add implements CommandExecutor{
	Reservar R = new Reservar ();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (sender instanceof Player) {
	    	if (cmd.getName().equalsIgnoreCase("add")) {
	    		if (args.length == 0) {
	    			sender.sendMessage(ChatColor.RED +"Nenhuma região reconhecida");
	    			sender.sendMessage(ChatColor.RED +"/add cidade dificuldade");
					return true;
				}
	    		else if (args[0] + args[1] != null) {
	    			Player player = (Player) sender;
			        String area;
					Location pos;
		    		int N = Integer.parseInt(args[1]);
		    		if (N < 0 && N > 4) {
		    			sender.sendMessage(ChatColor.RED +"Dificuldade deve ser entre 0 e 4!");
		    			return true;
		    		}
		    		else {
		    			pos = player.getLocation();
		    			area = R.addarea(args[0],args[1],pos);
		        		player.chat("//expand vert");
		        		player.chat("/region define "+area);
		        		sender.sendMessage(ChatColor.GREEN + area + " Adicionada!");
		        		return true;
		    		}
	    		}
	    } else {
	        sender.sendMessage("Deve ser um jogador!");
	        return false;
	    }
		return false;
	}
		return false;

}
}
