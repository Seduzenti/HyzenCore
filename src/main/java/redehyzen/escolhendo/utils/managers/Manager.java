package redehyzen.escolhendo.utils.managers;

import redehyzen.escolhendo.utils.Main;
import escolhendo.apexstore.services.nms.NMS;
import escolhendo.apexstore.services.plugin.config.ApexConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Manager {
  
  public static final ApexConfig CONFIG = Main.getInstance().getConfig("config");
  public static List<Player> USING_VANISH;
  
  public static boolean isVanished(Player player) {
    return USING_VANISH.contains(player);
  }
  
  public static void addVanish(Player player) {
    USING_VANISH.add(player);
  }
  
  public static void removeVanish(Player player) {
    USING_VANISH.remove(player);
  }
  
  public static void setupManager() {
    USING_VANISH = new ArrayList<>();
    
    new BukkitRunnable() {
      @Override
      public void run() {
        Bukkit.getOnlinePlayers().stream().filter(player -> USING_VANISH.contains(player)).forEach(player -> {
          NMS.sendActionBar(player, "§aVocê está invisível para os outros jogadores!");
          Bukkit.getOnlinePlayers().stream().filter(collect -> !collect.hasPermission("utils.vanish.see"))
              .forEach(collect -> collect.hidePlayer(player));
        });
      }
    }.runTaskTimer(Main.getInstance(), 1, 1);
  }
}
