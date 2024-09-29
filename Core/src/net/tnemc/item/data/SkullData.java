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
import net.tnemc.item.providers.SkullProfile;
import org.json.simple.JSONObject;

import java.util.UUID;

public abstract class SkullData<T> implements SerialItemData<T> {

  protected SkullProfile profile;

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("name", "skull");
    if(profile != null) {
      if(profile.getName() != null) json.put("name", profile.getName());
      if(profile.getUuid() != null) json.put("uuid", profile.getUuid());
      if(profile.getTexture() != null) json.put("texture", profile.getTexture());
    }
    return json;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public <I extends AbstractItemStack<T>> void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {

    profile = new SkullProfile();
    if(json.has("name")) profile.setName(json.getString("name"));

    try {

      if(json.has("uuid")) profile.setUuid(UUID.fromString(json.getString("uuid")));

    } catch(final Exception ignore) { }

    if(json.has("texture")) profile.setTexture(json.getString("texture"));
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

    if(data instanceof final SkullData<?> compare) {

      if(profile == null && compare.profile == null) return true;
      if(profile == null || compare.profile == null) return false;

      return profile.equals(compare.profile);
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

  public SkullProfile getProfile() {

    return profile;
  }
}