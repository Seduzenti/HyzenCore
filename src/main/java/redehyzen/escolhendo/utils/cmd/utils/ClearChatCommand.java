package redehyzen.escolhendo.utils.cmd.utils;

import redehyzen.escolhendo.utils.cmd.Commands;
import escolhendo.apexstore.services.player.role.Role;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.IntStream;

public class ClearChatCommand extends Commands {
  
  public ClearChatCommand() {
    super("cc", "clearchat");
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cApenas jogadores podem executar este comando.");
      return;
    }
    Player player = (Player) sender;
    if (!player.hasPermission("hyzenutils.cmd.clearchat")) {
      player.sendMessage("§cVocê não tem permissão para executar este comando.");
      return;
    }
  
    IntStream.range(0, 200).forEach(i -> Bukkit.getOnlinePlayers().forEach(consumer -> consumer.sendMessage("")));
    
    TextComponent component = new TextComponent("");
    Arrays.stream(TextComponent.fromLegacyText("§aO chat foi limpo.")).forEach(component::addExtra);
    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
        TextComponent.fromLegacyText("§fAutor: §r" + Role.getPrefixed(player.getName()) + "\n§fData: §7" + new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR")).format(System.currentTimeMillis()))));
    Bukkit.getOnlinePlayers().forEach(consumer -> consumer.spigot().sendMessage(component));
  }
}
