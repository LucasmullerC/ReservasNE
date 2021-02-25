
package io.github.LucasMullerC.ReservasNE.Comandos;

import io.github.LucasMullerC.Objetos.Grupos;
import org.bukkit.Location;
import io.github.LucasMullerC.Objetos.Regions;
import io.github.LucasMullerC.Objetos.DoneAreas;
import org.bukkit.Bukkit;
import java.util.UUID;
import io.github.LucasMullerC.ReservasNE.DiscordActs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import io.github.LucasMullerC.Objetos.Gerenciador;
import io.github.LucasMullerC.ReservasNE.ReservasNE;
import io.github.LucasMullerC.ReservasNE.Reservar;
import org.bukkit.command.CommandExecutor;

public class finalizar implements CommandExecutor
{
    Reservar Re;
    ReservasNE plugin;
    Gerenciador G;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("finalizar")) {
            if (args.length == 0) {
                final Player player = (Player)sender;
                final UUID id = player.getUniqueId();
                final String nome = player.getName();
                boolean Stats = false;
                this.G = Reservar.getPlayer(id.toString());
                if (this.G.getGrupo() && area.getGruposPos(id.toString()) == null) {
                    player.sendMessage(ChatColor.GOLD + "Somente o lider da reserva pode finalizar.");
                    player.sendMessage(ChatColor.RED + "Caso n\u00c3£o queira mais construir aqui digite /cancelar");
                    player.sendMessage(ChatColor.YELLOW + "Contate um administrador caso o lider esteja inativo!");
                    return true;
                }
                if (this.G.getDiscord().equals("nulo")) {
                    player.sendMessage(ChatColor.GOLD + "Seu Discord n\u00c3£o foi verificado!");
                    player.sendMessage(ChatColor.GOLD + "Para verificar digite:");
                    player.sendMessage(ChatColor.YELLOW + "/link Seu nome no Discord");
                    player.sendMessage(ChatColor.GOLD + "Ex: /link Lucas M#4469");
                    return true;
                }
                final String discord = this.G.getDiscord();
                Stats = Reservar.addPendente(id.toString(), nome);
                if (Stats) {
                    DiscordActs.AnalisarReserva();
                    player.sendMessage(ChatColor.GREEN + "Reserva Finalizada com sucesso!");
                    player.sendMessage(ChatColor.GOLD + "Um administrador ir\u00c3¡ analisar sua reserva, voc\u00c3ª ser\u00c3¡ notificado pelo nosso servidor do Discord.");
                    return true;
                }
                player.sendMessage(ChatColor.GOLD + "Voc\u00c3ª n\u00c3£o tem regi\u00c3µes reservadas!");
                return false;
            }
            else if (args[0].equalsIgnoreCase("analise")) {
                final Player player2 = (Player)sender;
                final DoneAreas D = Reservar.getPendente();
                final Regions R = Reservar.getRegiao(D.getNome());
                String msg = "";
                for (int i = 1; i < args.length; ++i) {
                    msg = msg + args[i] + " ";
                }
                msg = msg.trim();
                final String[] arrayValores = msg.split(" ");
                if (arrayValores[0].equalsIgnoreCase("confirmar")) {
                    FinalizarReserva(D.getPlayer());
                    Reservar.RemoverArea(R.getNome());
                    if (R.getNome().toLowerCase().indexOf("apply".toLowerCase()) != -1) {
                        System.out.println("Finalizou apply");
                    }
                    else {
                        Reservar.ACtoAF(R.getNome());
                    }
                    Reservar.RemoverPendente();
                    final UUID idp = UUID.fromString(D.getPlayer());
                    final Player conf = Bukkit.getPlayer(idp);
                    final String nomeP = conf.getName();
                    this.G = Reservar.getPlayer(D.getPlayer());
                    final String txt = "**" + nomeP + "** Sua reserva foi aceita! agora voce pode reservar outra regiao.";
                    DiscordActs.sendMessage(this.G.getDiscord(), txt);
                    player2.sendMessage(ChatColor.GOLD + "Reserva confirmada.");
                    return true;
                }
                if (arrayValores[0].equalsIgnoreCase("recusar")) {
                    String motivo = "";
                    for (int j = 1; j < arrayValores.length; ++j) {
                        motivo = motivo + arrayValores[j] + " ";
                    }
                    motivo = motivo.trim();
                    final UUID idp2 = UUID.fromString(D.getPlayer());
                    final Player conf2 = Bukkit.getPlayer(idp2);
                    final String nomeP2 = conf2.getName();
                    this.G = Reservar.getPlayer(D.getPlayer());
                    Reservar.RemoverPendente();
                    final String txt2 = "**" + nomeP2 + "** Sua reserva foi recusada pelo motivo: " + motivo + " | Va ate sua reserva e corrija os problemas!";
                    DiscordActs.sendMessage(this.G.getDiscord(), txt2);
                    player2.sendMessage(ChatColor.GOLD + "Reserva recusada.");
                    return true;
                }
                final Location L = R.getPos();
                final double[] coords = Reservar.toGeo(L.getX(), L.getZ());
                player2.chat("/tpll " + coords[1] + " " + coords[0]);
                player2.sendMessage(ChatColor.GOLD + "Digite /finalizar analise confirmar - Para aceitar a reserva.");
                player2.sendMessage(ChatColor.GOLD + "Ou digite /finalizar analise Recusar (Motivo) - Para recusar a reserva.");
                return true;
            }
        }
        return false;
    }
    
    private static void FinalizarReserva(final String Nome) {
        final Gerenciador G = Reservar.getPlayer(Nome);
        final Grupos GP = area.getGruposPos(Nome);
        boolean Stats = false;
        Player p = null;
        String UUIDid = null;
        if (GP != null) {
            if (!GP.getPlayer1().equals("nulo")) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer1() + " -w TerraPreGenerated");
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer1() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                Stats = Reservar.Finalizar(GP.getPlayer1());
                UUIDid = GP.getPlayer1();
                final UUID id = UUID.fromString(UUIDid);
                p = Bukkit.getPlayer(id);
                if (Stats) {
                    Rank(Stats, UUIDid, p);
                }
                else {
                    p.sendMessage(ChatColor.RED + "Voc\u00c3ª n\u00c3£o tem nenhuma regi\u00c3£o reservada!");
                }
            }
            if (!GP.getPlayer2().equals("nulo")) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer2() + " -w TerraPreGenerated");
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer2() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                Stats = Reservar.Finalizar(GP.getPlayer2());
                UUIDid = GP.getPlayer2();
                final UUID id = UUID.fromString(UUIDid);
                p = Bukkit.getPlayer(id);
                if (Stats) {
                    Rank(Stats, UUIDid, p);
                }
                else {
                    p.sendMessage(ChatColor.RED + "Voc\u00c3ª n\u00c3£o tem nenhuma regi\u00c3£o reservada!");
                }
            }
            if (!GP.getPlayer3().equals("nulo")) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + GP.getPlayer3() + " -w TerraPreGenerated");
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + GP.getPlayer3() + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
                Stats = Reservar.Finalizar(GP.getPlayer3());
                UUIDid = GP.getPlayer3();
                final UUID id = UUID.fromString(UUIDid);
                p = Bukkit.getPlayer(id);
                if (Stats) {
                    Rank(Stats, UUIDid, p);
                }
                else {
                    p.sendMessage(ChatColor.RED + "Voc\u00c3ª n\u00c3£o tem nenhuma regi\u00c3£o reservada!");
                }
            }
            area.RemoverGrupo(G.getUUID());
        }
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region removeowner " + G.getRegiao() + " " + Nome + " -w TerraPreGenerated");
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + Nome + " permission unset worldedit.* worldguard:region=" + G.getRegiao());
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "region remove " + G.getRegiao() + " -w TerraPreGenerated");
        Stats = Reservar.Finalizar(Nome);
        final UUID id = UUID.fromString(Nome);
        p = Bukkit.getPlayer(id);
        if (Stats) {
            Rank(Stats, Nome, p);
        }
        else {
            p.sendMessage(ChatColor.RED + "Voc\u00c3ª n\u00c3£o tem nenhuma regi\u00c3£o reservada!");
        }
    }
    
    private static void Rank(final Boolean Stats, final String UUID, final Player p) {
        final int rank = Reservar.Promocao(UUID);
        final Gerenciador G = Reservar.getPlayer(UUID);
        if (rank == 2) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + UUID + " group add Builder_Tierii");
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + UUID + " group remove Builder_Tieri");
            DiscordActs.addCargo(UUID, rank, G.getDiscord());
            final String txt1 = "Parab\u00c3©ns voc\u00c3ª subiu para o rank Builder Tier II!";
            final String txt2 = "Voc\u00c3ª vai precisar de mais 5 Builds simples e 15 Builds medianas para o proximo rank!";
            p.sendMessage(ChatColor.GREEN + txt1);
            p.sendMessage(ChatColor.WHITE + txt2);
            DiscordActs.sendMessage(G.getDiscord(), txt1);
            DiscordActs.sendMessage(G.getDiscord(), txt2);
        }
        else if (rank == 3) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + UUID + " group add Builder_Tieriii");
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + UUID + " group remove Builder_Tierii");
            DiscordActs.addCargo(UUID, rank, G.getDiscord());
            final String txt1 = "Parab\u00c3©ns voc\u00c3ª subiu para o rank Builder Tier III!";
            final String txt2 = "Voc\u00c3ª vai precisar de mais 5 Builds simples e 10 Builds medianas e 10 Build Complexas para o proximo rank!";
            p.sendMessage(ChatColor.GREEN + txt1);
            p.sendMessage(ChatColor.WHITE + txt2);
            DiscordActs.sendMessage(G.getDiscord(), txt1);
            DiscordActs.sendMessage(G.getDiscord(), txt2);
        }
        else if (rank == 4) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + UUID + " group add builder");
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + UUID + " group remove Builder_Tieriii");
            DiscordActs.addCargo(UUID, rank, G.getDiscord());
            final String txt1 = "Parab\u00c3©ns voc\u00c3ª subiu para o rank Builder PRO!";
            final String txt2 = "Voc\u00c3ª agora voc\u00c3ª pode fazer claims aonde quiser, Use o Discord para mais informa\u00c3§\u00c3µes.";
            p.sendMessage(ChatColor.GREEN + txt1);
            p.sendMessage(ChatColor.WHITE + txt2);
            DiscordActs.sendMessage(G.getDiscord(), txt1);
            DiscordActs.sendMessage(G.getDiscord(), txt2);
        }
        else if (rank == 1) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "lp user " + UUID + " group remove Applier");
            p.sendMessage(ChatColor.GREEN + "Parab\u00c3©ns voc\u00c3ª concluiu sua build de teste!");
            p.sendMessage(ChatColor.WHITE + "Se voc\u00c3ª j\u00c3¡ enviou seu cadastro com a screenshot no site do Build The Earth, Espere at\u00c3© um administrador analisar seu cadastro.");
            p.sendMessage(ChatColor.WHITE + "Se voc\u00c3ª estiver em nosso Discord, ser\u00c3¡ avisado quando seu cadastro for aceito.");
        }
        else {
            p.sendMessage(ChatColor.GREEN + "Regi\u00c3£o finalizada! Agora voc\u00c3ª pode fazer outra reserva.");
        }
    }
}
