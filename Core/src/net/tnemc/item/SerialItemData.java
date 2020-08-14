package net.tnemc.item;

import org.json.simple.JSONObject;

public interface SerialItemData {

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  JSONObject toJSON();

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   * @param json The JSONHelper instance of the json data.
   */
  void readJSON(JSONHelper json);


  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   * @param localeItemStack The locale itemstack object of the implementation.
   */
  void of(Object localeItemStack);

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   * @param localeItemStack The locale itemstack object of the implementation.
   */
  Object apply(Object localeItemStack);
}