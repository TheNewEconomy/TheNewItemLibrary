package net.tnemc.item.component.impl;
/*
 * The New Item Library
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
import net.tnemc.item.SerialItemData;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

/**
 * FoodComponent
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public abstract class FoodComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected boolean noHunger;
  protected float saturation;
  protected int nutrition;

  /**
   * @return the type of component this is.
   */
  @Override
  public String identifier() {

    return "food";
  }

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject food = new JSONObject();
    food.put("name", "food-component");
    food.put("noHunger", noHunger);
    food.put("saturation", saturation);
    food.put("nutrition", nutrition);
    return food;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {

    noHunger = json.getBoolean("noHunger");
    saturation = json.getFloat("saturation");
    nutrition = json.getInteger("nutrition");
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data. For instance, book copies will return false when compared to the original.
   *
   * @param component The component to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean equals(final SerialComponent<I, T> component) {

    if(component instanceof final FoodComponent<?, ?> food) {

      return this.noHunger == food.noHunger &&
             Float.compare(this.saturation, food.saturation) == 0 &&
             this.nutrition == food.nutrition;
    }
    return false;
  }
}