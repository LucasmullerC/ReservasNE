package io.github.LucasMullerC.ReservasNE;

import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import net.dv8tion.jda.api.JDA;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.JDABuilder;
import io.github.LucasMullerC.ReservasNE.util.GrupoStore;
import io.github.LucasMullerC.ReservasNE.util.AreaFinalizada;
import io.github.LucasMullerC.ReservasNE.util.AreaEmConstrucao;
import io.github.LucasMullerC.ReservasNE.util.Pendente;
import io.github.LucasMullerC.ReservasNE.util.Mapscsv;
import io.github.LucasMullerC.ReservasNE.util.PlayerStore;
import io.github.LucasMullerC.ReservasNE.util.ListStore;
import java.io.File;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import io.github.LucasMullerC.ReservasNE.Comandos.link;
import io.github.LucasMullerC.ReservasNE.Comandos.reloadne;
import io.github.LucasMullerC.ReservasNE.Comandos.salvar;
import io.github.LucasMullerC.ReservasNE.Comandos.player;
import io.github.LucasMullerC.ReservasNE.Comandos.cancelar;
import io.github.LucasMullerC.ReservasNE.Comandos.finalizar;
import io.github.LucasMullerC.ReservasNE.Comandos.claim;
import io.github.LucasMullerC.ReservasNE.Comandos.exibir;
import org.bukkit.command.CommandExecutor;
import io.github.LucasMullerC.ReservasNE.Comandos.add;
import org.bukkit.configuration.file.FileConfiguration;
import io.github.LucasMullerC.ReservasNE.Comandos.area;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReservasNE extends JavaPlugin
{
    Reservar R;
    area GP;
    FileConfiguration config;
    
    public ReservasNE() {
        this.config = this.getConfig();
    }
    
    public void onEnable() {
        this.getCommand("add").setExecutor((CommandExecutor)new add());
        this.getCommand("exibir").setExecutor((CommandExecutor)new exibir());
        this.getCommand("claim").setExecutor((CommandExecutor)new claim());
        this.getCommand("finalizar").setExecutor((CommandExecutor)new finalizar());
        this.getCommand("cancelar").setExecutor((CommandExecutor)new cancelar());
        this.getCommand("player").setExecutor((CommandExecutor)new player());
        this.getCommand("area").setExecutor((CommandExecutor)new area());
        this.getCommand("salvar").setExecutor((CommandExecutor)new salvar());
        this.getCommand("reloadne").setExecutor((CommandExecutor)new reloadne());
        this.getCommand("link").setExecutor((CommandExecutor)new link());
        this.getServer().getPluginManager().registerEvents((Listener)new Eventos(), (Plugin)this);
        String pluginFolder = this.getDataFolder().getAbsolutePath();
        new File(pluginFolder).mkdirs();
        Reservar.area = new ListStore(new File(pluginFolder + File.separator + "Areas-Reservas.txt"));
        Reservar.player = new PlayerStore(new File(pluginFolder + File.separator + "Data-Players.txt"));
        Reservar.pendente = new Mapscsv(new File(pluginFolder + File.separator + "pendente.txt"));
        Reservar.AP = new Pendente(new File(pluginFolder + File.separator + "areas.txt"));
        Reservar.ASR = new Pendente(new File(pluginFolder + File.separator + "AreasSemReserva.txt"));
        Reservar.AC = new AreaEmConstrucao(new File(pluginFolder + File.separator + "AreasEmConstrucao.txt"));
        Reservar.AF = new AreaFinalizada(new File(pluginFolder + File.separator + "AreasFinalizadas.txt"));
        area.grupo = new GrupoStore(new File(pluginFolder + File.separator + "Grupos.txt"));
        area.grupo.load();
        Reservar.area.load();
        Reservar.ASR.load();
        Reservar.AF.load();
        Reservar.AC.load();
        Reservar.player.load();
        Reservar.pendente.load();
        try {
            final JDA jda = JDABuilder.createDefault("NzgwNzMzMjgxOTM4NTcxMjY1.X7zYhw.sWhB3eohghJSHNFD_-GjW3l4P40").setChunkingFilter(ChunkingFilter.ALL).setMemberCachePolicy(MemberCachePolicy.ALL).enableIntents(GatewayIntent.GUILD_MEMBERS, new GatewayIntent[0]).build();
            jda.awaitReady();
            System.out.println("Finished Building JDA!");
            DiscordActs discordActs = new DiscordActs(jda);
        }
        catch (LoginException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        this.getLogger().info("Reservas BTE NE Ativado!");
    }
    
    public void onDisable() {
        Reservar r = this.R;
        Reservar r2 = this.R;
        Reservar r3 = this.R;
        Reservar r4 = this.R;
        Reservar r5 = this.R;
        Reservar r6 = this.R;
        area gp = this.GP;
        area.grupo.save();
        this.getLogger().info("Reservas BTE NE Desativado!");
    }
    
    public static String getStringLocation(final Location l) {
        if (l == null) {
            return "";
        }
        return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }
    
    public static Location getLocationString(final String s) {
        if (s == null || s.trim() == "") {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            return new Location(w, (double)x, (double)y, (double)z);
        }
        return null;
    }
}
