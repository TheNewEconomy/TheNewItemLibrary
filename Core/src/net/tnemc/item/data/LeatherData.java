package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

public abstract class LeatherData<T> implements SerialItemData<T> {

  private int colorRGB;

  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "leather");
    json.put("colour", colorRGB);
    return json;
  }

  @Override
  public void readJSON(JSONHelper json) {
    if(json.has("color")) colorRGB = json.getInteger("colour");
  }
}
