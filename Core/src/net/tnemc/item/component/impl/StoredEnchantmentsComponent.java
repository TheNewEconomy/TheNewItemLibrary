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
import net.tnemc.item.component.TooltippableSerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * StoredEnchantmentsComponents
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class StoredEnchantmentsComponent<I extends AbstractItemStack<T>, T> extends TooltippableSerialComponent<I, T> {

  protected final Map<String, Integer> enchantments = new HashMap<>();

  @Override
  public String identifier() {
    return "stored_enchantments";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    final JSONObject levels = new JSONObject();
    for (final Map.Entry<String, Integer> entry : enchantments.entrySet()) {
      levels.put(entry.getKey(), entry.getValue());
    }
    json.put("levels", levels);
    json.put("show_in_tooltip", showInTooltip);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    super.readJSON(json, platform);
    enchantments.clear();

    final JSONObject levels = json.getJSON("levels");
    for (final Object key : levels.keySet()) {
      final String enchantmentId = key.toString();
      final int level = Integer.parseInt(levels.get(key).toString());
      enchantments.put(enchantmentId, level);
    }
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if (!(component instanceof final StoredEnchantmentsComponent<?, ?> other)) return false;
    return showInTooltip == other.showInTooltip && Objects.equals(this.enchantments, other.enchantments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), enchantments);
  }
}