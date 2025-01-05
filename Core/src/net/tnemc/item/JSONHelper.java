package net.tnemc.item;

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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JSONHelper {

  private JSONObject object;

  public JSONHelper(final JSONObject object) {

    this.object = object;
  }

  public boolean has(final String identifier) {

    return object.containsKey(identifier);
  }

  public boolean isNull(final String identifier) {

    return object.get(identifier) == null;
  }

  public JSONHelper getHelper(final String identifier) {

    return new JSONHelper(getJSON(identifier));
  }

  public JSONObject getJSON(final String identifier) {

    return (JSONObject)object.get(identifier);
  }

  public Short getShort(final String identifier) {

    return Short.valueOf(getString(identifier));
  }

  public Float getFloat(final String identifier) {

    return Float.valueOf(getString(identifier));
  }

  public Double getDouble(final String identifier) {

    return Double.valueOf(getString(identifier));
  }

  public Integer getInteger(final String identifier) {

    return Integer.valueOf(getString(identifier));
  }

  public Boolean getBoolean(final String identifier) {

    return Boolean.valueOf(getString(identifier));
  }

  public String getString(final String identifier) {

    return object.get(identifier).toString();
  }

  public UUID getUUID(final String identifier) {

    return UUID.fromString(object.get(identifier).toString());
  }

  /**
   * Parses a JSON array into a List<String>.
   *
   * @param identifier The key of the JSON array.
   * @return A List<String> containing the elements of the JSON array.
   */
  public List<String> getStringList(final String identifier) {
    final List<String> result = new ArrayList<>();
    if(has(identifier)) {

      final JSONArray array = (JSONArray) object.get(identifier);
      for(final Object obj : array) {

        result.add(obj.toString());
      }
    }
    return result;
  }

  /**
   * Parses a JSON array into a List<Integer>.
   *
   * @param identifier The key of the JSON array.
   * @return A List<Integer> containing the elements of the JSON array.
   */
  public List<Integer> getIntegerList(final String identifier) {
    final List<Integer> result = new ArrayList<>();
    if(has(identifier)) {

      final JSONArray array = (JSONArray) object.get(identifier);
      for(final Object obj : array) {

        result.add(Integer.parseInt(obj.toString()));
      }
    }
    return result;
  }

  /**
   * Parses a JSON array into a List<Float>.
   *
   * @param identifier The key of the JSON array.
   * @return A List<Float> containing the elements of the JSON array.
   */
  public List<Float> getFloatList(final String identifier) {
    final List<Float> result = new ArrayList<>();
    if(has(identifier)) {

      final JSONArray array = (JSONArray) object.get(identifier);
      for(final Object obj : array) {

        result.add(Float.parseFloat(obj.toString()));
      }
    }
    return result;
  }

  /**
   * Parses a JSON array into a List<Boolean>.
   *
   * @param identifier The key of the JSON array.
   * @return A List<Boolean> containing the elements of the JSON array.
   */
  public List<Boolean> getBooleanList(final String identifier) {
    final List<Boolean> result = new ArrayList<>();
    if(has(identifier)) {

      final JSONArray array = (JSONArray) object.get(identifier);
      for(final Object obj : array) {

        result.add(Boolean.parseBoolean(obj.toString()));
      }
    }
    return result;
  }

  public JSONObject getObject() {

    return object;
  }

  public void setObject(final JSONObject object) {

    this.object = object;
  }
}