package redehyzen.escolhendo.utils.cmd.utils;

import redehyzen.escolhendo.utils.cmd.Commands;
import redehyzen.escolhendo.utils.managers.Manager;
import escolhendo.apexstore.services.player.Profile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand extends Commands {
  
  public VanishCommand() {
    super("vanish", "v");
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
      return;
    }
    Player player = (Player) sender;
    if (!player.hasPermission("hyzenutils.cmd.vanish")) {
      player.sendMessage("§cVocê não possui permissão para executar este comando.");
      return;
    }
    Profile profile = Profile.getProfile(player.getName());
    
    if (Manager.isVanished(player)) {
      Manager.removeVanish(player);
      
      if (profile.playingGame()) {
        Profile.listProfiles().stream().filter(Profile::playingGame).forEach(pf -> {
          if (pf.getGame().equals(profile.getGame()) && !profile.getGame().isSpectator(player)) {
            pf.getPlayer().showPlayer(player);
          } else if (pf.getGame().equals(profile.getGame()) && profile.getGame().isSpectator(player)) {
            if (pf.getGame().isSpectator(pf.getPlayer())) {
              pf.getPlayer().showPlayer(player);
            }
          }
        });
      } else {
        Profile.listProfiles().stream().filter(pf -> !pf.playingGame()).forEach(pf -> pf.getPlayer().showPlayer(player));
      }
      player.sendMessage("§cModo invisível desativado.");
    } else if (!Manager.isVanished(player)) {
      Manager.addVanish(player);
      player.sendMessage("§aModo invisível ativado.");
    }
  }
}
