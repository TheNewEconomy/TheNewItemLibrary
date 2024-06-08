package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import org.json.simple.JSONObject;

public abstract class CrossBowMeta<T> extends ItemStorageData<T> {

  @Override
  public JSONObject toJSON() {
    JSONObject json = super.toJSON();
    json.put("name", "crossbow");

    return json;
  }

  @Override
  public void readJSON(JSONHelper json) {
    super.readJSON(json);
  }
}