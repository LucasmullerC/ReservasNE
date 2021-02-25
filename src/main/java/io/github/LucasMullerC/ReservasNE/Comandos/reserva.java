package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.LucasMullerC.ReservasNE.Gerenciador;
import io.github.LucasMullerC.ReservasNE.Grupos;
import io.github.LucasMullerC.ReservasNE.Regions;
import io.github.LucasMullerC.ReservasNE.Reservar;
import io.github.LucasMullerC.ReservasNE.util.GrupoStore;

public class reserva implements CommandExecutor {
	public static GrupoStore grupo;

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("reserva")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GOLD + "/reserva (Nick do jogador)");
				sender.sendMessage(ChatColor.GOLD + "/reserva aceitar | recusar");
				return true;
			}
			else if (args[0].equalsIgnoreCase("aceitar")) {
				Player player = (Player) sender;
				UUID id = player.getUniqueId();
				Grupos Group = null;
				if (grupo.getValues().isEmpty() == false) {
					Group = getGruposPos(id.toString());
					Gerenciador P2 = null;
					Gerenciador P1 = Reservar.getPlayer(id.toString());
					if (Group.getPendente() != null) {
						if (Group.getPlayer1() == null) {
							P2 = Reservar.getPlayer(Group.getPendente());
							P2.setGrupo(true);
							Group.setPendente(null);
						} else if (Group.getPlayer2() == null) {
							P2 = Reservar.getPlayer(Group.getPendente());
							P2.setGrupo(true);
							Group.setPendente(null);
						} else if (Group.getPlayer3() == null) {
							P2 = Reservar.getPlayer(Group.getPendente());
							P2.setGrupo(true);
							Group.setPendente(null);
						} else {
							sender.sendMessage(ChatColor.WHITE + "Não foi possivel adicionar o membro ao grupo.");
							return true;
						}
					} else {
						sender.sendMessage(ChatColor.WHITE + "Você não tem nenhum pedido pendente.");
						return true;
					}
					Player p = Bukkit.getPlayer(P2.getUUID());
					P2.setRegiao(P1.getRegiao());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"region addowner " + P2.getRegiao() + " " + P2.getUUID() + " -w TerraPreGenerated");
					Regions R = Reservar.getRegiao(P2.getRegiao());
					Location L = R.getPos();
					player.teleport(L);
					String Coord = Reservar.toGeo(L.getX(), L.getZ()).toString();
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " +P2.getUUID()+ " permission set worldedit.* worldguard:region="+P2.getRegiao());
					p.sendMessage(ChatColor.GREEN + "Região Reservada!");
					p.sendMessage(ChatColor.WHITE + "Coordenadas: " + Coord);
					sender.sendMessage(
							ChatColor.GREEN + "O jogador " + p.getDisplayName() + " Foi adicionado a sua reserva!");
				} else {
					sender.sendMessage(ChatColor.WHITE + "Você não tem nenhum pedido pendente.");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("recusar")) {
				Player player = (Player) sender;
				UUID id = player.getUniqueId();
				Grupos Group = null;
				if (grupo.getValues().isEmpty() == false) {
					Group = getGruposPos(id.toString());
					if (Group.getPendente() != null) {
						Player p = Bukkit.getPlayer(Group.getPendente());
						Group.setPendente(null);
						p.sendMessage(ChatColor.RED + "Seu pedido foi recusado.");
						sender.sendMessage(ChatColor.GREEN + "Pedido recusado com sucesso!");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.GREEN + "Você não tem nenhum pedido pendente!");
					return true;
				}
			}
			 else if (args[0].equalsIgnoreCase("tp")) {
				Player player = (Player) sender;
				UUID id = player.getUniqueId();
				Location Coord = Reservar.getCoord(id.toString());
				if (Coord == null) {
					sender.sendMessage(ChatColor.RED + "Você não tem nenhum região reservada!");
					return true;
				} else {
					player.teleport(Coord);
					return true;
				}
			} else if (args[0].equalsIgnoreCase("coordenadas")) {
				Player player = (Player) sender;
				UUID id = player.getUniqueId();
				Location L = Reservar.getCoord(id.toString());
				if (L == null) {
					sender.sendMessage(ChatColor.RED + "Você não tem nenhum região reservada!");
					return true;
				} else {
					double[] coords = Reservar.toGeo(L.getX(),L.getZ());
					String result = "Coordenadas: " + coords[1] + ", " + coords[0];
					String link = + coords[1]+","+coords[0];
					sender.sendMessage(ChatColor.WHITE + result);
					sender.sendMessage(ChatColor.BLUE + "http://www.google.com/maps/place/"
							+link);
					return true;
				}
			}
			else if (args[0].equalsIgnoreCase(Bukkit.getPlayer(args[0]).getName())) {
				Player p = Bukkit.getPlayer(args[0]);
				Player player = (Player) sender;
				Gerenciador G, GP;
				if (p.isOnline() == true) {
					UUID id = p.getUniqueId();
					UUID idP = player.getUniqueId();
					G = Reservar.getPlayer(id.toString());
					GP = Reservar.getPlayer(idP.toString());
					Grupos Group;
					if (G.getRegiao() == "nulo") {
						sender.sendMessage(ChatColor.RED + "O Jogador não tem nenhuma região reservada.");
						return true;
					} else if (GP.getRegiao().equalsIgnoreCase("nulo") == false) {
						sender.sendMessage(ChatColor.RED + "Você já tem uma região reservada!");
						sender.sendMessage(ChatColor.WHITE + "Finalize ou cancele essa região para reservar uma nova!");
						return true;
					} else if (G.getRegiao() != "nulo" && GP.getRegiao().equalsIgnoreCase("nulo")) {
						if (grupo.getValues().size() <= 0) {
							Group = new Grupos(id.toString());
							grupo.add(Group);
						} else if (getGruposPos(id.toString()) == null) {
							Group = new Grupos(id.toString());
							grupo.add(Group);
						}
						Group = getGruposPos(id.toString());
						if (Group.getPlayer1() == null || Group.getPlayer2() == null || Group.getPlayer3() == null) {
							Group.setPendente(idP.toString());
						} else {
							sender.sendMessage(ChatColor.RED + "O grupo já está cheio :(");
							return true;
						}
						p.sendMessage(ChatColor.GREEN + "O jogador " + ChatColor.BLUE + player.getDisplayName()
								+ ChatColor.GREEN + " Quer te ajudar a construir!");
						p.sendMessage(ChatColor.WHITE + "Digite: " + ChatColor.GREEN + "/reserva aceitar"
								+ ChatColor.WHITE + " ou " + ChatColor.RED + "/reserva recusar");
						sender.sendMessage(ChatColor.GREEN + "Pedido enviado!");
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Não foi possivel enviar o pedido.");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "O Jogador deve estar Online!");
					return true;
				}
			}
			else {
				sender.sendMessage(ChatColor.GOLD + "/reserva (Nick do jogador)");
				sender.sendMessage(ChatColor.GOLD + "/reserva aceitar | recusar");
				return true;
			}
		}
		sender.sendMessage(ChatColor.GOLD + "Não identificado");
		return false;
	}

	public static Grupos getGruposPos(String search) {
		for (Grupos d : grupo.getValues()) {
			if (d.getUUID() != null && d.getUUID().contains(search)) {
				return d;
			}
		}
		return null;
	}

}
