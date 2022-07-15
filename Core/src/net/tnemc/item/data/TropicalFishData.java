package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

public abstract class TropicalFishData<T> implements SerialItemData<T> {

  private boolean variant;
  private int bodyColour;
  private int patternColour;
  private String pattern;

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "tropicalfish");
    json.put("variant", variant);

    if(variant) {
      json.put("bodyColour", bodyColour);
      json.put("patternColour", patternColour);
      json.put("pattern", pattern);
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
    if(json.has("variant")) {
      this.variant = json.getBoolean("variant");

      if(variant) {
        this.bodyColour = json.getInteger("bodyColour");
        this.patternColour = json.getInteger("patternColour");
        this.pattern = json.getString("pattern");
      }
    }
  }
}