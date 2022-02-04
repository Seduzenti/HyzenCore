package redehyzen.escolhendo.utils.cmd.utils;

import redehyzen.escolhendo.utils.Language;
import redehyzen.escolhendo.utils.cmd.Commands;
import escolhendo.apexstore.services.utils.BukkitUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class DiscordCommand extends Commands {
  
  public DiscordCommand() {
    super("discord", "dc");
  }
  
  public ChatColor getColor() {
    return ChatColor.valueOf(Language.discord$click.split(" : ")[0]);
  }
  
  public boolean getBold() {
    return Boolean.parseBoolean(Language.discord$click.split(" : ")[1].replace("bold>", ""));
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(Language.discord$message + "AQUI" + Language.discord$message2);
      return;
    }
    Player player = (Player) sender;
    
    if (!(args.length == 0)) {
      String action = args[0];
      if (!player.hasPermission("hyzenutils.cmd.discord")) {
        player.chat("/discord");
        return;
      }
      if (action.equalsIgnoreCase("give")) {
        player.getInventory().addItem(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&9Discord : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19"));
        player.sendMessage("§aCabeça adicionada ao seu inventário.");
        return;
      } else if (action.equalsIgnoreCase("ajuda")) {
        player.sendMessage("");
        player.sendMessage("§6/discord give §f- §7Pegar cabeças do discord.");
        player.sendMessage("");
        return;
      }
    }
    
    TextComponent component = new TextComponent("");
    Arrays.stream(TextComponent
        .fromLegacyText(Language.discord$message)).forEach(component::addExtra);
    TextComponent click = new TextComponent("AQUI");
    click.setColor(this.getColor());
    click.setBold(this.getBold());
    click.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Language.discord$link));
    click.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Clique aqui para abrir o link de convite.")));
    component.addExtra(click);
    for (BaseComponent components : TextComponent.fromLegacyText(Language.discord$message2)) {
      component.addExtra(components);
    }
    
    player.spigot().sendMessage(component);
  }
}
