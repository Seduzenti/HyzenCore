package redehyzen.escolhendo.utils.listeners.player;

import redehyzen.escolhendo.utils.menus.MenuUpgrades;
import escolhendo.apexstore.services.libraries.holograms.HologramLibrary;
import escolhendo.apexstore.services.libraries.holograms.api.Hologram;
import escolhendo.apexstore.services.libraries.npclib.api.event.NPCRightClickEvent;
import escolhendo.apexstore.services.libraries.npclib.api.npc.NPC;
import escolhendo.apexstore.services.player.Profile;
import escolhendo.apexstore.services.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerInteractListener implements Listener {
  
  private static final Map<Block, Hologram> hologramMap = new HashMap<>();
  
  @EventHandler
  public void onNPCRightClick(NPCRightClickEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    
    if (profile != null) {
      NPC npc = evt.getNPC();
      if (npc.data().has("utils-upgrade")) {
        new MenuUpgrades<>(profile);
      }
    }
  }
  
  @EventHandler
  public void onPluginDisableEvent(PluginDisableEvent evt) {
    if (evt.getPlugin().getName().equalsIgnoreCase("LopoUtils")) {
      hologramMap.values().forEach(Hologram::despawn);
      hologramMap.clear();
    }
  }
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onBlockPlaceEvent(BlockPlaceEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    Block block = evt.getBlockPlaced();
    if (block != null && profile != null && player.getItemInHand() != null &&
        player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName()
        && player.getItemInHand().getItemMeta().getDisplayName().contains("Discord")) {
      player.setItemInHand(null);
      Hologram hologramLibrary = HologramLibrary.createHologram(block.getLocation().clone().add(0.5, -0.5, 0.5),
          "§eClique abrir o link!", "§bDiscord");
      hologramLibrary.getLine(1).setTouchable(pp -> pp.chat("/discord"));
      hologramLibrary.getLine(2).setTouchable(pp -> pp.chat("/discord"));
      hologramLibrary.spawn();
      player.sendMessage("§aCabeça do discord setada com sucesso!");
    }
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    if (profile != null && !profile.playingGame()) {
      if (evt.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK) {
        player.setVelocity(player.getLocation().getDirection().multiply(3).setY(1));
        EnumSound.FIREWORK_LAUNCH.play(player, 1.0F, 1.0F);
      }
    }
  }
}
