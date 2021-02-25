
package io.github.LucasMullerC.ReservasNE.Comandos;

import org.bukkit.Location;
import io.github.LucasMullerC.Objetos.Regions;
import java.util.UUID;
import io.github.LucasMullerC.ReservasNE.DiscordActs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import io.github.LucasMullerC.Objetos.Gerenciador;
import io.github.LucasMullerC.ReservasNE.Reservar;
import org.bukkit.command.CommandExecutor;

public class claim implements CommandExecutor
{
    Reservar R;
    Gerenciador G;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("claim")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "Inv\u00e1lido");
                return true;
            }
            if (args[0] + args[1] != null) {
                final Player player = (Player)sender;
                final UUID id = player.getUniqueId();
                this.G = Reservar.getPlayer(id.toString());
                if (this.G.getDiscord().equals("nulo")) {
                    player.sendMessage(ChatColor.GOLD + "Seu Discord n\u00e3o foi verificado!");
                    player.sendMessage(ChatColor.GOLD + "Para verificar digite:");
                    player.sendMessage(ChatColor.YELLOW + "/link Seu nome no Discord");
                    player.sendMessage(ChatColor.GOLD + "Ex: /link Lucas M#4469");
                    return true;
                }
                final Regions Region = Reservar.checkplayer(id.toString(), args[0], args[1]);
                if (Region == null) {
                    player.sendMessage(ChatColor.RED + "N\u00e3o foi possivel fazer a reserva!");
                    player.sendMessage(ChatColor.RED + "Confira se voc\u00ea j\u00e1 n\u00e3o tem uma \u00e1rea reservada ou escolheu uma dificuldade acima do seu Rank.");
                    return true;
                }
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region addowner " + Region.getNome() + " " + id.toString() + " -w TerraPreGenerated");
                final Location L = Region.getPos();
                final double[] coords = Reservar.toGeo(L.getX(), L.getZ());
                player.chat("/tp area");
                final String result = "Coordenadas: " + coords[1] + ", " + coords[0];
                final String link = "http://www.google.com/maps/place/" + coords[1] + "," + coords[0];
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + id.toString() + " permission set worldedit.* worldguard:region=" + Region.getNome());
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region setpriority " + Region.getNome() + " 1 -w TerraPreGenerated");
                player.sendMessage(ChatColor.GREEN + "Regi\u00e3o Reservada | C\u00f3digo = " + Region.getNome());
                player.sendMessage(ChatColor.GREEN + result);
                player.sendMessage(ChatColor.BLUE + link);
                DiscordActs.sendMessage(this.G.getDiscord(), result);
                DiscordActs.sendMessage(this.G.getDiscord(), link);
                if (Region.getNome().toLowerCase().indexOf("apply".toLowerCase()) != -1) {
                    return true;
                }
                Reservar.ASRtoAC(Region.getNome(), player.getName());
                return true;
            }
        }
        return false;
    }
}
