package net.tnemc.item.sponge.data.abs;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

public abstract class DyeData<T> implements SerialItemData<T> {

  protected String dye = "";

  @Override
  public JSONObject toJSON() {

    JSONObject json = new JSONObject();
    json.put("name", "dye");
    json.put("dye", dye);
    return json;
  }

  @Override
  public void readJSON(JSONHelper json) {

    if(json.has("dye")) dye = json.getString("dye");
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data. For instance, book copies will return false when compared to the original.
   *
   * @param data The data to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean equals(SerialItemData<? extends T> data) {

    if(data instanceof DyeData<? extends T>) {
      DyeData<?> compare = (DyeData<?>)data;
      return dye.equals(compare.dye);
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