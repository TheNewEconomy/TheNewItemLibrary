package net.tnemc.item.component;

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
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.platform.applier.ItemApplicator;
import net.tnemc.item.platform.check.ItemCheck;
import net.tnemc.item.platform.serialize.ItemSerializer;
import org.json.simple.JSONObject;

/**
 * SerialComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */

public interface SerialComponent<I extends AbstractItemStack<T>, T> extends ItemCheck<T>, ItemApplicator<I, T>, ItemSerializer<I, T> {


  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   * @return True if this component applies to the item, false otherwise.
   */
  boolean appliesTo(T item);

  /**
   * Converts the {@link SerialComponent} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialComponent}.
   */
  JSONObject toJSON();

  /**
   * Reads JSON data and converts it back to a {@link SerialComponent} object.
   *
   * @param json     The JSONHelper instance of the json data.
   * @param platform The {@link ItemPlatform platform} instance.
   */
  void readJSON(JSONHelper json, ItemPlatform<I, T> platform);

  /**
   * Used to determine if some data is equal to this component. This means that it has to be an
   * exact copy of this component.
   *
   * @param component The component to compare.
   *
   * @return True if similar, otherwise false.
   */
  boolean equals(SerialComponent<I, T> component);

  /**
   * @param original the original stack
   * @param check    the stack to use for the check
   *
   * @return True if the check passes, otherwise false.
   */
  @Override
  default boolean check(final AbstractItemStack<T> original, final AbstractItemStack<T> check) {
    if(original.components().containsKey(identifier()) && check.components().containsKey(identifier())) {

      return original.components().get(identifier()).equals(check.components().get(identifier()));
    }

    return !original.components().containsKey(identifier()) && !check.components().containsKey(identifier());
  }
}