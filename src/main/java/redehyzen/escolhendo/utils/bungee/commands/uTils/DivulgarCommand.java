package redehyzen.escolhendo.utils.bungee.commands.uTils;

import escolhendo.apexstore.services.player.role.Role;
import escolhendo.apexstore.services.utils.StringUtils;
import escolhendo.apexstore.services.utils.TimeUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import redehyzen.escolhendo.utils.bungee.commands.Commands;

import java.util.HashMap;

public class DivulgarCommand extends Commands {

    public static HashMap<ProxiedPlayer, Long> delay = new HashMap();

    public DivulgarCommand() {
        super("divulgar");
    }

    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("§cApenas jogadores podem utilizar este comando."));
        } else {
            ProxiedPlayer player = (ProxiedPlayer)sender;
            if (!player.hasPermission("hyzenutils.cmd.divulgar")) {
                player.sendMessage(new TextComponent("§cVocê não possui permissão para utilizar este comando!"));
            } else if (delay.containsKey(player) && delay.get(player) >= System.currentTimeMillis()) {
                String time = TimeUtils.getTime(delay.get(player) - System.currentTimeMillis());
                player.sendMessage(new TextComponent("§cVocê deve aguardar " + time + " para divulgar novamente!"));
            } else if (args.length == 0) {
                player.sendMessage(new TextComponent("§cUtilize /divulgar [mensagem]"));
            } else {
                delay.put(player, System.currentTimeMillis() + 600000L);

                for (ProxiedPlayer players : BungeeCord.getInstance().getPlayers()) {
                    players.sendMessage(new TextComponent(" \n §aDivulgação de " + Role.getColored(player.getName(), false) + "§f: " + StringUtils.join(args, 0, " ").replace("&", "§") + "\n "));
                }

            }
        }
    }
}
