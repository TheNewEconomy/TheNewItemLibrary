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
import org.json.simple.JSONObject;

/**
 * EnchantableComponent - outline from Snapshot 24w33a
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#enchantable">Reference</a>
 * @since 0.2.0.0
 */
public abstract class EnchantableComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected int value;

  public EnchantableComponent() {

  }

  public EnchantableComponent(final int value) {

    this.value = value;
  }

  /**
   * @return the type of component this is.
   *
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {

    return "enchantable";
  }

  /**
   * Converts the {@link SerialComponent} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialComponent}.
   *
   * @since 0.2.0.0
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject enchantable = new JSONObject();
    enchantable.put("name", "enchantable-component");
    enchantable.put("value", value);

    return enchantable;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialComponent} object.
   *
   * @param json The JSONHelper instance of the json data.
   *
   * @since 0.2.0.0
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    value = json.getInteger("value");
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data. For instance, book copies will return false when compared to the original.
   *
   * @param component The component to compare.
   *
   * @return True if similar, otherwise false.
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(component instanceof final EnchantableComponent<?, ?> enchant) {

      return this.value == enchant.value;
    }
    return false;
  }

  public int value() {

    return value;
  }

  public void value(final int value) {

    this.value = value;
  }
}