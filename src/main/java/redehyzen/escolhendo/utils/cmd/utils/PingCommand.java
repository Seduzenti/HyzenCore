package redehyzen.escolhendo.utils.cmd.utils;

import redehyzen.escolhendo.utils.cmd.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand extends Commands {
  
  public PingCommand() {
    super("ping");
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    sender.sendMessage("§aSeu ping é de " + (sender instanceof Player ? ((CraftPlayer) sender).getHandle().ping : 0) + " §ams!");
  }
}
