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
 * RepairableComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#repairable">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class RepairableComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<String> repairItems = new ArrayList<>();

  public RepairableComponent() {

  }

  public RepairableComponent(final String repairItem) {

    this.repairItems.add(repairItem);
  }

  public RepairableComponent(final List<String> repairItems) {

    this.repairItems.addAll(repairItems);
  }

  @Override
  public String identifier() {
    return "repairable";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    final JSONArray itemsArray = new JSONArray();
    itemsArray.addAll(repairItems);
    json.put("items", itemsArray);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    repairItems.clear();
    repairItems.addAll(json.getStringList("items"));
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if (!(component instanceof final RepairableComponent<?, ?> other)) return false;
    return Objects.equals(this.repairItems, other.repairItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(repairItems);
  }

  public List<String> repairItems() {

    return repairItems;
  }

  public void repairItem(final String repairItem) {

    this.repairItems.add(repairItem);
  }

  public void repairItem(final List<String> repairItems) {

    this.repairItems.addAll(repairItems);
  }

  public void repairItem(final String... repairItems) {

    this.repairItems.addAll(Arrays.asList(repairItems));
  }
}