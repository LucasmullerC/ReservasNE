
package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.Iterator;
import io.github.LucasMullerC.Objetos.Regions;
import org.bukkit.Location;
import io.github.LucasMullerC.Objetos.Grupos;
import io.github.LucasMullerC.ReservasNE.DiscordActs;
import java.util.UUID;
import org.bukkit.Bukkit;
import io.github.LucasMullerC.ReservasNE.Reservar;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import io.github.LucasMullerC.Objetos.Gerenciador;
import io.github.LucasMullerC.ReservasNE.util.GrupoStore;
import org.bukkit.command.CommandExecutor;

public class area implements CommandExecutor
{
    public static GrupoStore grupo;
    Gerenciador G;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!command.getName().equalsIgnoreCase("area")) {
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "/area [add|remover|tp|coordenadas|tier4 Nome da \u00e1rea|Nick do jogador|aceitar|recusar]");
            return true;
        }
        if (args[0].equalsIgnoreCase("add")) {
            final Player player = (Player)sender;
            if (args[0].equalsIgnoreCase("add") + args[1] != null) {
                if (player.hasPermission("reservasbte.adm")) {
                    final UUID id = player.getUniqueId();
                    final Location pos = player.getLocation();
                    Reservar.AddArea4(args[1], pos);
                    player.sendMessage(ChatColor.GOLD + "\u00c1rea adicionada com sucesso!");
                    return true;
                }
                player.sendMessage(ChatColor.GOLD + "Hoje n\u00e3o patr\u00e3o..");
                return true;
            }
        }
        else if (args[0].equalsIgnoreCase("remover")) {
            final Player player = (Player)sender;
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "/remover codigo");
            }
            else {
                if (!args[1].equalsIgnoreCase(Reservar.getRegiao(args[1]).getNome())) {
                    player.sendMessage(ChatColor.RED + "Regi\u00e3o n\u00e3o encontrada...");
                    return true;
                }
                if (player.hasPermission("reservasbte.adm")) {
                    Reservar.RemoverArea(args[1]);
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region remove " + args[1]);
                    sender.sendMessage(ChatColor.GOLD + "\u00c1rea removida com sucesso!");
                    return true;
                }
                player.sendMessage(ChatColor.GOLD + "Hoje n\u00e3o patr\u00e3o..");
                return true;
            }
        }
        else if (args[0].equalsIgnoreCase("aceitar")) {
            final Player player = (Player)sender;
            final UUID id2 = player.getUniqueId();
            Grupos Group = null;
            this.G = Reservar.getPlayer(id2.toString());
            if (area.grupo.getValues().isEmpty()) {
                sender.sendMessage(ChatColor.WHITE + "Voc\u00ea n\u00e3o tem nenhum pedido pendente.");
                return true;
            }
            Group = getGruposPos(id2.toString());
            Gerenciador P2 = null;
            final Gerenciador P3 = Reservar.getPlayer(id2.toString());
            if (!Group.getPendente().equals("nulo")) {
                P2 = Reservar.getPlayer(Group.getPendente());
                if (Group.getPlayer1().equals("nulo")) {
                    Group.setPlayer1(Group.getPendente());
                }
                else if (Group.getPlayer2().equals("nulo")) {
                    Group.setPlayer2(Group.getPendente());
                }
                else if (Group.getPlayer3().equals("nulo")) {
                    Group.setPlayer3(Group.getPendente());
                }
                P2.setGrupo(true);
                P2.setRegiao(this.G.getRegiao());
                Group.setPendente("nulo");
                final UUID idplayer = UUID.fromString(P2.getUUID());
                final Player pg = Bukkit.getPlayer(idplayer);
                P2.setRegiao(P3.getRegiao());
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region addowner " + P2.getRegiao() + " " + P2.getUUID() + " -w TerraPreGenerated");
                final Regions R = Reservar.getRegiao(P2.getRegiao());
                final Location L = R.getPos();
                final double[] coords = Reservar.toGeo(L.getX(), L.getZ());
                pg.chat("/tp area");
                final String result = "Coordenadas: " + coords[1] + ", " + coords[0];
                final String link = "http://www.google.com/maps/place/" + coords[1] + "," + coords[0];
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + P2.getUUID() + " permission set worldedit.* worldguard:region=" + P2.getRegiao());
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region setpriority " + P2.getRegiao() + " 1 -w TerraPreGenerated");
                pg.sendMessage(ChatColor.GREEN + "Regi\u00e3o Reservada | C\u00f3digo = " + R.getNome());
                pg.sendMessage(ChatColor.GREEN + result);
                pg.sendMessage(ChatColor.BLUE + link);
                DiscordActs.sendMessage(P2.getDiscord(), result);
                DiscordActs.sendMessage(P2.getDiscord(), link);
                sender.sendMessage(ChatColor.GREEN + "O jogador " + pg.getDisplayName() + " Foi adicionado a sua reserva!");
                return true;
            }
            sender.sendMessage(ChatColor.WHITE + "Voc\u00ea n\u00e3o tem nenhum pedido pendente.");
            return true;
        }
        else if (args[0].equalsIgnoreCase("recusar")) {
            final Player player = (Player)sender;
            final UUID id2 = player.getUniqueId();
            Grupos Group = null;
            if (area.grupo.getValues().isEmpty()) {
                sender.sendMessage(ChatColor.GREEN + "Voc\u00ea n\u00e3o tem nenhum pedido pendente!");
                return true;
            }
            Group = getGruposPos(id2.toString());
            if (!Group.getPendente().equals("nulo")) {
                final UUID idplayer2 = UUID.fromString(Group.getPendente());
                final Player p = Bukkit.getPlayer(idplayer2);
                Group.setPendente("nulo");
                if (Group.getPlayer1().equals("nulo") && Group.getPlayer2().equals("nulo") && Group.getPlayer3().equals("nulo")) {
                    RemoverGrupo(id2.toString());
                }
                p.sendMessage(ChatColor.RED + "Seu pedido foi recusado.");
                sender.sendMessage(ChatColor.GREEN + "Pedido recusado com sucesso!");
                return true;
            }
        }
        else if (args[0].equalsIgnoreCase("tp")) {
            final Player player = (Player)sender;
            final UUID id2 = player.getUniqueId();
            if (Reservar.getPlayer(id2.toString()).getRegiao().equals("nulo")) {
                sender.sendMessage(ChatColor.RED + "Voc\u00ea n\u00e3o tem nenhuma regi\u00e3o reservada!");
                return true;
            }
            final Location L2 = Reservar.getCoord(id2.toString());
            final double[] coords2 = Reservar.toGeo(L2.getX(), L2.getZ());
            player.chat("/tpll " + coords2[1] + " " + coords2[0]);
            return true;
        }
        else if (args[0].equalsIgnoreCase("coordenadas")) {
            final Player player = (Player)sender;
            final UUID id2 = player.getUniqueId();
            if (Reservar.getPlayer(id2.toString()).getRegiao().equals("nulo")) {
                sender.sendMessage(ChatColor.RED + "Voc\u00ea n\u00e3o tem nenhuma regi\u00e3o reservada!");
                return true;
            }
            final Location L2 = Reservar.getCoord(id2.toString());
            final double[] coords2 = Reservar.toGeo(L2.getX(), L2.getZ());
            final String result2 = "Coordenadas: " + coords2[1] + ", " + coords2[0];
            final String link2 = coords2[1] + "," + coords2[0];
            sender.sendMessage(ChatColor.WHITE + result2);
            sender.sendMessage(ChatColor.BLUE + "http://www.google.com/maps/place/" + link2);
            return true;
        }
        else if (args[0].equalsIgnoreCase(Reservar.getPlayerbyname(args[0]))) {
            final Player p2 = Bukkit.getPlayer(args[0]);
            final Player player2 = (Player)sender;
            if (!p2.isOnline()) {
                sender.sendMessage(ChatColor.RED + "O Jogador deve estar Online!");
                return true;
            }
            final UUID id3 = p2.getUniqueId();
            final UUID idP = player2.getUniqueId();
            final Gerenciador G = Reservar.getPlayer(id3.toString());
            final Gerenciador GP = Reservar.getPlayer(idP.toString());
            if (G.getDiscord().equals("nulo")) {
                player2.sendMessage(ChatColor.GOLD + "Seu Discord n\u00e3o foi verificado!");
                player2.sendMessage(ChatColor.GOLD + "Para verificar digite:");
                player2.sendMessage(ChatColor.YELLOW + "/link Seu nome no Discord");
                player2.sendMessage(ChatColor.GOLD + "Ex: /link Lucas M#4469");
                return true;
            }
            if (G.getRegiao() == "nulo") {
                sender.sendMessage(ChatColor.RED + "O Jogador n\u00e3o tem nenhuma regi\u00e3o reservada.");
                return true;
            }
            if (!GP.getRegiao().equalsIgnoreCase("nulo")) {
                sender.sendMessage(ChatColor.RED + "Voc\u00ea j\u00e1 tem uma regi\u00e3o reservada!");
                sender.sendMessage(ChatColor.WHITE + "Finalize ou cancele essa regi\u00e3o para reservar uma nova!");
                return true;
            }
            if (G.getRegiao() == "nulo" || !GP.getRegiao().equalsIgnoreCase("nulo")) {
                sender.sendMessage(ChatColor.RED + "N\u00e3o foi possivel enviar o pedido.");
                return true;
            }
            if (area.grupo.getValues().size() <= 0) {
                final Grupos Group2 = new Grupos(id3.toString());
                area.grupo.add(Group2);
                Group2.setPlayer1("nulo");
                Group2.setPlayer2("nulo");
                Group2.setPlayer3("nulo");
            }
            else if (getGruposPos(id3.toString()) == null) {
                final Grupos Group2 = new Grupos(id3.toString());
                area.grupo.add(Group2);
                Group2.setPlayer1("nulo");
                Group2.setPlayer2("nulo");
                Group2.setPlayer3("nulo");
            }
            final Grupos Group2 = getGruposPos(id3.toString());
            if (Group2.getPlayer1().equals("nulo") || Group2.getPlayer2().equals("nulo") || Group2.getPlayer3().equals("nulo")) {
                Group2.setPendente(idP.toString());
                p2.sendMessage(ChatColor.GREEN + "O jogador " + ChatColor.BLUE + player2.getDisplayName() + ChatColor.GREEN + " Quer te ajudar a construir!");
                p2.sendMessage(ChatColor.WHITE + "Digite: " + ChatColor.GREEN + "/area aceitar" + ChatColor.WHITE + " ou " + ChatColor.RED + "/area recusar");
                sender.sendMessage(ChatColor.GREEN + "Pedido enviado!");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "O grupo j\u00e1 est\u00e1 cheio :(");
            return true;
        }
        else {
            if (!args[0].equalsIgnoreCase("tier4")) {
                sender.sendMessage(ChatColor.GOLD + "/area (Nick do jogador)");
                sender.sendMessage(ChatColor.GOLD + "/area aceitar | recusar");
                return true;
            }
            final Player player = (Player)sender;
            if (!args[1].equalsIgnoreCase(Reservar.getRegiao(args[1]).getNome())) {
                player.sendMessage(ChatColor.RED + "Regi\u00e3o n\u00e3o encontrada...");
                return true;
            }
            if (!player.hasPermission("reservasbte.area")) {
                sender.sendMessage(ChatColor.RED + "Voc\u00ea deve ser um Builder Tier 4 para usar o comando!");
                return true;
            }
            final UUID id2 = player.getUniqueId();
            this.G = Reservar.getPlayer(id2.toString());
            if (this.G.getDiscord().equals("nulo")) {
                player.sendMessage(ChatColor.GOLD + "Seu Discord n\u00e3o foi verificado!");
                player.sendMessage(ChatColor.GOLD + "Para verificar digite:");
                player.sendMessage(ChatColor.YELLOW + "/link Seu nome no Discord");
                player.sendMessage(ChatColor.GOLD + "Ex: /link Lucas M#4469");
                return true;
            }
            final Regions Region = Reservar.reservatier(id2.toString(), args[1]);
            if (Region != null) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region addowner " + Region.getNome() + " " + id2.toString() + " -w TerraPreGenerated");
                final Location L3 = Region.getPos();
                final double[] coords3 = Reservar.toGeo(L3.getX(), L3.getZ());
                player.chat("/tp area");
                final String result3 = "Coordenadas: " + coords3[1] + ", " + coords3[0];
                final String link3 = "http://www.google.com/maps/place/" + coords3[1] + "," + coords3[0];
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + id2.toString() + " permission set worldedit.* worldguard:region=" + Region.getNome());
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region setpriority " + Region.getNome() + " 1 -w TerraPreGenerated");
                player.sendMessage(ChatColor.GREEN + "Regi\u00e3o Reservada | C\u00f3digo = " + Region.getNome());
                player.sendMessage(ChatColor.GREEN + result3);
                player.sendMessage(ChatColor.BLUE + link3);
                DiscordActs.sendMessage(this.G.getDiscord(), result3);
                DiscordActs.sendMessage(this.G.getDiscord(), link3);
                Reservar.ASRtoAC(Region.getNome(), player.getName());
                return true;
            }
            player.sendMessage(ChatColor.RED + "N\u00e3o foi possivel reservar a regi\u00e3o");
            return true;
        }
        sender.sendMessage(ChatColor.GOLD + "N\u00e3o identificado");
        return false;
    }
    
    public static Grupos getGruposPos(final String search) {
        for (final Grupos d : area.grupo.getValues()) {
            if (d.getUUID() != null && d.getUUID().contains(search)) {
                return d;
            }
        }
        return null;
    }
    
    public static void RemoverGrupo(final String UUIDLider) {
        area.grupo.remove(getGruposPos(UUIDLider));
    }
}
