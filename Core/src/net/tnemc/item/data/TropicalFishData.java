package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

public abstract class TropicalFishData<T> implements SerialItemData<T> {

  private boolean variant;
  private int bodyColour;
  private int patternColour;
  private String pattern;

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "tropicalfish");
    json.put("variant", variant);

    if(variant) {
      json.put("bodyColour", bodyColour);
      json.put("patternColour", patternColour);
      json.put("pattern", pattern);
    }
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
      this.variant = json.getBoolean("variant");

      if(variant) {
        this.bodyColour = json.getInteger("bodyColour");
        this.patternColour = json.getInteger("patternColour");
        this.pattern = json.getString("pattern");
      }
    }
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
    if(data instanceof TropicalFishData) {
      TropicalFishData<?> compare = (TropicalFishData<?>)data;
      return variant == compare.variant && bodyColour == compare.bodyColour
          && patternColour == compare.patternColour && pattern.equalsIgnoreCase(compare.pattern);
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