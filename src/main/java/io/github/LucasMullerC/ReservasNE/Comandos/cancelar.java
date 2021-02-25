
package io.github.LucasMullerC.ReservasNE.Comandos;

import io.github.LucasMullerC.Objetos.Grupos;
import io.github.LucasMullerC.Objetos.Gerenciador;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import io.github.LucasMullerC.ReservasNE.Reservar;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class cancelar implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("cancelar")) {
            final Player player = (Player)sender;
            final UUID id = player.getUniqueId();
            if (args.length == 0) {
                boolean Stats = false;
                final Gerenciador G = Reservar.getPlayer(id.toString());
                final Grupos GP = area.getGruposPos(id.toString());
                if (GP != null) {
                    if (GP.getPlayer1().equals(id.toString())) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer1() + " -w TerraPreGenerated");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer1() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                        Stats = Reservar.Cancelar(GP.getPlayer1());
                        GP.setPlayer1("nulo");
                    }
                    else if (GP.getPlayer2().equals(id.toString())) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer2() + " -w TerraPreGenerated");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer1() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                        Stats = Reservar.Cancelar(GP.getPlayer2());
                        GP.setPlayer2("nulo");
                    }
                    else if (GP.getPlayer3().equals(id.toString())) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer3() + " -w TerraPreGenerated");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer1() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                        Stats = Reservar.Cancelar(GP.getPlayer3());
                        GP.setPlayer3("nulo");
                    }
                    else if (GP.getUUID().equals(id.toString())) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + id.toString() + " -w TerraPreGenerated");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + id.toString() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region setpriority " + G.getRegiao() + " 0 -w TerraPreGenerated");
                        if (!GP.getPlayer1().equals("nulo")) {
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer1() + " -w TerraPreGenerated");
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer1() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                            Reservar.Cancelar(GP.getPlayer1());
                            GP.setPlayer1("nulo");
                        }
                        if (!GP.getPlayer2().equals("nulo")) {
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer2() + " -w TerraPreGenerated");
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer2() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                            Reservar.Cancelar(GP.getPlayer2());
                            GP.setPlayer2("nulo");
                        }
                        if (!GP.getPlayer3().equals("nulo")) {
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer3() + " -w TerraPreGenerated");
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer3() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                            Reservar.Cancelar(GP.getPlayer3());
                            GP.setPlayer3("nulo");
                        }
                        Reservar.ACtoASR(G.getRegiao());
                        Stats = Reservar.Cancelar(id.toString());
                        area.RemoverGrupo(id.toString());
                        sender.sendMessage(ChatColor.GOLD + "A regi\u00e3o foi cancelada para todo o grupo!");
                    }
                }
                else {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + id.toString() + " -w TerraPreGenerated");
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + id.toString() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region setpriority " + G.getRegiao() + " 0 -w TerraPreGenerated");
                    if (G.getRegiao().toLowerCase().indexOf("apply".toLowerCase()) != -1) {
                        System.out.println("Cancelou apply");
                    }
                    else {
                        Reservar.ACtoASR(G.getRegiao());
                    }
                    Stats = Reservar.Cancelar(id.toString());
                }
                if (Stats) {
                    sender.sendMessage(ChatColor.GREEN + "Regi\u00e3o cancelada, Voc\u00ea pode reservar outra regi\u00e3o.");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Voc\u00ea n\u00e3o tem nenhuma regi\u00e3o reservada!");
                return true;
            }
            else if (args[0] != null) {
                final Gerenciador G = Reservar.getPlayer(args[0]);
                final Grupos GP = area.getGruposPos(args[0]);
                if (GP != null) {
                    if (GP.getPlayer1().equals(id.toString())) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer1() + " -w TerraPreGenerated");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer1() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                        final boolean Stats = Reservar.Cancelar(GP.getPlayer1());
                        GP.setPlayer1("nulo");
                    }
                    else if (GP.getPlayer2().equals(id.toString())) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer2() + " -w TerraPreGenerated");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer1() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                        final boolean Stats = Reservar.Cancelar(GP.getPlayer2());
                        GP.setPlayer2("nulo");
                    }
                    else if (GP.getPlayer3().equals(id.toString())) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer3() + " -w TerraPreGenerated");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer1() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                        final boolean Stats = Reservar.Cancelar(GP.getPlayer3());
                        GP.setPlayer3("nulo");
                    }
                    else if (GP.getUUID().equals(id.toString())) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + id.toString() + " -w TerraPreGenerated");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + id.toString() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region setpriority " + G.getRegiao() + " 0 -w TerraPreGenerated");
                        if (!GP.getPlayer1().equals("nulo")) {
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer1() + " -w TerraPreGenerated");
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer1() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                            Reservar.Cancelar(GP.getPlayer1());
                            GP.setPlayer1("nulo");
                        }
                        if (!GP.getPlayer2().equals("nulo")) {
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer2() + " -w TerraPreGenerated");
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer2() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                            Reservar.Cancelar(GP.getPlayer2());
                            GP.setPlayer2("nulo");
                        }
                        if (!GP.getPlayer3().equals("nulo")) {
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer3() + " -w TerraPreGenerated");
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer3() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                            Reservar.Cancelar(GP.getPlayer3());
                            GP.setPlayer3("nulo");
                        }
                        Reservar.ACtoASR(G.getRegiao());
                        final boolean Stats = Reservar.Cancelar(id.toString());
                        area.RemoverGrupo(id.toString());
                        sender.sendMessage(ChatColor.GOLD + "A regi\u00e3o foi cancelada para todo o grupo!");
                    }
                }
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + args[0] + " -w TerraPreGenerated");
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + args[0] + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region setpriority " + G.getRegiao() + " 0 -w TerraPreGenerated");
                final boolean Stats = Reservar.Cancelar(args[0]);
                if (Stats) {
                    Reservar.ACtoASR(G.getRegiao());
                    sender.sendMessage(ChatColor.GREEN + "Regi\u00e3o cancelada, Voc\u00ea pode reservar outra regi\u00e3o.");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Voc\u00ea n\u00e3o tem nenhuma regi\u00e3o reservada!");
                return true;
            }
        }
        return false;
    }
}
