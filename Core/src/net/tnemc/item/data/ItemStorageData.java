package net.tnemc.item.data;

import net.tnemc.item.AbstractItemStack;
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
    if(data instanceof ItemStorageData) {
      ItemStorageData<?> compare = (ItemStorageData<?>)data;

      if(items.size() != compare.items.size()) return false;

      for(Map.Entry<Integer, SerialItem<T>> entry : items.entrySet()) {

        if(!compare.items.containsKey(entry.getKey())) return false;

        final SerialItem<? extends T> item = (SerialItem<? extends T>)compare.items.get(entry.getKey());
        final AbstractItemStack<? extends T> stack = item.getStack();

        if(!entry.getValue().getStack().similar(stack)) return false;
      }
      return true;
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

  public Map<Integer, SerialItem<T>> getItems() {
    return items;
  }
}