package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.UUID;

import javax.swing.text.Position;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.LucasMullerC.ReservasNE.DoneAreas;
import io.github.LucasMullerC.ReservasNE.Gerenciador;
import io.github.LucasMullerC.ReservasNE.Grupos;
import io.github.LucasMullerC.ReservasNE.Regions;
import io.github.LucasMullerC.ReservasNE.Reservar;

public class finalizar implements CommandExecutor {
	Reservar Re;

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("finalizar")) {
			if (args.length == 0) {
				Player player = (Player) sender;
				UUID id = player.getUniqueId();
				String nome = player.getName();
				boolean Stats = false;
				player.sendMessage(
						ChatColor.GOLD + "Você será notificado pelo usuario: " +nome);
				Stats = Reservar.addPendente(id.toString(), nome);
				if (Stats == true) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"discord broadcast #chat-administradores @Lider de Estado Existem areas para serem analisadas, Se estiver disponivel entre no servidor e digite /finalizar analise :4459_ComfyBlob:");
					player.sendMessage(ChatColor.GREEN + "Reserva Finalizada com sucesso!");
					player.sendMessage(ChatColor.GOLD
							+ "Um administrador irá analisar sua reserva, você será notificado pelo nosso servidor do Discord.");
					return true;
				} else {
					player.sendMessage(ChatColor.GREEN + "Você não tem regiões reservadas!");
					return false;
				}
			}
			
			else if (args[0].equalsIgnoreCase("analise")) {
				DoneAreas D;
				Regions R;
				Player player = (Player) sender;
				D = Reservar.getPendente();
				R = Reservar.getRegiao(D.getArea());
			
				String msg = "";
				for (int i = 1;i<args.length;i++) {
					msg += args[i] + " ";
				}
				msg = msg.trim();
				String[] arrayValores = msg.split(" ");
				if (arrayValores[0].equalsIgnoreCase("confirmar")) {
					FinalizarReserva(D.getNome());
					Reservar.RemoverArea(R.getNome());
					Reservar.ACtoAF(R.getNome());
					Reservar.RemoverPendente();
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"discord broadcast @AvisosReserva **"+D.getNome()+"** Sua reserva foi aceita! agora voce pode reservar outra regiao.");
					player.sendMessage(ChatColor.GOLD + "Reserva confirmada.");
					return true;

				} else if (arrayValores[0].equalsIgnoreCase("recusar")) {
					String motivo = "";
					for (int i = 1;i<arrayValores.length;i++) {
						motivo += arrayValores[i] + " ";
					}
					motivo = motivo.trim();
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"discord broadcast @AvisosReserva **"+D.getNome()+"** Sua reserva foi recusada pelo motivo: " +motivo+" | Va ate sua reserva e corrija os problemas!");
					player.sendMessage(ChatColor.GOLD + "Reserva recusada.");
					return true;
				}
				else {
					Location L = R.getPos();
					double[] coords = Reservar.toGeo( L.getX(),L.getZ());
					player.chat("/tpll "+coords[1]+ " " +coords[0]);
					player.sendMessage(ChatColor.GOLD + "Digite /finalizar analise confirmar - Para aceitar a reserva.");
					player.sendMessage(ChatColor.GOLD + "Ou digite /finalizar analise Recusar (Motivo) - Para recusar a reserva.");
					return true;
				}
			}
		}
		return false;
	}

	private static void FinalizarReserva(String Nome) {
		Player player = Bukkit.getPlayer(Nome);
		UUID id = player.getUniqueId();
		String nome = player.getName();
		Gerenciador G = Reservar.getPlayer(id.toString());
		Grupos GP = reserva.getGruposPos(id.toString());
		boolean Stats = false;
		Player p = null;
		String UUID = null;
		if (GP != null) {
			int cont = 1;
			do {
				if (GP.getPlayer1() != null) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"region removeowner " + G.getRegiao() + " " + GP.getPlayer1() + " -w TerraPreGenerated");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + GP.getPlayer1()
							+ " permission unset worldedit.* worldguard:region=" + G.getRegiao());
					Stats = Reservar.Finalizar(GP.getPlayer1());
					UUID = GP.getPlayer1();
					p = Bukkit.getPlayer(UUID);
					GP.setPlayer1(null);
					if (Stats == true) {
						Rank(Stats, UUID, p);
					} else {
						p.sendMessage(ChatColor.RED + "Você não tem nenhuma região reservada!");
					}
					cont++;
				} else if (GP.getPlayer2() != null) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"region removeowner " + G.getRegiao() + " " + GP.getPlayer2() + " -w TerraPreGenerated");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + GP.getPlayer2()
							+ " permission unset worldedit.* worldguard:region=" + G.getRegiao());
					Stats = Reservar.Finalizar(GP.getPlayer2());
					UUID = GP.getPlayer2();
					p = Bukkit.getPlayer(UUID);
					GP.setPlayer2(null);
					if (Stats == true) {
						Rank(Stats, UUID, p);
					} else {
						p.sendMessage(ChatColor.RED + "Você não tem nenhuma região reservada!");
					}
					cont++;
				} else if (GP.getPlayer3() != null) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"region removeowner " + G.getRegiao() + " " + GP.getPlayer3() + " -w TerraPreGenerated");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + GP.getPlayer3()
							+ " permission unset worldedit.* worldguard:region=" + G.getRegiao());
					Stats = Reservar.Finalizar(GP.getPlayer3());
					UUID = GP.getPlayer3();
					p = Bukkit.getPlayer(UUID);
					GP.setPlayer3(null);
					if (Stats == true) {
						Rank(Stats, UUID, p);
					} else {
						p.sendMessage(ChatColor.RED + "Você não tem nenhuma região reservada!");
					}
					cont++;
				}
			} while (cont == 4);
		}
		else {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"region removeowner " + G.getRegiao() + " " + id.toString() + " -w TerraPreGenerated");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"lp user " + id.toString() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"region remove " + G.getRegiao() + " -w TerraPreGenerated");
			Stats = Reservar.Finalizar(id.toString());
		}
		UUID = id.toString();
		p = Bukkit.getPlayer(UUID);
		if (Stats == true) {
			Rank(Stats, UUID, player);
		} else {
			player.sendMessage(ChatColor.RED + "Você não tem nenhuma região reservada!");
		}
	}

	private static void Rank(Boolean Stats, String UUID, Player p) {
		int rank = Reservar.Promocao(UUID);
		if (rank == 2) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + UUID + " group add Builder_TierII");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + UUID + " group remove Builder_TierI");
			p.sendMessage(ChatColor.GREEN + "Parabéns você subiu para o rank Builder Tier II!");
			p.sendMessage(ChatColor.WHITE
					+ "Você vai precisar de mais 5 Builds simples e 15 Builds medianas para o proximo rank!");
		} else if (rank == 3) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + UUID + " group add Builder_TierIII");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + UUID + " group remove Builder_TierII");
			p.sendMessage(ChatColor.GREEN + "Parabéns você subiu para o rank Builder Tier III!");
			p.sendMessage(ChatColor.WHITE
					+ "Você vai precisar de mais 5 Builds simples e 10 Builds medianas e 10 Build Complexas para o proximo rank!");
		} else if (rank == 4) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + UUID + " group add builder");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + UUID + " group remove Builder_TierIII");
			p.sendMessage(ChatColor.GREEN + "Parabéns você subiu para o rank Builder PRO!");
			p.sendMessage(ChatColor.WHITE
					+ "Você agora você pode fazer claims aonde quiser, Use o Discord para mais informações.");
		} else if (rank == 1) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + UUID + " group remove Applier");
			p.sendMessage(ChatColor.GREEN + "Parabéns você concluiu sua build de teste!");
			p.sendMessage(ChatColor.WHITE
					+ "Se você já enviou seu cadastro com a screenshot no site do Build The Earth, Espere até um administrador analisar seu cadastro.");
			p.sendMessage(
					ChatColor.WHITE + "Se você estiver em nosso Discord, será avisado quando seu cadastro for aceito.");
		} else {
			p.sendMessage(ChatColor.GREEN + "Região finalizada! Agora você pode fazer outra reserva.");
		}
	}

}
