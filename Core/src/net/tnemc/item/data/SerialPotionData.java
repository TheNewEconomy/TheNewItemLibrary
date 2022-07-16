package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.potion.PotionEffectData;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class SerialPotionData<T> implements SerialItemData<T> {

  private List<PotionEffectData> customEffects = new ArrayList<>();
  private String type;
  private int colorRGB = -1;
  private boolean extended;
  private boolean upgraded;

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "potion");
    json.put("type", type);
    if(colorRGB != -1) json.put("colour", colorRGB);
    json.put("extended", extended);
    json.put("upgraded", upgraded);

    if(customEffects.size() > 0) {
      JSONObject effects = new JSONObject();
      for(PotionEffectData effect : customEffects) {
        JSONObject effObject = new JSONObject();
        effObject.put("name", effect.getName());
        effObject.put("amplifier", effect.getAmplifier());
        effObject.put("duration", effect.getDuration());
        effObject.put("ambient", effect.isAmbient());
        effObject.put("particles", effect.hasParticles());
        effObject.put("icon", effect.hasIcon());
        effects.put(effect.getName(), effObject);
      }
      json.put("effects", effects);
    }
    return json;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(JSONHelper json) {
    type = json.getString("type");

    if(json.has("colour")) colorRGB = json.getInteger("colour");
    extended = json.getBoolean("extended");
    upgraded = json.getBoolean("upgraded");

    if(json.has("effects")) {
      JSONHelper effects = json.getHelper("effects");
      effects.getObject().forEach((key, value)->{
        final JSONHelper helperObj = new JSONHelper((JSONObject)value);
        customEffects.add(new PotionEffectData(helperObj.getString("name"),
                                           helperObj.getInteger("amplifier"),
                                               helperObj.getInteger("duration"),
                                           helperObj.getBoolean("ambient"),
                                               helperObj.getBoolean("particles"),
                                           helperObj.getBoolean("icon")));
      });
    }
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact copy
   * of this data. For instance, book copies will return false when compared to the original.
   *
   * @param data The data to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean equals(SerialItemData<? extends T> data) {
    if(data instanceof SerialPotionData) {
      SerialPotionData<?> compare = (SerialPotionData<?>)data;
      return customEffects.equals(compare.customEffects) &&  type.equalsIgnoreCase(compare.type)
          && colorRGB == compare.colorRGB && extended == compare.extended
          && upgraded == compare.upgraded;
    }
    return false;
  }

  /**
   * Used to determine if some data is similar to this data. This means that it doesn't have to be a
   * strict equals. For instance, book copies would return true when compared to the original, etc.
   *
   * @param data The data to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean similar(SerialItemData<? extends T> data) {
    return equals(data);
  }
}