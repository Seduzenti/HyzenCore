package redehyzen.escolhendo.utils.listeners;

import redehyzen.escolhendo.utils.Main;
import redehyzen.escolhendo.utils.listeners.player.PlayerInteractListener;
import redehyzen.escolhendo.utils.listeners.player.PlayerJoinListener;
import redehyzen.escolhendo.utils.listeners.player.PlayerRestListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Listeners {
  
  public static void setupListeners() {
    try {
      PluginManager pm = Bukkit.getPluginManager();
      
      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerInteractListener(), Main.getInstance());
      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerRestListener(), Main.getInstance());
      pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerJoinListener(), Main.getInstance());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
}
