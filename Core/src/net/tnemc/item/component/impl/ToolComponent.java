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
import net.tnemc.item.SerialItemData;
import net.tnemc.item.component.SerialComponent;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * ToolComponent
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public abstract class ToolComponent<T> implements SerialComponent<T> {

  protected final List<ToolRule> rules = new LinkedList<>();

  protected float defaultSpeed;
  protected int blockDamage;

  /**
   * @return the type of component this is.
   */
  @Override
  public String getType() {

    return "tool";
  }

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject tool = new JSONObject();
    tool.put("name", "tool-component");
    tool.put("defaultSpeed", defaultSpeed);
    tool.put("blockDamage", blockDamage);

    if(!rules.isEmpty()) {
      final JSONObject rulesObj = new JSONObject();
      for(int it = 0; it < rules.size(); it++) {
        rulesObj.put(it, rules.get(it).toJSON());
      }
      tool.put("rules", rulesObj);
    }
    return tool;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(final JSONHelper json) {

    defaultSpeed = json.getFloat("defaultSpeed");
    blockDamage = json.getInteger("blockDamage");

    if(json.has("rules")) {
      final JSONHelper rulesObj = json.getHelper("rules");
      rules.clear();

      rulesObj.getObject().forEach((key, value)->rules.add(ToolRule.readJSON(new JSONHelper((JSONObject)value))));
    }
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
  public boolean equals(final SerialComponent<? extends T> component) {

    if(component instanceof ToolComponent<?> tool) {

      //TODO: This.
    }
    return false;
  }
}