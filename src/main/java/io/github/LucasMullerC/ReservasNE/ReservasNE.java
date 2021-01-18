package io.github.LucasMullerC.ReservasNE;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.LucasMullerC.ReservasNE.Comandos.add;
import io.github.LucasMullerC.ReservasNE.Comandos.area;
import io.github.LucasMullerC.ReservasNE.Comandos.cancelar;
import io.github.LucasMullerC.ReservasNE.Comandos.claim;
import io.github.LucasMullerC.ReservasNE.Comandos.exibir;
import io.github.LucasMullerC.ReservasNE.Comandos.finalizar;
import io.github.LucasMullerC.ReservasNE.Comandos.player;
import io.github.LucasMullerC.ReservasNE.Comandos.reserva;
import io.github.LucasMullerC.ReservasNE.util.GrupoStore;
import io.github.LucasMullerC.ReservasNE.util.ListStore;
import io.github.LucasMullerC.ReservasNE.util.PlayerStore;

public final class ReservasNE extends JavaPlugin {
	Reservar R;

	public void onEnable() {
		getCommand("add").setExecutor(new add());
		getCommand("exibir").setExecutor(new exibir());
		getCommand("claim").setExecutor(new claim());
		getCommand("finalizar").setExecutor(new finalizar());
		getCommand("cancelar").setExecutor(new cancelar());
		getCommand("reserva").setExecutor(new reserva());
		getCommand("player").setExecutor(new player());
		getCommand("area").setExecutor(new area());
		

		String pluginFolder = this.getDataFolder().getAbsolutePath();
		(new File(pluginFolder)).mkdirs();
		R.area = new ListStore(new File(pluginFolder + File.separator + "Areas-Reservas.txt"));
		R.player = new PlayerStore(new File(pluginFolder + File.separator + "Data-Players.txt"));
		reserva.grupo = new GrupoStore(new File(pluginFolder + File.separator + "Grupos.txt"));
		reserva.grupo.load();
		R.area.load();
		R.player.load();
		getLogger().info("Reservas BTE NE Ativado!");
	}

	public void onDisable() {
		if (R.area.getValues().isEmpty() == false) {
			R.area.save();
		}
		if (R.player.getValues().isEmpty() == false) {
			R.player.save();
		}
		if (reserva.grupo.getValues().isEmpty() == false) {
			reserva.grupo.save();
		}
		getLogger().info("Reservas BTE NE Desativado!");
	}
	
	@EventHandler
	public void PlayerJoin(PlayerLoginEvent event) {
	Player p = event.getPlayer();
	if(!p.hasPlayedBefore()) {
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName("Menu");
		item.setItemMeta(itemmeta);
		p.getInventory().addItem(new ItemStack(item));
	}
	}

	static public String getStringLocation(final Location l) {
		if (l == null) {
			return "";
		}
		return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
	}

	static public Location getLocationString(final String s) {
		if (s == null || s.trim() == "") {
			return null;
		}
		final String[] parts = s.split(":");
		if (parts.length == 4) {
			final World w = Bukkit.getServer().getWorld(parts[0]);
			final int x = Integer.parseInt(parts[1]);
			final int y = Integer.parseInt(parts[2]);
			final int z = Integer.parseInt(parts[3]);
			return new Location(w, x, y, z);
		}
		return null;
	}

}
