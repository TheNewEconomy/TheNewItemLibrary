package net.tnemc.item.component.impl;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
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
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * EnchantmentsComponent - Can contain either the following fields, or key-value pairs of levels of
 * enchantments.
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#enchantments">Reference</a>
 * @since 0.2.0.0
 */
public abstract class EnchantmentsComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final Map<String, Integer> levels = new HashMap<>();

  public EnchantmentsComponent() {

  }

  public EnchantmentsComponent(final Map<String, Integer> levels) {

    this.levels.putAll(levels);
  }

  @Override
  public String identifier() {

    return "enchantments";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    final JSONArray enchantmentsArray = new JSONArray();
    for(final Map.Entry<String, Integer> entry : levels.entrySet()) {

      final JSONObject enchantmentJson = new JSONObject();
      enchantmentJson.put("id", entry.getKey());
      enchantmentJson.put("level", entry.getValue());
      enchantmentsArray.add(enchantmentJson);
    }
    json.put("enchantments", enchantmentsArray);

    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    levels.clear();

    final JSONArray enchantmentsArray = (JSONArray)json.getObject().get("enchantments");
    if(enchantmentsArray != null) {
      for(final Object obj : enchantmentsArray) {
        final JSONObject enchantmentJson = (JSONObject)obj;

        final String id = enchantmentJson.get("id").toString();
        final int level = Integer.parseInt(enchantmentJson.get("level").toString());
        levels.put(id, level);
      }
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final EnchantmentsComponent<?, ?> other)) return false;

    return Objects.equals(this.levels, other.levels);
  }

  @Override
  public int hashCode() {

    return Objects.hash(levels);
  }

  public Map<String, Integer> levels() {

    return levels;
  }

  public void levels(final Map<String, Integer> levels) {

    this.levels.clear();
    this.levels.putAll(levels);
  }
}