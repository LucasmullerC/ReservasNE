// 
// Decompiled by Procyon v0.5.36
// 

package io.github.LucasMullerC.ReservasNE.Comandos;

import java.util.UUID;
import io.github.LucasMullerC.ReservasNE.Cargos;
import io.github.LucasMullerC.ReservasNE.DiscordActs;
import org.bukkit.ChatColor;
import io.github.LucasMullerC.ReservasNE.Reservar;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import io.github.LucasMullerC.Objetos.Gerenciador;
import org.bukkit.command.CommandExecutor;

public class link implements CommandExecutor
{
    Gerenciador G;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player)sender;
        final UUID id = player.getUniqueId();
        if (args[0] == null) {
            return false;
        }
        String msg = "";
        for (int i = 0; i < args.length; ++i) {
            msg = msg + args[i] + " ";
        }
        msg = msg.trim();
        if (Reservar.getPlayer(id.toString()) == null) {
            Reservar.LinkDiscord(id.toString(), msg);
        }
        this.G = Reservar.getPlayer(id.toString());
        if (!this.G.getDiscord().equals("nulo")) {
            player.sendMessage(ChatColor.GREEN + "Seu Discord j\u00e1 foi verificado!");
            return true;
        }
        System.out.println(msg);
        final String discord = DiscordActs.CheckDiscord(msg);
        if (discord.equals("nulo")) {
            player.sendMessage(ChatColor.RED + "Discord n\u00e3o identificado, Verifique se voc\u00ea est\u00e1 conectado em nosso servidor ou se voc\u00ea colocou o nome e o c\u00f3digo exato.");
            player.sendMessage(ChatColor.GOLD + "Ex: /link Lucas M#4469");
            return true;
        }
        this.G.setDiscord(discord);
        player.sendMessage(ChatColor.GREEN + "Discord verificado!");
        Cargos.gerenciarcargo(player);
        return true;
    }
}
