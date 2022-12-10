package net.tnemc.item.bukkit.data;

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
import net.tnemc.item.bukkit.data.firework.SerialFireworkEffect;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class FireworkData<T> implements SerialItemData<T> {

  protected List<SerialFireworkEffect> effects = new ArrayList<>();

  protected int power;

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {
    JSONObject firework = new JSONObject();
    firework.put("name", "firework");
    firework.put("power", power);

    if(effects.size() > 0) {
      JSONObject effectsObj = new JSONObject();
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
  public void readJSON(JSONHelper json) {
    power = json.getInteger("power");
    if(json.has("effects")) {
      JSONHelper effectsObj = json.getHelper("effects");

      effects = new ArrayList<>();
      effectsObj.getObject().forEach((key, value)->effects.add(SerialFireworkEffect.readJSON(new JSONHelper((JSONObject)value))));
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
    if(data instanceof FireworkData) {
      FireworkData<?> compare = (FireworkData<?>)data;
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
  public boolean similar(SerialItemData<? extends T> data) {
    return equals(data);
  }
}