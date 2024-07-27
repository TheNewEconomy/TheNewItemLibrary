package net.tnemc.item.data;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public abstract class ItemStorageData<T> implements SerialItemData<T> {

  protected final Map<Integer, SerialItem<T>> items = new HashMap<>();

  @Override
  public JSONObject toJSON() {
    final JSONObject itemsObj = new JSONObject();
    items.forEach((slot, item)->{
      itemsObj.put(slot, item.toJSON());
    });
    return itemsObj;
  }

  @Override
  public void readJSON(JSONHelper json) {
    json.getJSON("items").forEach((key, value)->{
      final int slot = Integer.valueOf(String.valueOf(key));
      try {
        items.put(slot, (SerialItem<T>)SerialItem.unserialize((JSONObject)value).get());
      } catch(ParseException ignore) {

      }
    });
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact copy
   * of this data. For instance, book copies will return false when compared to the original.
   *
   * @param data The data to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean equals(SerialItemData<? extends T> data) {
    if(data instanceof ItemStorageData<?> compare) {

      if(items.size() != compare.items.size()) return false;

      for(Map.Entry<Integer, SerialItem<T>> entry : items.entrySet()) {

        if(!compare.items.containsKey(entry.getKey())) return false;

        final SerialItem<? extends T> item = (SerialItem<? extends T>)compare.items.get(entry.getKey());
        final AbstractItemStack<? extends T> stack = item.getStack();

        if(entry.getValue().getStack().amount() != stack.amount()) return false;

        if(!entry.getValue().getStack().similar(stack)) return false;
      }
      return true;
    }
    return false;
  }

  /**
   * Used to determine if some data is similar to this data. This means that it doesn't have to be a
   * strict equals. For instance, book copies would return true when compared to the original, etc.
   *
   * @param data The data to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean similar(SerialItemData<? extends T> data) {
    return equals(data);
  }

  public Map<Integer, SerialItem<T>> getItems() {
    return items;
  }
}