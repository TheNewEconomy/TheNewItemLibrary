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
import net.tnemc.item.data.firework.SerialFireworkEffect;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class FireworkData<T> implements SerialItemData<T> {

  protected final List<SerialFireworkEffect> effects = new ArrayList<>();

  protected long power;

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject firework = new JSONObject();
    firework.put("name", "firework");
    firework.put("power", power);

    if(effects.size() > 0) {
      final JSONObject effectsObj = new JSONObject();
      for(int it = 0; it < effects.size(); it++) {
        effectsObj.put(it, effects.get(it).toJSON());
      }
      firework.put("effects", effectsObj);
    }
    return firework;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public <I extends AbstractItemStack<T>> void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {

    power = json.getInteger("power");
    if(json.has("effects")) {
      final JSONHelper effectsObj = json.getHelper("effects");

      effects.clear();
      effectsObj.getObject().forEach((key, value)->effects.add(SerialFireworkEffect.readJSON(new JSONHelper((JSONObject)value))));
    }
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

    if(data instanceof final FireworkData<?> compare) {
      return effects.equals(compare.effects) && power == compare.power;
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