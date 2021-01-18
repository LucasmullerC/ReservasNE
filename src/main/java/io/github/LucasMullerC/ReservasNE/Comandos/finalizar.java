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

public class finalizar implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			UUID id = player.getUniqueId();
			Gerenciador G = Reservar.getPlayer(id.toString());
			Grupos GP = reserva.getGruposPos(id.toString());
			boolean Stats = false;
			Player p = null;
			String UUID = null;
			if (GP != null) {
				int cont = 1;
				do {
					if (GP.getPlayer1() != null) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"region removeowner " + G.getRegiao() + " " + GP.getPlayer1() + " -w TerraPreGenerated");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +GP.getPlayer1()+ " permission unset worldedit.* worldguard:region="+G.getRegiao());
						Stats = Reservar.Finalizar(GP.getPlayer1());
						UUID = GP.getPlayer1();
						p = Bukkit.getPlayer(UUID);
						GP.setPlayer1(null);
						if (Stats == true) {		
							Rank(Stats,UUID,p);
						}
							else {
								p.sendMessage(ChatColor.RED + "Você não tem nenhuma região reservada!");
							}
						cont++;
					}
					else if (GP.getPlayer2() != null) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"region removeowner " + G.getRegiao() + " " + GP.getPlayer2() + " -w TerraPreGenerated");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +GP.getPlayer2()+ " permission unset worldedit.* worldguard:region="+G.getRegiao());
						Stats = Reservar.Finalizar(GP.getPlayer2());
						UUID = GP.getPlayer2();
						p = Bukkit.getPlayer(UUID);
						GP.setPlayer2(null);
						if (Stats == true) {		
							Rank(Stats,UUID,p);
						}
							else {
								p.sendMessage(ChatColor.RED + "Você não tem nenhuma região reservada!");
							}
						cont++;
					}
					else if (GP.getPlayer3() != null) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"region removeowner " + G.getRegiao() + " " + GP.getPlayer3() + " -w TerraPreGenerated");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +GP.getPlayer3()+ " permission unset worldedit.* worldguard:region="+G.getRegiao());
						Stats = Reservar.Finalizar(GP.getPlayer3());
						UUID = GP.getPlayer3();
						p = Bukkit.getPlayer(UUID);
						GP.setPlayer3(null);
						if (Stats == true) {		
							Rank(Stats,UUID,p);
						}
							else {
								p.sendMessage(ChatColor.RED + "Você não tem nenhuma região reservada!");
							}
						cont++;
					}
				}while (cont == 4);
			}	
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"region removeowner " + G.getRegiao() + " " + id.toString() + " -w TerraPreGenerated");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +id.toString()+ " permission unset worldedit.* worldguard:region="+G.getRegiao());
			Stats = Reservar.Finalizar(id.toString());
			UUID = id.toString();
			p = Bukkit.getPlayer(UUID);
			if (Stats == true) {		
			Rank(Stats,UUID,player);
			return true;
		}
			else {
				player.sendMessage(ChatColor.RED + "Você não tem nenhuma região reservada!");
				return true;
			}

		} else {
			sender.sendMessage("Deve ser um jogador!");
			return false;
		}
	}
	private static void Rank (Boolean Stats,String UUID,Player p) {
		int rank = Reservar.Promocao(UUID);
		if (rank == 2) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +UUID+ " group add Builder_TierII");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +UUID+ " group remove Builder_TierI");
			p.sendMessage(ChatColor.GREEN + "Parabéns você subiu para o rank Builder Tier II!");
			p.sendMessage(ChatColor.WHITE + "Você vai precisar de mais 5 Builds simples e 15 Builds medianas para o proximo rank!");
		}
		else if (rank == 3) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +UUID+ " group add Builder_TierIII");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +UUID+ " group remove Builder_TierII");
			p.sendMessage(ChatColor.GREEN + "Parabéns você subiu para o rank Builder Tier III!");
			p.sendMessage(ChatColor.WHITE + "Você vai precisar de mais 5 Builds simples e 10 Builds medianas e 10 Build Complexas para o proximo rank!");
		}
		else if (rank == 4) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +UUID+ " group add builder");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +UUID+ " group remove Builder_TierIII");
			p.sendMessage(ChatColor.GREEN + "Parabéns você subiu para o rank Builder PRO!");
			p.sendMessage(ChatColor.WHITE + "Você agora você pode fazer claims aonde quiser, Use o Discord para mais informações.");
		}
		else if (rank == 1) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +UUID+ " group remove Applier");
			p.sendMessage(ChatColor.GREEN + "Parabéns você concluiu sua build de teste!");
			p.sendMessage(ChatColor.WHITE + "Se você já enviou seu cadastro com a screenshot no site do Build The Earth, Espere até um administrador analisar seu cadastro.");
			p.sendMessage(ChatColor.WHITE + "Se você estiver em nosso Discord, será avisado quando seu cadastro for aceito.");
		}
		else {
			p.sendMessage(ChatColor.GREEN + "Região finalizada! Agora você pode fazer outra reserva.");
		}
	}

}
