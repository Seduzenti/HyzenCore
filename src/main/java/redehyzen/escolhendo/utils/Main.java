package redehyzen.escolhendo.utils;

import redehyzen.escolhendo.utils.cmd.Commands;
import redehyzen.escolhendo.utils.listeners.Listeners;
import redehyzen.escolhendo.utils.listeners.PluginMessageListener;
import redehyzen.escolhendo.utils.lobby.UpgradeNPC;
import redehyzen.escolhendo.utils.managers.Manager;
import redehyzen.escolhendo.utils.upgrade.Upgrade;
import escolhendo.apexstore.services.plugin.ApexPlugin;

public class Main extends ApexPlugin {
  
  public static Main instance;
  private boolean validInit;
  
  public static Main getInstance() {
    return instance;
  }
  
  @Override
  public void start() {
    instance = this;
  }
  
  @Override
  public void load() {}
  
  @Override
  public void enable() {
    saveDefaultConfig();
    
    Listeners.setupListeners();
    Upgrade.setupUpgrades();
    Manager.setupManager();
    Commands.setupCommands();
    
    Language.setupLanguage();
    UpgradeNPC.setupNPCs();
    
    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    getServer().getMessenger().registerOutgoingPluginChannel(this, "MESSAGE_UTILS");
    getServer().getMessenger().registerIncomingPluginChannel(this, "MESSAGE_UTILS", new PluginMessageListener());
    
    validInit = true;
    this.getLogger().info("O plugin foi ativado.");
  }
  
  @Override
  public void disable() {
    if (validInit) {
      UpgradeNPC.listNPCs().forEach(UpgradeNPC::destroy);
    }
    this.getLogger().info("O plugin foi desativado.");
  }
}
