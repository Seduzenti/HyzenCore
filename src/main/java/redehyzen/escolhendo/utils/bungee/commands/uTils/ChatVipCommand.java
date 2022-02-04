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

public class ChatVipCommand extends Commands {

    public ChatVipCommand() {
        super("cv", "chatvip");
    }

    public void perform(CommandSender sender, String[] args) {
        if (sender.hasPermission("hyzenutils.cmd.chatvip")) {
            if (args.length == 0) {
                sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /cv [mensagem]"));
                return;
            }

            ProxiedPlayer playerr = null;
            if (sender instanceof ProxiedPlayer) {
                playerr = (ProxiedPlayer) sender;
            }

            String joined = StringUtils.join(args, " ");

            ProxiedPlayer finalPlayerr = playerr;
            String server = finalPlayerr == null ? "Lobby" : StringUtils.capitalise(finalPlayerr.getServer().getInfo().getName().toUpperCase());

            ProxyServer.getInstance().getPlayers().stream().filter(player -> player.hasPermission("hyzenutils.cmd.chatvip")).forEach(player -> {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("VIP_SOUND");
                out.writeUTF(player.getName());
                player.getServer().sendData("MESSAGE_UTILS", out.toByteArray());

                player.sendMessage(TextComponent.fromLegacyText("§6§l[Chat Vip] " + "§7[" + server + "] " + (sender instanceof ProxiedPlayer ? Role.getPlayerRole(finalPlayerr).getPrefix() + finalPlayerr.getName() : "§4[Supremo] CONSOLE")
                        + "" + "§f: " + StringUtils.formatColors(joined)));
            });
        } else {
            sender.sendMessage(TextComponent.fromLegacyText("§cVocê não tem permissão para isto."));
        }
    }
}
