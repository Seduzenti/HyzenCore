package redehyzen.escolhendo.utils.bungee;

import redehyzen.escolhendo.utils.bungee.plugin.config.HyzenConfig;
import redehyzen.escolhendo.utils.bungee.plugin.config.HyzenWriter;
import redehyzen.escolhendo.utils.bungee.plugin.logger.HyzenLogger;
import escolhendo.apexstore.services.utils.StringUtils;
import net.md_5.bungee.api.ProxyServer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings({"rawtypes"})
public class Settings {
  
  public static String maintenance$kick =
      "§c§lMANUTENÇÃO - REDE HYZEN\n \n§cNeste momento, nossa rede está\n§cem manutenção. Veja mais em\n§b§nredehyzen.com.br/discord§c.";
  
  public static List<String> blocked_commands$list =
      Arrays.asList("me", "pl");
  
  public static String blocked_commands$message =
      "§fComando desconhecido.";
  
  public static String blocked_commands$message_staff =
      "§e{player} tentou executar o comando §7(§f{command}§7)§e.";
  
  public static List<String> lobby$blacklist = Collections.singletonList("login");
  public static boolean lobby$command$enabled = false;
  
  public static final HyzenLogger LOGGER = ((HyzenLogger) Bungee.getInstance().getLogger()).getModule("LANGUAGE");
  private static final HyzenConfig CONFIG = HyzenConfig.getConfig("language");
  
  public static void setupSettings() {
    boolean save = false;
    HyzenWriter writer = new HyzenWriter(CONFIG.getFile(), "HyzenUtils - Maked by Escolhendo\nVersão da configuração: " + Bungee.getInstance().getDescription()
        .getVersion());
    for (Field field : Settings.class.getDeclaredFields()) {
      if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
        String nativeName = field.getName().replace("$", ".").replace("_", "-");
        
        try {
          Object value;
          HyzenWriter.YamlEntryInfo entryInfo = field.getAnnotation(HyzenWriter.YamlEntryInfo.class);
          
          if (CONFIG.contains(nativeName)) {
            value = CONFIG.get(nativeName);
            if (value instanceof String) {
              value = StringUtils.formatColors((String) value).replace("\\n", "\n");
            } else if (value instanceof List) {
              List l = (List) value;
              List<Object> list = new ArrayList<>(l.size());
              for (Object v : l) {
                if (v instanceof String) {
                  list.add(StringUtils.formatColors((String) v).replace("\\n", "\n"));
                } else {
                  list.add(v);
                }
              }
              
              value = list;
            }
            
            field.set(null, value);
            writer.set(nativeName, new HyzenWriter.YamlEntry(
                new Object[]{entryInfo == null ? "" : entryInfo.annotation(),
                    CONFIG.get(nativeName)}));
          } else {
            value = field.get(null);
            if (value instanceof String) {
              value = StringUtils.deformatColors((String) value).replace("\n", "\\n");
            } else if (value instanceof List) {
              List l = (List) value;
              List<Object> list = new ArrayList<>(l.size());
              for (Object v : l) {
                if (v instanceof String) {
                  list.add(StringUtils.deformatColors((String) v).replace("\n", "\\n"));
                } else {
                  list.add(v);
                }
              }
              
              value = list;
            }
            
            save = true;
            writer.set(nativeName, new HyzenWriter.YamlEntry(
                new Object[]{entryInfo == null ? "" : entryInfo.annotation(), value}));
          }
        } catch (ReflectiveOperationException e) {
          LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", e);
        }
      }
    }
    
    if (save) {
      writer.write();
      CONFIG.reload();
      ProxyServer.getInstance().getScheduler().runAsync(Bungee.getInstance(), () ->
          LOGGER.info("A config §6language.yml §afoi modificada ou criada."));
    }
  }
}