package net.tnemc.item;

import org.json.simple.JSONObject;

public interface SerialItemData<T> {

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
   * Used to determine if some data is equal to this data. This means that it has to be an exact copy
   * of this data. For instance, book copies will return false when compared to the original.
   *
   * @param data The data to compare.
   * @return True if similar, otherwise false.
   */
  boolean equals(SerialItemData<? extends T> data);

  /**
   * Used to determine if some data is similar to this data. This means that it doesn't have to be a
   * strict equals. For instance, book copies would return true when compared to the original, etc.
   * @param data The data to compare.
   * @return True if similar, otherwise false.
   */
  boolean similar(SerialItemData<? extends T> data);

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   * @param stack The locale itemstack object of the implementation.
   */
  void of(T stack);

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   * @param stack The locale itemstack object of the implementation.
   */
  T apply(T stack);
}