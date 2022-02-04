package redehyzen.escolhendo.utils.bungee.manager;

import redehyzen.escolhendo.utils.bungee.plugin.config.HyzenConfig;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceManager {
  
  private static List<String> PLAYERS;
  private static List<String> SERVERS;
  
  public static final HyzenConfig CONFIG = HyzenConfig.getConfig("config");
  public static final HyzenConfig WHITELIST = HyzenConfig.getConfig("whitelisteds");
  
  public static void setupMaintenance() {
    PLAYERS = new ArrayList<>();
    SERVERS = new ArrayList<>();
    
    PLAYERS.addAll(WHITELIST.getStringList("playerNames"));
    SERVERS.addAll(CONFIG.getStringList("maintenance"));
  }
  
  public static void removePlayer(String player) {
    List<String> players = WHITELIST.getStringList("playerNames");
    players.remove(player);
    WHITELIST.set("playerNames", players);
    PLAYERS.remove(player);
  }
  
  public static void addPlayer(String player) {
    List<String> players = WHITELIST.getStringList("playerNames");
    players.add(player);
    WHITELIST.set("playerNames", players);
    PLAYERS.add(player);
  }
  
  
  public static List<String> getPlayers() {
    return PLAYERS;
  }
  
  public static List<String> getServers() {
    return SERVERS;
  }
}
