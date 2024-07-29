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

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.component.SerialComponent;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * FoodComponent
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public abstract class FoodComponent<T> implements SerialComponent<T> {

  protected final List<FoodRule> rules = new LinkedList<>();

  protected SerialItem<T> convertsTo;

  protected boolean noHunger;
  protected float eatTime;
  protected float saturation;
  protected int nutrition;

  /**
   * @return the type of component this is.
   */
  @Override
  public String getType() {
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
    food.put("eatTime", eatTime);
    food.put("saturation", saturation);
    food.put("nutrition", nutrition);
    food.put("convertsTo", convertsTo.toJSON());

    if(!rules.isEmpty()) {
      final JSONObject rulesObj = new JSONObject();
      for(int it = 0; it < rules.size(); it++) {
        rulesObj.put(it, rules.get(it).toJSON());
      }
      food.put("rules", rulesObj);
    }
    return food;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(JSONHelper json) {
    noHunger = json.getBoolean("noHunger");
    eatTime = json.getFloat("eatTime");
    saturation = json.getFloat("saturation");
    nutrition = json.getInteger("nutrition");

    if(json.has("convertsTo")) {
      try {
        final Optional<SerialItem<T>> convertOptional = SerialItem.unserialize(json.getJSON("convertsTo"));

        convertOptional.ifPresent(tSerialItem->this.convertsTo = tSerialItem);

      } catch(ParseException ignore) {
      }
    }

    if(json.has("rules")) {
      final JSONHelper rulesObj = json.getHelper("rules");
      rules.clear();

      rulesObj.getObject().forEach((key, value)->rules.add(FoodRule.readJSON(new JSONHelper((JSONObject)value))));
    }
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact copy
   * of this data. For instance, book copies will return false when compared to the original.
   *
   * @param component The component to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean equals(SerialComponent<? extends T> component) {
    if(component instanceof FoodComponent<?> food) {

      //TODO: This.
    }
    return false;
  }
}