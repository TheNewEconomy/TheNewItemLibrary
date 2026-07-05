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
import net.tnemc.item.component.helper.DyeColor;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * DyeComponent
 * @see <a href="https://minecraft.wiki/w/Data_component_format#dye">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class DyeComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected DyeColor color = DyeColor.WHITE;

  public DyeComponent() {

  }

  public DyeComponent(final DyeColor color) {
    this.color = color;
  }

  @Override
  public String identifier() {

    return "dye";
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isTwentySixOne(version);
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    json.put("color", color.id());

    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    if(json.has("color")) {
      color(DyeColor.fromId(json.getString("color")));
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final DyeComponent<?, ?> other)) return false;
    return Objects.equals(this.color.id(), other.color.id());
  }

  @Override
  public int hashCode() {

    return Objects.hash(color.id());
  }

  public DyeColor color() {
    return color;
  }

  public void color(final DyeColor color) {
    this.color = color;
  }
}
