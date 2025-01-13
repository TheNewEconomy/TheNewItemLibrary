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

import java.util.Objects;

/**
 * BaseColorComponent
 *
 * @see <a href="https://minecraft.wiki/w/Data_component_format#base_color">Reference</a>
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */

public abstract class BaseColorComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected String color;

  /**
   * Constructs a new BaseColorComponent with the specified color.
   *
   * @param color The base color value for the component.
   */
  public BaseColorComponent(final String color) {

    this.color = color;
  }

  /**
   * @return the type of component this is.
   */
  @Override
  public String identifier() {
    return "base_color";
  }

  /**
   * Converts this component's data to a JSON object.
   *
   * @return The JSONObject representing this component's data.
   */
  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("color", color);
    return json;
  }

  /**
   * Reads JSON data and converts it back to this component's data.
   *
   * @param json The JSONHelper instance of the JSON data.
   * @param platform The ItemPlatform instance.
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    if(json.has("color")) {
      color = json.getString("color");
    }
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data.
   *
   * @param component The component to compare.
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if(!(component instanceof final BaseColorComponent<?, ?> other)) return false;

    return Objects.equals(this.color, other.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(color);
  }

  /**
   * @return The current base color.
   */
  public String color() {
    return color;
  }

  /**
   * Sets the base color for this component.
   *
   * @param color The color to set.
   */
  public void color(final String color) {
    this.color = color;
  }
}