
package io.github.LucasMullerC.ReservasNE.Comandos;

import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import io.github.LucasMullerC.ReservasNE.Reservar;
import org.bukkit.entity.Player;
import io.github.LucasMullerC.Objetos.Areas;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class add implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Reservar R = null;
        ArrayList<Areas> Pendente = new ArrayList<Areas>();
        if (!(sender instanceof Player)) {
            return false;
        }
        if (!cmd.getName().equalsIgnoreCase("add")) {
            sender.sendMessage("Deve ser um jogador!");
            return false;
        }
        if (args.length == 0) {
            Reservar.AP.load();
            final Player player = (Player)sender;
            Pendente = Reservar.getAreaPos();
            if (Pendente == null) {
                sender.sendMessage(ChatColor.GREEN + "A lista est\u00e1 vazia!");
                return true;
            }
            for (int i = 0; i < Pendente.size(); ++i) {
                final Double X1 = Double.parseDouble(Pendente.get(i).getP1x());
                final Double X2 = Double.parseDouble(Pendente.get(i).getP1y());
                final double[] P1 = Reservar.fromGeo(X2, X1);
                String C1 = String.valueOf(P1[0]);
                String C2 = String.valueOf(P1[1]);
                final String[] Coords = C1.split("\\.");
                final String[] Coords2 = C2.split("\\.");
                final String result = Coords[0] + ",0," + Coords2[0];
                player.chat("//pos1 " + result);
                final Double X3 = Double.parseDouble(Pendente.get(i).getP3x().replace(",", "."));
                final Double X4 = Double.parseDouble(Pendente.get(i).getP3y().replace(",", "."));
                final double[] P2 = Reservar.fromGeo(X4, X3);
                C1 = String.valueOf(P2[0]);
                C2 = String.valueOf(P2[1]);
                final String[] Coords3 = C1.split("\\.");
                final String[] Coords4 = C2.split("\\.");
                final String result2 = Coords3[0] + ",0," + Coords4[0];
                player.chat("//pos2 " + result2);
                final Location pos = player.getLocation();
                final int x = Integer.parseInt(Coords3[0]);
                final int z = Integer.parseInt(Coords4[0]);
                final int y = 100;
                final World w = pos.getWorld();
                final Location loc = new Location(w, (double)x, (double)y, (double)z);
                final String area = Reservar.addarea(Pendente.get(i).getNome(), Pendente.get(i).getDificuldade(), loc);
                player.chat("//expand 10000 u");
                player.chat("//expand 10000 d");
                player.chat("/region define " + area);
                final Areas A = Pendente.get(i);
                A.setNome(area);
                Reservar.addSemReserva(Pendente.get(i));
                sender.sendMessage(ChatColor.GREEN + area + " Adicionada!");
            }
            Pendente.clear();
            Reservar.limpapend();
            sender.sendMessage(ChatColor.GOLD + " Lista Limpa!");
            return true;
        }
        else {
            if (args[0] + args[1] == null) {
                return false;
            }
            final Player player = (Player)sender;
            final int N = Integer.parseInt(args[1]);
            if (N < 0 && N > 4) {
                sender.sendMessage(ChatColor.RED + "Dificuldade deve ser entre 0 e 4!");
                return true;
            }
            final Location pos2 = player.getLocation();
            final String area2 = Reservar.addarea(args[0], args[1], pos2);
            player.chat("//expand vert");
            player.chat("/region define " + area2);
            sender.sendMessage(ChatColor.GREEN + area2 + " Adicionada!");
            return true;
        }
    }
}
