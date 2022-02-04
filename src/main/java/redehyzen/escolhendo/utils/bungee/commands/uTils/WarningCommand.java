package redehyzen.escolhendo.utils.bungee.commands.uTils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import redehyzen.escolhendo.utils.bungee.commands.Commands;
import escolhendo.apexstore.services.player.role.Role;
import escolhendo.apexstore.services.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class WarningCommand extends Commands {
  
  public WarningCommand() {
    super("aviso", "anunciar");
  }
  
  public void perform(CommandSender sender, String[] args) {
    if (sender.hasPermission("hyzenutils.cmd.warning")) {
      if (args.length == 0) {
        sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /aviso [mensagem]"));
        return;
      }
      ProxiedPlayer playerr = null;
      if (sender instanceof ProxiedPlayer) {
        playerr = (ProxiedPlayer) sender;
      }
      String join = StringUtils.join(args, " ").replace("&", "§");
      ProxiedPlayer finalPlayerr = playerr;
      ProxyServer.getInstance().getPlayers().forEach(player -> {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PLAY_SOUND");
        out.writeUTF(player.getName());
        out.writeUTF("ORB_PICKUP");
        out.writeUTF("0.5F");
        out.writeUTF("1.0F");
        player.getServer().sendData("MESSAGE_UTILS", out.toByteArray());
        
        player.sendMessage(TextComponent.fromLegacyText(""));
        player.sendMessage(TextComponent.fromLegacyText("§r" + (sender instanceof ProxiedPlayer ? Role.getPlayerRole(finalPlayerr).getPrefix() + finalPlayerr.getName() : "§6[Master] CONSOLE") + "§f: " + join));
        player.sendMessage(TextComponent.fromLegacyText(""));
      });
    } else {
      sender.sendMessage(TextComponent.fromLegacyText("§cVocê não tem permissão para isto."));
    }
  }
}
