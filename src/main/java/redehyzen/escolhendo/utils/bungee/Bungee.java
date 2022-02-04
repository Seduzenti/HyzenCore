package redehyzen.escolhendo.utils.bungee;

import redehyzen.escolhendo.utils.bungee.commands.Commands;
import redehyzen.escolhendo.utils.bungee.listeners.Listeners;
import redehyzen.escolhendo.utils.bungee.manager.MaintenanceManager;
import redehyzen.escolhendo.utils.bungee.plugin.logger.HyzenLogger;
import escolhendo.apexstore.services.database.Database;
import escolhendo.apexstore.services.player.role.Role;
import escolhendo.apexstore.services.utils.StringUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static escolhendo.apexstore.services.utils.StringUtils.formatColors;

public class Bungee extends Plugin {
  
  public static Bungee instance;
  
  public Bungee() {
    try {
      Field field = Plugin.class.getDeclaredField("logger");
      field.setAccessible(true);
      field.set(this, new HyzenLogger(this));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    instance = this;
  }
  
  @Override
  public void onEnable() {
    getProxy().getPluginManager().registerListener(this, new Listeners());
    
    Settings.setupSettings();
    Commands.setupCommands();
    MaintenanceManager.setupMaintenance();
    
    this.getLogger().info("O plugin foi ativado.");
  }
  
  @Override
  public void onDisable() {
    this.getLogger().info("O plugin foi desativado.");
  }
  
  public static final Map<ProxiedPlayer,
      ProxiedPlayer> TELL = new HashMap<>();
  
  public static Bungee getInstance() {
    return instance;
  }
}
