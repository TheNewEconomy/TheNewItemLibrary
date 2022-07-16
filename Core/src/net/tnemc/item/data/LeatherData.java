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
    if(data instanceof LeatherData) {
      LeatherData<?> compare = (LeatherData<?>)data;
      return colorRGB == compare.colorRGB;
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
