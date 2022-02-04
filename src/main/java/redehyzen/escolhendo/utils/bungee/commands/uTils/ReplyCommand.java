package redehyzen.escolhendo.utils.bungee.commands.uTils;

import redehyzen.escolhendo.utils.bungee.commands.Commands;
import escolhendo.apexstore.services.player.role.Role;
import escolhendo.apexstore.services.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import redehyzen.escolhendo.utils.bungee.Bungee;

import static escolhendo.apexstore.services.utils.StringUtils.formatColors;

public class ReplyCommand extends Commands {
  
  public ReplyCommand() {
    super("r");
  }
  
  public void perform(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      return;
    }
    if (args.length == 0) {
      sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /r [mensagem]"));
      return;
    }
    
    ProxiedPlayer player = (ProxiedPlayer) sender;
    ProxiedPlayer target = Bungee.TELL.get(player);
    if (target == null || !target.isConnected()) {
      player.sendMessage(TextComponent.fromLegacyText("§cJogador offline."));
      return;
    }
    String join = StringUtils.join(args, " ");
    if (player.hasPermission("hyzenutils.tell.color")) {
      join = formatColors(join);
    }
    
    Bungee.TELL.put(target, player);
    Bungee.TELL.put(player, target);
    target.sendMessage(TextComponent.fromLegacyText("§8Mensagem de: " + Role.getPlayerRole(player).getPrefix() + player.getName() + "§8: §6" + join));
    player.sendMessage(TextComponent.fromLegacyText("§8Mensagem para: " + Role.getPlayerRole(target).getPrefix() + target.getName() + "§8: §6" + join));
  }
}
