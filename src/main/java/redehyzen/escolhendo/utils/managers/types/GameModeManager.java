package redehyzen.escolhendo.utils.managers.types;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GameModeManager {
  
  public static void setGamemode(Player player, String type) {
    if (type.equals("0")) {
      type = "SURVIVAL";
    }
    if (type.equals("1")) {
      type = "CREATIVE";
    }
    if (type.equals("2")) {
      type = "ADVENTURE";
    }
    if (type.equals("3")) {
      type = "SPECTATOR";
    }
    player.setGameMode(GameMode.valueOf(type.toUpperCase()));
  }
  
  public static boolean isValidMode(String type) {
    try {
      if (Integer.parseInt(type) == 0 || Integer.parseInt(type) == 3 || Integer.parseInt(type) == 1 || Integer.parseInt(type) == 2) {
        return true;
      }
    } catch (NumberFormatException ex) {
      return type.equalsIgnoreCase("spectator") || type.equalsIgnoreCase("survival") || type.equalsIgnoreCase("creative") || type.equalsIgnoreCase("adventure");
    }
    return false;
  }
  
  public static String getFrom(String type) {
    try {
      if (Integer.parseInt(type) == 0) {
        return "Sobrevivência";
      }
      if (Integer.parseInt(type) == 1) {
        return "Criativo";
      }
      if (Integer.parseInt(type) == 2) {
        return "Aventura";
      }
      if (Integer.parseInt(type) == 3) {
        return "Espectador";
      }
      
    } catch (NumberFormatException ex) {
      if (type.equalsIgnoreCase("Survival")) {
        return "Sobrevivência";
      }
      if (type.equalsIgnoreCase("Creative")) {
        return "Criativo";
      }
      if (type.equalsIgnoreCase("Adventure")) {
        return "Aventura";
      }
      if (type.equalsIgnoreCase("Spectator")) {
        return "Espectador";
      }
      return "Aventura";
    }
    return "Sobrevivência";
  }
  
}
