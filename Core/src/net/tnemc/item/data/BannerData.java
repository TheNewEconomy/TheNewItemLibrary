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

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.banner.PatternData;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class BannerData<T> implements SerialItemData<T> {

  protected List<PatternData> patterns = new LinkedList<>();

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "banner");


    int i = 0;
    JSONObject patternsObject = new JSONObject();
    for(PatternData pattern : patterns) {
      JSONObject patternObj = new JSONObject();
      patternObj.put("colour", pattern.getColor());
      patternObj.put("pattern", pattern.getPattern());
      patternsObject.put(i, patternObj);
      i++;
    }
    json.put("patterns", patternsObject);
    return json;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(JSONHelper json) {
    json.getJSON("patterns").forEach((key, value)->{
      JSONHelper helperObj = new JSONHelper((JSONObject)value);
      final PatternData pattern = new PatternData(helperObj.getString("colour"),
                                                  helperObj.getString("pattern"));
      patterns.add(pattern);
    });
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
    if(data instanceof BannerData) {
      BannerData<?> compare = (BannerData<?>)data;
      return patterns.equals(compare.patterns);
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