package net.tnemc.item.data;

/*
 * The New Economy Minecraft Server Plugin
 *
 * Copyright (C) 2022 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

import java.util.UUID;

public abstract class CompassData<T> implements SerialItemData<T> {

  protected boolean tracked = false;
  protected UUID world;
  protected double x;
  protected double y;
  protected double z;

  protected float yaw;
  protected float pitch;

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
    if(data instanceof CompassData) {
      CompassData<?> compare = (CompassData<?>)data;
      return tracked == compare.tracked && x == compare.x && y == compare.y && z == compare.z
          && world.toString().equalsIgnoreCase(compare.world.toString()) && yaw == compare.yaw
          && pitch == compare.pitch;
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