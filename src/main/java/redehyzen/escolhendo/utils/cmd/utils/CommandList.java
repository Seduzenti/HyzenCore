package redehyzen.escolhendo.utils.cmd.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import redehyzen.escolhendo.utils.cmd.Commands;

public class CommandList extends Commands {

    public CommandList() {
        super("utils");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
        } else {
            Player player = (Player) sender;
            if (!player.hasPermission("hyzenutils.cmd.commandlist")) {
                player.sendMessage("§eHyzen Utils §b1.2 - §eCriado por §6Escolhendo§6.");
            } else {
                sender.sendMessage("");
                sender.sendMessage("§7Lista de comandos da utils:");
                sender.sendMessage("");
                sender.sendMessage("§e/aviso");
                sender.sendMessage("§e/divulgar");
                sender.sendMessage("§e/chatvip");
                sender.sendMessage("§e/tell");
                sender.sendMessage("§e/r");
                sender.sendMessage("§e/whitelist");
                sender.sendMessage("§e/online");
                sender.sendMessage("§e/staffchat");
                sender.sendMessage("§e/gamemode");
                sender.sendMessage("§e/lobby");
                sender.sendMessage("§e/npcuparvip");
                sender.sendMessage("§e/ping");
                sender.sendMessage("§e/vanish");
                sender.sendMessage("§e/discord");
                sender.sendMessage("§e/clearchat");
                sender.sendMessage("§e/teleportar");



            }
        }
    }
}
