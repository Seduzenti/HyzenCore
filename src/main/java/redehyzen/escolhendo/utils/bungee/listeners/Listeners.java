package redehyzen.escolhendo.utils.bungee.listeners;

import redehyzen.escolhendo.utils.bungee.Settings;
import redehyzen.escolhendo.utils.bungee.manager.MaintenanceManager;
import redehyzen.escolhendo.utils.bungee.plugin.config.HyzenConfig;
import escolhendo.apexstore.services.utils.StringUtils;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.PlayerInfo;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;
import java.util.stream.Collectors;

import static escolhendo.apexstore.services.utils.StringUtils.formatColors;

public class Listeners implements Listener {
  
  public static final HyzenConfig config = HyzenConfig.getConfig("motd");
  
  @EventHandler
  public void onServerConnect(ServerConnectEvent evt) {
    if (!MaintenanceManager.getServers().contains(evt.getTarget().getName())) {
      return;
    }
    ProxiedPlayer player = evt.getPlayer();
    if (!MaintenanceManager.getPlayers().contains(player.getName())) {
      player.sendMessage(TextComponent.fromLegacyText("§cExpulso enquanto se conectava a " + evt.getTarget().getName() + ": "  + Settings.maintenance$kick));
      evt.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onServerConnected(ServerConnectedEvent evt) {
    if (!MaintenanceManager.getServers().contains("ALL")) {
      return;
    }
    ProxiedPlayer player = evt.getPlayer();
    if (!MaintenanceManager.getPlayers().contains(player.getName())) {
      player.disconnect(TextComponent.fromLegacyText(Settings.maintenance$kick));
    }
  }
  
  @EventHandler
  public void onProxyPing(ProxyPingEvent evt) {
    // DEPRECATED
    ServerPing ping = evt.getResponse();
    ServerPing.Players pingPlayers = ping.getPlayers();
    ServerPing.Protocol pingVersion = ping.getVersion();
    
    if (config.getBoolean("version_change_enabled")) {
      pingPlayers.setSample(new PlayerInfo[] {new PlayerInfo(formatColors(config.getString("version_name")), UUID.randomUUID())});
      pingVersion.setName(formatColors(config.getString("version")));
      evt.getResponse().setVersion(new ServerPing.Protocol(formatColors(config.getString("version")), 2));
    }
    ping.setDescription(formatColors(config.getString("motd").replace("\\n", "\n")));
    
    evt.setResponse(ping);
  }
  
  @EventHandler(priority = (byte) 128)
  public void onChat(ChatEvent evt) {
    if (!(evt.getSender() instanceof ProxiedPlayer)) {
      return;
    }
    if (evt.isCommand()) {
      ProxiedPlayer player = (UserConnection) evt.getSender();
      String[] args = evt.getMessage().replace("/", "").split(" ");
      
      String command = args[0];
      if (Settings.blocked_commands$list.stream().map(String::toLowerCase).collect(Collectors.toList()).contains(command.toLowerCase())) {
        player.sendMessage(TextComponent.fromLegacyText(Settings.blocked_commands$message.replace("{command}", command)));
        ProxyServer.getInstance().getPlayers().stream().filter(pp -> pp.hasPermission("hyzenutils.exe.notification"))
            .forEach(pp -> pp.sendMessage(TextComponent.fromLegacyText(StringUtils.formatColors(Settings.blocked_commands$message_staff
                .replace("{player}", player.getName()).replace("{command}", "/" + command)))));
        evt.setCancelled(true);
      }
    }
  }
}