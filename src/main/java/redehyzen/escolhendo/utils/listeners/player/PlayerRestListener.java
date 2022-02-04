package redehyzen.escolhendo.utils.listeners.player;

import redehyzen.escolhendo.utils.Language;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PlayerRestListener implements Listener {
  
  @EventHandler
  public void onServerListPing(ServerListPingEvent evt) {
    if (Language.motd$enabled) {
      evt.setMotd(Language.motd$text);
    }
  }
}
