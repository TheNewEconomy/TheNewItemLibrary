package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

public abstract class MapData<T> implements SerialItemData<T> {

  private Integer id = null;
  private String location = null;
  private int colorRGB = -1;
  private boolean scaling = false;

  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "map");
    if(id != null) json.put("id", id);
    if(location != null) json.put("location", location);
    if(colorRGB != -1) json.put("colour", colorRGB);
    json.put("scaling", scaling);
    return json;
  }

  @Override
  public void readJSON(JSONHelper json) {
    if(json.has("id")) id = json.getInteger("id");
    if(json.has("location")) location = json.getString("location");
    if(json.has("colour")) colorRGB = json.getInteger("colour");
    scaling = json.getBoolean("scaling");
  }
}