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
import net.tnemc.item.data.potion.PotionEffectData;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class SuspiciousStewData<T> implements SerialItemData<T> {

  protected final List<PotionEffectData> customEffects = new ArrayList<>();


  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("name", "stew");

    if(!customEffects.isEmpty()) {
      final JSONObject effects = new JSONObject();
      for(final PotionEffectData effect : customEffects) {
        final JSONObject effObject = new JSONObject();
        effObject.put("name", effect.getName());
        effObject.put("amplifier", effect.getAmplifier());
        effObject.put("duration", effect.getDuration());
        effObject.put("ambient", effect.isAmbient());
        effObject.put("particles", effect.hasParticles());
        effObject.put("icon", effect.hasIcon());
        effects.put(effect.getName(), effObject);
      }
      json.put("effects", effects);
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

    if(json.has("effects")) {
      final JSONHelper effects = json.getHelper("effects");
      effects.getObject().forEach((key, value)->{
        final JSONHelper helperObj = new JSONHelper((JSONObject)value);
        customEffects.add(new PotionEffectData(helperObj.getString("name"),
                                               helperObj.getInteger("amplifier"),
                                               helperObj.getInteger("duration"),
                                               helperObj.getBoolean("ambient"),
                                               helperObj.getBoolean("particles"),
                                               helperObj.getBoolean("icon")));
      });
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

    if(data instanceof final SuspiciousStewData<?> compare) {
      return customEffects.equals(compare.customEffects);
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
