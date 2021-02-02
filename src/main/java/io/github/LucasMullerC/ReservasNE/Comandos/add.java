package io.github.LucasMullerC.ReservasNE.Comandos;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.LucasMullerC.ReservasNE.Areas;
import io.github.LucasMullerC.ReservasNE.Regions;
import io.github.LucasMullerC.ReservasNE.Reservar;

public class add implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Areas A;
		Reservar R = null;
		ArrayList<Areas> Pendente = new ArrayList<Areas>();
	    if (sender instanceof Player) {
	    	if (cmd.getName().equalsIgnoreCase("add")) {
	    		if (args.length == 0) {
	    			R.AP.load();
	    			Player player = (Player) sender;
	    			Pendente = Reservar.getAreaPos();
	    			if (Pendente == null) {
	    				sender.sendMessage(ChatColor.GREEN + "A lista está vazia!");
	    				return true;
	    			}
	    			else {
	    				String result,result2;
		    			for (int i = 0;i<Pendente.size();i++) {   				
		    				Double X1 = Double.parseDouble(Pendente.get(i).getP1x());
		    				Double X2 = Double.parseDouble(Pendente.get(i).getP1y());  				
			    			double[] P1 = Reservar.fromGeo(X2,X1);
			    			String C1 = String.valueOf(P1[0]);
			    			String C2 = String.valueOf(P1[1]);
			    			String [] Coords = C1.split("\\.");
			    			String [] Coords1 = C2.split("\\.");
			    			result = Coords[0]+",0,"+Coords1[0];
		    				player.chat("//pos1 "+result);
		    				Double X3 = Double.parseDouble(Pendente.get(i).getP3x().replace(",","."));
		    				Double X4 = Double.parseDouble(Pendente.get(i).getP3y().replace(",","."));
			    			double[] P2 = Reservar.fromGeo(X4,X3);
			    			C1 = String.valueOf(P2[0]);
			    			C2 = String.valueOf(P2[1]);
		    				String [] Coords2 = C1.split("\\.");
			    			String [] Coords3 = C2.split("\\.");
			    			result2 = Coords2[0]+",0,"+Coords3[0];
		    				player.chat("//pos2 "+result2);
		    		        String area;
		    				Location pos = player.getLocation();
		    				int x = Integer.parseInt(Coords2[0]);
		    				int z = Integer.parseInt(Coords3[0]);
		    				int y = 100;
		    				World w = pos.getWorld();
		    				Location loc = new Location(w, x, y, z); //defines new location
		    				area = Reservar.addarea(Pendente.get(i).getNome(),Pendente.get(i).getDificuldade(),loc);
		    				player.chat("//expand 10000 u");
		    				player.chat("//expand 10000 d");
		    				player.chat("/region define "+area);
		    				A = Pendente.get(i);
		    				A.setNome(area);
		    				Reservar.addSemReserva(Pendente.get(i));
		    				sender.sendMessage(ChatColor.GREEN + area + " Adicionada!");
		    			}
		    			Pendente.clear();
		    			Reservar.limpapend();
		    			sender.sendMessage(ChatColor.GOLD +" Lista Limpa!");
						return true;
	    			}
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
		    			area = Reservar.addarea(args[0],args[1],pos);
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
