package redehyzen.escolhendo.utils.bungee.commands.uTils;

import redehyzen.escolhendo.utils.bungee.commands.Commands;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import org.bukkit.entity.Player;

public class OnlineCommand extends Commands {
  
  public OnlineCommand() {
    super("online");
  }
  
  @Override
  public void perform(CommandSender sender, String[] args) {
    sender.sendMessage("§eTotal de jogadores online: §7" + BungeeCord.getInstance().getPlayers().size());
  }
}