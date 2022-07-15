package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public abstract class ItemStorageData<T> implements SerialItemData<T> {

  private Map<Integer, SerialItem<T>> items = new HashMap<>();

  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
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
        items.put(slot, (SerialItem<T>)SerialItem.unserialize((JSONObject)value).get());
      } catch(ParseException ignore) {

      }
    });
  }

  public Map<Integer, SerialItem<T>> getItems() {
    return items;
  }

  public void setItems(Map<Integer, SerialItem<T>> items) {
    this.items = items;
  }
}