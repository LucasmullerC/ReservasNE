
package io.github.LucasMullerC.ReservasNE.Comandos;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import io.github.LucasMullerC.ReservasNE.Reservar;
import org.bukkit.command.CommandExecutor;

public class salvar implements CommandExecutor
{
    Reservar R;
    area GP;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("salvar")) {
            final Player player = (Player)sender;
            final Reservar r = this.R;
            Reservar.pendente.save();
            final Reservar r2 = this.R;
            Reservar.area.save();
            final Reservar r3 = this.R;
            Reservar.player.save();
            final Reservar r4 = this.R;
            Reservar.ASR.save();
            final Reservar r5 = this.R;
            Reservar.AC.save();
            final Reservar r6 = this.R;
            Reservar.AF.save();
            final area gp = this.GP;
            area.grupo.save();
            player.sendMessage(ChatColor.GREEN + "ReservasNE Salvo!");
        }
        return false;
    }
}
