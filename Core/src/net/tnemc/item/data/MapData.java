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

import java.util.Objects;

public abstract class MapData<T> implements SerialItemData<T> {

  private Integer id = null;
  private String location = null;
  private int colorRGB = -1;
  private boolean scaling = false;

  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "map");
    if(id != null) json.put("id", id);
    if(location != null) json.put("location", location);
    if(colorRGB != -1) json.put("colour", colorRGB);
    json.put("scaling", scaling);
    return json;
  }

  @Override
  public void readJSON(JSONHelper json) {
    if(json.has("id")) id = json.getInteger("id");
    if(json.has("location")) location = json.getString("location");
    if(json.has("colour")) colorRGB = json.getInteger("colour");
    scaling = json.getBoolean("scaling");
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
    if(data instanceof MapData) {
      MapData<?> compare = (MapData<?>)data;
      return Objects.equals(id, compare.id) && Objects.equals(location, compare.location)
          && colorRGB == compare.colorRGB && scaling == compare.scaling;
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