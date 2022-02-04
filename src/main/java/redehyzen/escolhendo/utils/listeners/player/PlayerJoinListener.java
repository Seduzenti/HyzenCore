package redehyzen.escolhendo.utils.listeners.player;

import escolhendo.apexstore.services.player.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static redehyzen.escolhendo.utils.upgrade.Upgrade.QUEUE_VIPS;

public class PlayerJoinListener implements Listener {
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    if (QUEUE_VIPS.containsKey(player.getName())) {
      QUEUE_VIPS.get(player.getName()).dispatchPack(player);
    }
  }
  
}
