package redehyzen.escolhendo.utils.cmd.utils;

import redehyzen.escolhendo.utils.cmd.Commands;
import redehyzen.escolhendo.utils.managers.types.GameModeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand extends Commands {
  
  public GamemodeCommand() {
    super("gamemode", "gm");
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
      return;
    }
    Player player = (Player) sender;
    if (!player.hasPermission("hyzenutils.cmd.gamemode")) {
      player.sendMessage("§cVocê não possui permissão para executar este comando.");
      return;
    }
    if (args.length == 0) {
      player.sendMessage("§cUtilize /gamemode [modo] ou /gamemode [jogador] [modo]");
      return;
    }
    if (args.length == 1) {
      String gamemode = args[0];
      
      if (GameModeManager.isValidMode(gamemode)) {
        GameModeManager.setGamemode(player, gamemode);
        player.sendMessage("§aSeu modo de jogo foi alterado para " + GameModeManager.getFrom(gamemode));
      } else {
        player.sendMessage("§cUtilize /gamemode [modo]");
      }
    } else {
      Player target = Bukkit.getPlayer(args[0]);
      String gamemode = args[1];
      
      if (target == null || !target.isOnline()) {
        player.sendMessage("§cJogador não encontrado.");
        return;
      }
      
      if (GameModeManager.isValidMode(gamemode)) {
        GameModeManager.setGamemode(target, gamemode);
        target.sendMessage("§aSeu modo de jogo foi alterado para " + GameModeManager.getFrom(gamemode));
        player.sendMessage("§aO modo de jogo de " + target.getName() + " §afoi alterado para " + GameModeManager.getFrom(gamemode));
      } else {
        player.sendMessage("§cUtilize /gamemode [jogador] [modo]");
      }
    }
  }
}
