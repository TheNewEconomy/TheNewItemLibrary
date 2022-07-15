package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class EnchantStorageData<T> implements SerialItemData<T> {

  Map<String, Integer> enchantments = new HashMap<>();

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "enchantstorage");

    JSONObject object = new JSONObject();
    enchantments.forEach(object::put);
    json.put("enchantments", object);

    return json;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(JSONHelper json) {
    JSONObject enchants = json.getJSON("enchantments");
    enchants.forEach((key, value)->{
      enchantments.put(key.toString(), Integer.valueOf(value.toString()));
    });
  }
}