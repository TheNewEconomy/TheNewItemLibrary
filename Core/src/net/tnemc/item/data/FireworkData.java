package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.firework.FireworkEffectData;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class FireworkData implements SerialItemData {

  private List<FireworkEffectData> effects = new ArrayList<>();

  private int power;

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {
    JSONObject firework = new JSONObject();
    firework.put("name", "firework");
    firework.put("power", power);

    if(effects.size() > 0) {
      JSONObject effectsObj = new JSONObject();
      for(int it = 0; it < effects.size(); it++) {
        effectsObj.put(it, effects.get(it).toJSON());
      }
      firework.put("effects", effectsObj);
    }
    return firework;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(JSONHelper json) {
    power = json.getInteger("power");
    if(json.has("effects")) {
      JSONHelper effectsObj = json.getHelper("effects");

      effects = new ArrayList<>();
      effectsObj.getObject().forEach((key, value)->effects.add(FireworkEffectData.readJSON(new JSONHelper((JSONObject)value))));
    }
  }
}