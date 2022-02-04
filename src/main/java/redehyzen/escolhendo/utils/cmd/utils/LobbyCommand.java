package redehyzen.escolhendo.utils.cmd.utils;

import redehyzen.escolhendo.utils.cmd.Commands;
import escolhendo.apexstore.services.Services;
import escolhendo.apexstore.services.game.FakeGame;
import escolhendo.apexstore.services.game.Game;
import escolhendo.apexstore.services.game.GameTeam;
import escolhendo.apexstore.services.player.Profile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand extends Commands {
  
  public LobbyCommand() {
    super("lobby");
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cApenas jogadores podem executar este comando.");
      return;
    }
    Player player = (Player) sender;
    Profile profile = Profile.getProfile(player.getName());
    Game<? extends GameTeam> game = profile.getGame();
    if (game != null && !(game instanceof FakeGame)) {
      player.sendMessage("§aConectando...");
      game.leave(profile, null);
      return;
    }
    Services.sendServer(profile, "lobby");
  }
}
