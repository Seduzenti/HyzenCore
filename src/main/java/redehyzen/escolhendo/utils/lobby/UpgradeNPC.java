package redehyzen.escolhendo.utils.lobby;

import redehyzen.escolhendo.utils.Main;
import redehyzen.escolhendo.utils.lobby.trait.NPCSkinTrait;
import escolhendo.apexstore.services.libraries.holograms.HologramLibrary;
import escolhendo.apexstore.services.libraries.holograms.api.Hologram;
import escolhendo.apexstore.services.libraries.npclib.NPCLibrary;
import escolhendo.apexstore.services.libraries.npclib.api.npc.NPC;
import escolhendo.apexstore.services.plugin.config.ApexConfig;
import escolhendo.apexstore.services.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UpgradeNPC {
  
  private static final ApexConfig CONFIG = Main.getInstance().getConfig("npcs");
  private static final List<UpgradeNPC> NPCS = new ArrayList<>();
  
  private String id;
  private Location location;
  private NPC npc;
  private Hologram hologram;
  
  public UpgradeNPC(Location location, String id) {
    this.location = location;
    this.id = id;
    if (!this.location.getChunk().isLoaded()) {
      this.location.getChunk().load(true);
    }
    
    this.spawn();
  }
  
  public static void setupNPCs() {
    if (!CONFIG.contains("upgrade")) {
      CONFIG.set("upgrade", new ArrayList<>());
    }
    
    for (String serialized : CONFIG.getStringList("upgrade")) {
      if (serialized.split("; ").length > 6) {
        String id = serialized.split("; ")[6];
        
        NPCS.add(new UpgradeNPC(BukkitUtils.deserializeLocation(serialized), id));
      }
    }
    
    Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> listNPCs().forEach(UpgradeNPC::update), 20, 20);
  }
  
  public static void add(String id, Location location) {
    NPCS.add(new UpgradeNPC(location, id));
    List<String> list = CONFIG.getStringList("upgrade");
    list.add(BukkitUtils.serializeLocation(location) + "; " + id);
    CONFIG.set("upgrade", list);
  }
  
  public static void remove(UpgradeNPC npc) {
    NPCS.remove(npc);
    List<String> list = CONFIG.getStringList("upgrade");
    list.remove(BukkitUtils.serializeLocation(npc.getLocation()) + "; " + npc.getId());
    CONFIG.set("upgrade", list);
    
    npc.destroy();
  }
  
  public static UpgradeNPC getById(String id) {
    return NPCS.stream().filter(npc -> npc.getId().equals(id)).findFirst().orElse(null);
  }

  public static Collection<UpgradeNPC> listNPCs() {
    return NPCS;
  }
  
  public void spawn() {
    if (this.npc != null) {
      this.npc.destroy();
      this.npc = null;
    }
    
    if (this.hologram != null) {
      HologramLibrary.removeHologram(this.hologram);
      this.hologram = null;
    }
    
    this.hologram = HologramLibrary.createHologram(this.location.clone().add(0, 0.5, 0), Arrays.asList("§e§lCLIQUE DIREITO!", "§bUpar seu VIP!"));
    
    this.npc = NPCLibrary.createNPC(EntityType.PLAYER, "§8[NPC] ");
    this.npc.data().set("utils-upgrade", "open_menu");
    this.npc.data().set(NPC.HIDE_BY_TEAMS_KEY, true);
    this.npc.addTrait(new NPCSkinTrait(this.npc, "ewogICJ0aW1lc3RhbXAiIDogMTYxOTMyMTIyNTAzOSwKICAicHJvZmlsZUlkIiA6ICJkZTE0MGFmM2NmMjM0ZmM0OTJiZTE3M2Y2NjA3MzViYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTUlRlYW0iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTg2ZjgwNjE0YjI4MTU4MmJhM2UyM2Y2MWE4ZTY0ZjVjZDg5YWFjNmJiMjgzNmJjZTBmNjkwOGM5NTYwMDEzMiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9",
        "vdgxnIxjQa4ku5YAIXB65iT/noWWnVNez+CYyfskDtYAyr9Rirr47ymfupmMnanz5UfWsU/jR9CzHm+C8F7tF2HL12q51VFWr6n3D6WWBmp9VjDHH/0FdtoCAQJoLLkacTJg7+in1Nf1gsgAcfAOdqz19IuZccOg2pHZbxBs0wBxYwpvq1cY9nmmInPmV4alvNAiis/SbV85jMApJbYribPJrYOqW81rKR26mR/13Esw2nUYC8EXzqxW2fpyJLemtZDKCYPRzik73sxmca77Pkj8zf3lXtMoCpahRRiBZecq8ha9xhczQ/1Gldb+2F81ZGQ8w4oQxikHVFXux1T2VoihNkWjxHtYH7X5J5wp2lYUpL+vbF16h+uGDQaVpPImUtkVjU+12V/A0sAR6kLAf2w0D49Z/y2gF1zNg4QhWagyRvOHyHlmYLfOIXrdY6kAb51+EkwZYO7lNMgIpqcbCouzdQnwLjrlQVbhUmnSbS5I8jtenV1TYQdaNnAgmfs0vha0+eQhZmxiPGCIsse6qftDOcRMkrJNkDJZhta4ytn3ZitL0kAwNoc0Ce4TPNTB1prYjXzVoCyRetrpF7OQS8sGjr76FVhG4RLj989skAGxP3KCoNMfvFiG3vfBfBsX2i9BCa8YDr4x3n7Vf+gKbMVI82md8cFJ5eaGM+uPdac="));
    
    this.npc.spawn(this.location);
  }
  
  public void update() {}
  
  public void destroy() {
    this.id = null;
    this.location = null;
    
    this.npc.destroy();
    this.npc = null;
    HologramLibrary.removeHologram(this.hologram);
    this.hologram = null;
  }
  
  public String getId() {
    return id;
  }
  
  public Location getLocation() {
    return this.location;
  }
}
