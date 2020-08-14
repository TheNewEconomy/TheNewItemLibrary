package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public abstract class ItemStorageData implements SerialItemData {

  private Map<Integer, SerialItem> items = new HashMap<>();

  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "shulker");
    JSONObject itemsObj = new JSONObject();
    items.forEach((slot, item)->{
      itemsObj.put(slot, item.toJSON());
    });
    json.put("items", itemsObj);
    return json;
  }

  @Override
  public void readJSON(JSONHelper json) {
    json.getJSON("items").forEach((key, value)->{
      final int slot = Integer.valueOf(String.valueOf(key));
      try {
        items.put(slot, SerialItem.unserialize((JSONObject)value).get());
      } catch(ParseException ignore) {

      }
    });
  }
}