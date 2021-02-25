package io.github.LucasMullerC.ReservasNE;

import io.github.LucasMullerC.Objetos.Gerenciador;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Cargos
{
    public static void gerenciarcargo(final Player p) {
        final UUID id = p.getUniqueId();
        if (Reservar.getPlayer(id.toString()) == null) {
            p.sendMessage(ChatColor.YELLOW + "Para ganhar seus cargos, Digite /link Seu nome no Discord");
            p.sendMessage(ChatColor.RED + "Ex: /link Lucas M#4469");
        }
        else if (Reservar.getPlayer(id.toString()).getDiscord().equals("nulo")) {
            p.sendMessage(ChatColor.YELLOW + "Para ganhar seus cargos, Digite /link Seu nome no Discord");
            p.sendMessage(ChatColor.RED + "Ex: /link Lucas M#4469");
        }
        else {
            final Gerenciador G = Reservar.getPlayer(id.toString());
            final String cargo = DiscordActs.getCargo(G.getDiscord());
            if (G.getCargo()) {
                if (cargo.equals("nulo")) {
                    G.setCargo(false);
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + G.getUUID() + " group remove Builder_Tieri");
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + G.getUUID() + " group remove Builder_Tierii");
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + G.getUUID() + " group remove Builder_Tieriii");
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + G.getUUID() + " group remove builder");
                    p.sendMessage(ChatColor.RED + "Seus cargos foram removidos pois voc\u00ea n\u00e3o \u00e9 mais um Builder.");
                }
            }
            else if (cargo.equals("nulo")) {
                p.sendMessage(ChatColor.GOLD + "Para poder construir considere se tornar um builder em nosso time!");
                p.sendMessage(ChatColor.GOLD + "Junte-se ao nosso Discord para mais informa\u00e7\u00f5es: " + ChatColor.BLUE + "https://discord.gg/nXX3F9Z");
            }
            else {
                if (cargo.equals("t1")) {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + G.getUUID() + " group add Builder_Tieri");
                }
                else if (cargo.equals("t2")) {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + G.getUUID() + " group add Builder_Tierii");
                }
                else if (cargo.equals("t3")) {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + G.getUUID() + " group add Builder_Tieriii");
                }
                else if (cargo.equals("t4")) {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + G.getUUID() + " group add builder");
                }
                G.setCargo(true);
                p.sendMessage(ChatColor.GREEN + "Seus cargos foram adicionados! Bem-vindo ao time!");
            }
        }
    }
}
