package redehyzen.escolhendo.utils.lobby.trait;

import escolhendo.apexstore.services.libraries.npclib.api.npc.NPC;
import escolhendo.apexstore.services.libraries.npclib.npc.skin.Skin;
import escolhendo.apexstore.services.libraries.npclib.npc.skin.SkinnableEntity;
import escolhendo.apexstore.services.libraries.npclib.trait.NPCTrait;

public class NPCSkinTrait extends NPCTrait {
  
  private final Skin skin;
  
  public NPCSkinTrait(NPC npc, String value, String signature) {
    super(npc);
    this.skin = Skin.fromData(value, signature);
  }
  
  @Override
  public void onSpawn() {
    this.skin.apply((SkinnableEntity) this.getNPC().getEntity());
  }
}
