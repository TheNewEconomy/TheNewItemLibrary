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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * ContainerComponent - The items contained in this container.
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class ContainerComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final Map<Integer, AbstractItemStack<T>> items = new HashMap<>();

  public ContainerComponent() {

  }

  public ContainerComponent(final Map<Integer, AbstractItemStack<T>> items) {

    this.items.putAll(items);
  }

  @Override
  public String identifier() {
    return "container";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject itemsObj = new JSONObject();

    items.forEach((slot, item)->{
      itemsObj.put(slot, item.toJSON());
    });
    return itemsObj;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {

    items.clear();
    json.getJSON("items").forEach((key, value)->{
      final int slot = Integer.parseInt(String.valueOf(key));

      final Optional<I> serialized = platform.initSerialized((JSONObject)value);
      serialized.ifPresent(i->items.put(slot, i));
    });
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if(component instanceof final ContainerComponent<?, ?> compare) {

      if(items.size() != compare.items.size()) return false;

      for(final Map.Entry<Integer, AbstractItemStack<T>> entry : items.entrySet()) {

        if(!compare.items.containsKey(entry.getKey())) return false;

        final AbstractItemStack<? extends T> item = (AbstractItemStack<? extends T>)compare.items.get(entry.getKey());

        if(entry.getValue().amount() != item.amount()) return false;

        if(!entry.getValue().similar(item)) return false;
      }
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(items);
  }

  public Map<Integer, AbstractItemStack<T>> items() {

    return items;
  }
}