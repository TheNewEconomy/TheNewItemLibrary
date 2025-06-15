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
import net.tnemc.item.component.helper.ToolRule;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * ToolComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class ToolComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<ToolRule> rules = new LinkedList<>();

  protected float defaultSpeed = 1.0f;
  protected int blockDamage;
  protected boolean canDestroyBlocksCreative = true; //since MC Snapshot 25w02a

  public ToolComponent(final int blockDamage) {

    this.blockDamage = blockDamage;
  }

  public ToolComponent(final float defaultSpeed, final int blockDamage) {

    this.defaultSpeed = defaultSpeed;
    this.blockDamage = blockDamage;
  }

  public ToolComponent(final boolean canDestroyBlocksCreative, final int blockDamage) {

    this.canDestroyBlocksCreative = canDestroyBlocksCreative;
    this.blockDamage = blockDamage;
  }

  public ToolComponent(final float defaultSpeed, final int blockDamage, final boolean canDestroyBlocksCreative) {

    this.defaultSpeed = defaultSpeed;
    this.blockDamage = blockDamage;
    this.canDestroyBlocksCreative = canDestroyBlocksCreative;
  }

  public ToolComponent(final float defaultSpeed, final int blockDamage, final boolean canDestroyBlocksCreative,
                       final ToolRule rule) {

    this.defaultSpeed = defaultSpeed;
    this.blockDamage = blockDamage;
    this.canDestroyBlocksCreative = canDestroyBlocksCreative;

    this.rules.add(rule);
  }

  public ToolComponent(final float defaultSpeed, final int blockDamage, final boolean canDestroyBlocksCreative,
                       final List<ToolRule> rules) {

    this.defaultSpeed = defaultSpeed;
    this.blockDamage = blockDamage;
    this.canDestroyBlocksCreative = canDestroyBlocksCreative;

    this.rules.addAll(rules);
  }

  /**
   * @return the type of component this is.
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {

    return "tool";
  }

  /**
   * Converts the {@link SerialComponent} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialComponent}.
   * @since 0.2.0.0
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
   * Reads JSON data and converts it back to a {@link SerialComponent} object.
   *
   * @param json The JSONHelper instance of the json data.
   * @since 0.2.0.0
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

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
   * @since 0.2.0.0
   */
  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(component instanceof final ToolComponent<?, ?> tool) {
      return Float.compare(this.defaultSpeed, tool.defaultSpeed) == 0 &&
             this.blockDamage == tool.blockDamage &&
             this.canDestroyBlocksCreative == tool.canDestroyBlocksCreative &&
             Objects.equals(this.rules, tool.rules);
    }
    return false;
  }

  public List<ToolRule> rules() {

    return rules;
  }

  public void rules(final List<ToolRule> rules) {
    this.rules.clear();
    this.rules.addAll(rules);
  }

  public void rules(final ToolRule... rules) {
    this.rules.clear();
    this.rules.addAll(Arrays.asList(rules));
  }

  public float defaultSpeed() {

    return defaultSpeed;
  }

  public void defaultSpeed(final float defaultSpeed) {

    this.defaultSpeed = defaultSpeed;
  }

  public int blockDamage() {

    return blockDamage;
  }

  public void blockDamage(final int blockDamage) {

    this.blockDamage = blockDamage;
  }

  public boolean canDestroyBlocksCreative() {

    return canDestroyBlocksCreative;
  }

  public void canDestroyBlocksCreative(final boolean canDestroyBlocksCreative) {

    this.canDestroyBlocksCreative = canDestroyBlocksCreative;
  }
}