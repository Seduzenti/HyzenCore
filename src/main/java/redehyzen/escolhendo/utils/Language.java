package redehyzen.escolhendo.utils;

import escolhendo.apexstore.services.plugin.config.ApexConfig;
import escolhendo.apexstore.services.plugin.config.ApexWriter;
import escolhendo.apexstore.services.plugin.config.ApexWriter.YamlEntry;
import escolhendo.apexstore.services.plugin.config.ApexWriter.YamlEntryInfo;
import escolhendo.apexstore.services.plugin.logger.ApexLogger;
import escolhendo.apexstore.services.utils.StringUtils;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings("rawtypes")
public class Language {

  public static final ApexLogger LOGGER = ((ApexLogger) Main.getInstance().getLogger())
      .getModule("LANGUAGE");
  private static final ApexConfig CONFIG = Main.getInstance().getConfig("language");

  public static boolean motd$enabled = false;
  public static String motd$text = "A Minecraft Server";

  public static String staffchat$warn$message = "§eHá uma nova mensagem no chat staff!";
  public static boolean staffchat$warn$sound = true;

  public static String vipchat$warn$message = "§eHá uma nova mensagem no chat vip!";
  public static boolean vipchat$warn$sound = true;

  public static String discord$message = "\n §eClique ";
  public static String discord$message2 = " §epara abrir o link do discord.\n ";
  public static String discord$click = "BLUE : bold>true";
  public static String discord$link = "https://redehyzen.com.br/discord";

  public static void setupLanguage() {
    boolean save = false;
    ApexWriter writer =
        Main.getInstance().getWriter(CONFIG.getFile(),
            "HyzenUtils - Maked by Escolhendo\nVersão da configuração: " + Main.getInstance()
                .getDescription().getVersion());
    for (Field field : Language.class.getDeclaredFields()) {
      if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
        String nativeName = field.getName().replace("$", ".").replace("_", "-");

        try {
          Object value;

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
            writer.set(nativeName, new YamlEntry(new Object[]{"", CONFIG.get(nativeName)}));
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
            writer.set(nativeName, new YamlEntry(new Object[]{"", value}));
          }
        } catch (ReflectiveOperationException e) {
          LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", e);
        }
      }
    }

    if (save) {
      writer.write();
      CONFIG.reload();
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
          () -> LOGGER.info("A config §6language.yml §afoi modificada ou criada."));
    }
  }
}