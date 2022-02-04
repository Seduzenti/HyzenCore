package redehyzen.escolhendo.utils.bungee.commands.uTils;

import redehyzen.escolhendo.utils.bungee.commands.Commands;
import escolhendo.apexstore.services.database.Database;
import escolhendo.apexstore.services.player.role.Role;
import escolhendo.apexstore.services.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import redehyzen.escolhendo.utils.bungee.Bungee;

import static escolhendo.apexstore.services.utils.StringUtils.formatColors;

public class TellCommand extends Commands {
  
  public TellCommand() { super("tell"); }
  
  public void perform(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      return;
    }
    if (args.length < 2) {
      sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /tell [jogador] [mensagem]"));
      return;
    }
    
    ProxiedPlayer player = (ProxiedPlayer) sender;
    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
    if (target == player) {
      player.sendMessage(TextComponent.fromLegacyText("§cVocê não pode mandar mensagens privadas para si mesmo."));
      return;
    }
    if (target == null || !target.isConnected()) {
      player.sendMessage(TextComponent.fromLegacyText("§cJogador offline."));
      return;
    }
    
    boolean canReceiveTell = Database.getInstance().getPreference(player.getName(), "pm", true);
    boolean canReceiveTellT = Database.getInstance().getPreference(target.getName(), "pm", true);
    if (!canReceiveTell) {
      player.sendMessage(TextComponent.fromLegacyText("§cVocê não pode enviar mensagens privadas com as mensagens privadas desativadas."));
      return;
    }
    if (!canReceiveTellT) {
      player.sendMessage(TextComponent.fromLegacyText("§cEste usuário desativou as mensagens privadas."));
      return;
    }
    String join = StringUtils.join(args, 1, " ");
    if (player.hasPermission("hyzenutils.tell.color")) {
      join = formatColors(join);
    }
    
    Bungee.TELL.put(target, player);
    Bungee.TELL.put(player, target);
    target.sendMessage(TextComponent.fromLegacyText("§8Mensagem de: " + Role.getPlayerRole(player).getPrefix() + player.getName() + "§8: §6" + join));
    player.sendMessage(TextComponent.fromLegacyText("§8Mensagem para: " + Role.getPlayerRole(target).getPrefix() + target.getName() + "§8: §6" + join));
  }
}
