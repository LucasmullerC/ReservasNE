
package io.github.LucasMullerC.ReservasNE;

import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import io.github.LucasMullerC.Objetos.Gerenciador;
import org.bukkit.event.Listener;

public class Eventos implements Listener
{
    Gerenciador G;
    ReservasNE plugin;
    
    @EventHandler
    public void onJoinEvent(final PlayerJoinEvent event) {
        final Player p = event.getPlayer();
        Cargos.gerenciarcargo(p);
    }
}
