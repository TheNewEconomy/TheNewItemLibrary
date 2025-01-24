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

import java.util.Objects;

/**
 * TrimComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#trim">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class TrimComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected String pattern = "";
  protected String material = "";

  public TrimComponent() {

  }

  public TrimComponent(final String pattern, final String material) {

    this.pattern = pattern;
    this.material = material;
  }

  @Override
  public String identifier() {
    return "trim";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("pattern", pattern);
    json.put("material", material);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    pattern = json.getString("pattern");
    material = json.getString("material");
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if (!(component instanceof final TrimComponent<?, ?> other)) return false;
    return Objects.equals(this.pattern, other.pattern) &&
           Objects.equals(this.material, other.material);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), pattern, material);
  }

  public String pattern() {

    return pattern;
  }

  public void pattern(final String pattern) {

    this.pattern = pattern;
  }

  public String material() {

    return material;
  }

  public void material(final String material) {

    this.material = material;
  }
}