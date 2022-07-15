package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

public abstract class AxolotlData<T> implements SerialItemData<T> {

  private String variant;

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "axolotl");
    json.put("variant", variant);
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
      this.variant = json.getString("variant");
    }
  }
}