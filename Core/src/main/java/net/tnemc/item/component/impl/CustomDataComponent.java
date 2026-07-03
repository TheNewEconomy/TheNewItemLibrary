package net.tnemc.item.component.impl;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.component.helper.StringDataConverter;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * CustomDataComponent
 * @see <a href="https://minecraft.wiki/w/Data_component_format#custom_data">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class CustomDataComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T>, StringDataConverter {

  protected final Map<String, String> data = new HashMap<>();

  public CustomDataComponent() {

  }

  public CustomDataComponent(final Map<String, String> data) {

    this.data.putAll(data);
  }

  @Override
  public String identifier() {

    return "custom_data";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    final JSONArray dataArray = new JSONArray();
    for(final Map.Entry<String, String> entry : data.entrySet()) {

      final JSONObject dataJson = new JSONObject();
      dataJson.put("key", entry.getKey());
      dataJson.put("value", entry.getValue());

      dataArray.add(dataJson);
    }
    json.put("data", dataArray);

    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    data.clear();

    final JSONArray dataArray = (JSONArray)json.getObject().get("data");
    if(dataArray != null) {

      for(final Object obj : dataArray) {

        final JSONObject dataJson = (JSONObject)obj;

        final String key = dataJson.get("key").toString();
        final String value = dataJson.get("value").toString();

        data.put(key, value);
      }
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final CustomDataComponent<?, ?> other)) return false;
    return Objects.equals(this.data, other.data);
  }

  @Override
  public int hashCode() {

    return Objects.hash(data);
  }

  public Map<String, String> data() {

    return Collections.unmodifiableMap(data);
  }

  public void data(final Map<String, String> data) {

    this.data.clear();
    this.data.putAll(data);
  }

  public void put(final String key, final String value) {

    data.put(key, value);
  }

  @Override
  public String get(final String key) {

    return data.get(key);
  }

  public String getOrDefault(final String key, final String def) {

    return data.getOrDefault(key, def);
  }

  public String remove(final String key) {

    return data.remove(key);
  }

  public boolean contains(final String key) {

    return data.containsKey(key);
  }

  public void clear() {

    data.clear();
  }
}
