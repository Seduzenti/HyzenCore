package redehyzen.escolhendo.utils.bungee.commands;

import escolhendo.apexstore.services.bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import redehyzen.escolhendo.utils.bungee.commands.uTils.*;

public abstract class Commands extends Command {
  
  public Commands(String name, String... aliases) {
    super(name, null, aliases);
    ProxyServer.getInstance().getPluginManager().registerCommand(Bungee.getInstance(), this);
  }
  
  public abstract void perform(CommandSender sender, String[] args);
  
  @Override
  public void execute(CommandSender sender, String[] args) {
    this.perform(sender, args);
  }
  
  public static void setupCommands() {
    new WarningCommand();
    new StaffChatCommand();
    new OnlineCommand();
    new TellCommand();
    new ReplyCommand();
    new WhitelistCommand();
    new ChatVipCommand();
    new DivulgarCommand();
  }
}