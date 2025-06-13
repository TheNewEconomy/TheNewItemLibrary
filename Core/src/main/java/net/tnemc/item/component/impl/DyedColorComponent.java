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
 * DyedColorComponent -The color applied of this leather armor piece. Color codes are the hex code of
 * the color converted to a decimal number, or can be calculated from the Red, Green and Blue components
 * using this formula: R << 16 + G << 8 + B
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#dyed_color">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class DyedColorComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected int rgb;

  public DyedColorComponent() {

  }

  public DyedColorComponent(final int rgb) {

    this.rgb = rgb;
  }

  public DyedColorComponent(final int red, final int green, final int blue) {

    this.rgb = (red << 16) + (green << 8) + blue;
  }

  @Override
  public String identifier() {
    return "dyed_color";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("rgb", rgb);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    rgb = json.getInteger("rgb");
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {
    if(!(component instanceof final DyedColorComponent<?, ?> other)) return false;

    return this.rgb == other.rgb;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rgb);
  }

  public int rgb() {

    return rgb;
  }

  public void rgb(final int rgb) {

    this.rgb = rgb;
  }

  public void rgb(final int red, final int green, final int blue) {

    this.rgb = (red << 16) + (green << 8) + blue;
  }
}