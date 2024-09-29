package net.tnemc.item.data;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.Objects;
import java.util.UUID;

public abstract class MapData<T> implements SerialItemData<T> {

  protected String location = null;
  protected int colorRGB = -1;
  protected boolean scaling = false;

  protected UUID id;

  //TODO: MapView?

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("name", "map");
    if(location != null) json.put("location", location);
    if(colorRGB != -1) json.put("colour", colorRGB);
    json.put("scaling", scaling);
    if(id != null) json.put("id", id);
    return json;
  }

  @Override
  public <I extends AbstractItemStack<T>> void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {

    if(json.has("location")) location = json.getString("location");
    if(json.has("colour")) colorRGB = json.getInteger("colour");
    scaling = json.getBoolean("scaling");
    if(json.has("id")) id = json.getUUID("id");
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
  public boolean equals(final SerialItemData<? extends T> data) {

    if(data instanceof final MapData<?> compare) {
      return Objects.equals(location, compare.location) && colorRGB == compare.colorRGB
             && scaling == compare.scaling;
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
  public boolean similar(final SerialItemData<? extends T> data) {

    return equals(data);
  }
}