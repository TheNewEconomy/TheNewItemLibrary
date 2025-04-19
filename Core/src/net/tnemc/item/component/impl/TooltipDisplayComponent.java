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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * TooltipDisplayComponent - Allows the tooltips provided specifically by any given item component to
 * be surpressed. As of MC 1.21.5.
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class TooltipDisplayComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<String> hiddenComponents = new ArrayList<>();

  protected boolean hideTooltip = false;

  public TooltipDisplayComponent() {

  }

  public TooltipDisplayComponent(final boolean hideTooltip) {

    this.hideTooltip = hideTooltip;
  }

  public TooltipDisplayComponent(final boolean hideTooltip, final List<String> hiddenComponents) {

    this.hideTooltip = hideTooltip;
    this.hiddenComponents.addAll(hiddenComponents);
  }

  public TooltipDisplayComponent(final boolean hideTooltip, final String... hiddenComponents) {

    this.hideTooltip = hideTooltip;
    this.hiddenComponents.addAll(Arrays.asList(hiddenComponents));
  }

  @Override
  public String identifier() {
    return "tooltip_display";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    final JSONArray componentArray = new JSONArray();

    json.put("hideTooltip", hideTooltip);

    for(final String component : hiddenComponents) {
      componentArray.add(component);
    }

    json.put("hiddenComponents", componentArray);
    return json; // Empty JSON as no additional data is needed
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    hideTooltip = json.getBoolean("hideTooltip");

    hiddenComponents.clear();

    for(final String component : json.getStringList("hiddenComponents")) {
      hiddenComponents.add(component);
    }
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if(!(component instanceof final TooltipDisplayComponent<?, ?> other)) return false;
    return Objects.equals(this.hiddenComponents, other.hiddenComponents) && Objects.equals(this.hideTooltip, other.hideTooltip);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier(), hiddenComponents, hideTooltip);
  }

  public List<String> hiddenComponents() {

    return hiddenComponents;
  }

  public void hiddenComponents(final List<String> hiddenComponents) {

    this.hiddenComponents.clear();
    this.hiddenComponents.addAll(hiddenComponents);
  }

  public void hiddenComponents(final String... hiddenComponents) {

    this.hiddenComponents.clear();
    this.hiddenComponents.addAll(Arrays.asList(hiddenComponents));
  }

  public boolean hideTooltip() {

    return hideTooltip;
  }

  public void hideTooltip(final boolean hideTooltip) {

    this.hideTooltip = hideTooltip;
  }
}