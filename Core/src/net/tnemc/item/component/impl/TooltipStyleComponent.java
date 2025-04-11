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
 * TooltipStyleComponent -The resource location of the custom sprites for the tooltip background and
 * frame which references textures
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#tooltip_style">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class TooltipStyleComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected String style = "";

  public TooltipStyleComponent() {

  }

  public TooltipStyleComponent(final String style) {

    this.style = style;
  }

  @Override
  public String identifier() {
    return "tooltip_style";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("style", style);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    style = json.getString("style");
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if (!(component instanceof final TooltipStyleComponent<?, ?> other)) return false;
    return Objects.equals(this.style, other.style);
  }

  @Override
  public int hashCode() {
    return Objects.hash(style);
  }

  public String style() {

    return style;
  }

  public void style(final String style) {

    this.style = style;
  }
}