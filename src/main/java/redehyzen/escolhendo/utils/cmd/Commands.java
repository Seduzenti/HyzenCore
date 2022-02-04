package redehyzen.escolhendo.utils.cmd;

import escolhendo.apexstore.services.Services;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import redehyzen.escolhendo.utils.cmd.utils.*;

import java.util.Arrays;
import java.util.logging.Level;

public abstract class Commands extends Command {
  
  public Commands(String name, String... aliases) {
    super(name);
    this.setAliases(Arrays.asList(aliases));
    
    try {
      SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
      simpleCommandMap.register(this.getName(), "hyzenutils", this);
    } catch (ReflectiveOperationException ex) {
      Services.getInstance().getLogger().log(Level.SEVERE, "Cannot register command: ", ex);
    }
  }
  
  public static void setupCommands() {
    new GamemodeCommand();
    new ClearChatCommand();
    new VanishCommand();
    new NPCUpgradeCommand();
    new LobbyCommand();
    new DiscordCommand();
    new PingCommand();
    new CommandList();
    new TeleportCommand();
  }
  
  public abstract void perform(CommandSender sender, String label, String[] args);
  
  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    this.perform(sender, commandLabel, args);
    return true;
  }
}
