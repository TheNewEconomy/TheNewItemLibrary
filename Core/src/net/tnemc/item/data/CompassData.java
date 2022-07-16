package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

import java.util.UUID;

public abstract class CompassData<T> implements SerialItemData<T> {

  private boolean tracked = false;
  private UUID world;
  private double x;
  private double y;
  private double z;

  private float yaw;
  private float pitch;

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "compass");
    json.put("tracked", tracked);
    json.put("world", world.toString());
    json.put("x", x);
    json.put("y", y);
    json.put("z", z);
    json.put("yaw", yaw);
    json.put("pitch", pitch);
    return json;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(JSONHelper json) {
    if(json.has("tracked")) {
      this.tracked = json.getBoolean("tracked");

      this.world = json.getUUID("world");
      this.x = json.getInteger("x");
      this.y = json.getInteger("y");
      this.z = json.getInteger("z");
      this.yaw = json.getFloat("yaw");
      this.pitch = json.getFloat("pitch");
    }
  }
}