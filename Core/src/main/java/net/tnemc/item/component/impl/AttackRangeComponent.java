package net.tnemc.item.component.impl;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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
 * AttackRangeComponent
 * @see <a href="https://minecraft.wiki/w/Data_component_format#attack_range">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class AttackRangeComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  //The minimum distance in blocks from the attacker to the target to be considered valid. Defaults to 0.0, valid from 0.0 to 64.0.
  protected float minReach = 0.0f;
  //The maximum distance in blocks from the attacker to the target to be considered valid. Defaults to 3.0, valid from 0.0 to 64.0.
  protected float maxReach = 3.0f;
  //The minimum distance in blocks from the attacker to the target to be considered valid in Creative mode. Defaults to 0.0, valid from 0.0 to 64.0.
  protected float minCreativeReach = 0.0f;
  //The maximum distance in blocks from the attacker to the target to be considered valid in Creative mode. Defaults to 5.0, valid from 0.0 to 64.0.
  protected float maxCreativeReach = 5.0f;
  //The margin applied to the target bounding box when checking for valid hitbox collision. Defaults to 0.3, valid from 0.0 to 1.0.
  protected float hitboxMargin = 0.3f;
  //The multiplier applied to min_range and max_range when checking for valid distance if item is used by a mob. Defaults to 1.0, valid from 0.0 to 2.0.
  protected float mobFactor = 1.0f;

  public AttackRangeComponent() {

  }

  public AttackRangeComponent(final float minReach,
                              final float maxReach,
                              final float minCreativeReach,
                              final float maxCreativeReach,
                              final float hitboxMargin,
                              final float mobFactor) {
    this.minReach = minReach;
    this.maxReach = maxReach;
    this.minCreativeReach = minCreativeReach;
    this.maxCreativeReach = maxCreativeReach;
    this.hitboxMargin = hitboxMargin;
    this.mobFactor = mobFactor;
  }

  /**
   * @return the type of component this is.
   *
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {

    return "attack_range";
  }

  /**
   * Converts this component's data to a JSON object.
   *
   * @return The JSONObject representing this component's data.
   *
   * @since 0.2.0.0
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    json.put("minReach", minReach);
    json.put("maxReach", maxReach);
    json.put("minCreativeReach", minCreativeReach);
    json.put("maxCreativeReach", maxCreativeReach);
    json.put("hitboxMargin", hitboxMargin);
    json.put("mobFactor", mobFactor);

    return json;
  }

  /**
   * Reads JSON data and converts it back to this component's data.
   *
   * @param json     The JSONHelper instance of the JSON data.
   * @param platform The ItemPlatform instance.
   *
   * @since 0.2.0.0
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    minReach(json.getFloat("minReach"));
    maxReach(json.getFloat("maxReach"));
    minCreativeReach(json.getFloat("minCreativeReach"));
    maxCreativeReach(json.getFloat("maxCreativeReach"));
    hitboxMargin(json.getFloat("hitboxMargin"));
    mobFactor(json.getFloat("mobFactor"));
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data.
   *
   * @param component The component to compare.
   *
   * @return True if similar, otherwise false.
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final AttackRangeComponent<?, ?> other)) return false;
    return Objects.equals(this.minReach, other.minReach) &&
           Objects.equals(this.maxReach, other.maxReach) &&
           Objects.equals(this.minCreativeReach, other.minCreativeReach) &&
           Objects.equals(this.maxCreativeReach, other.maxCreativeReach) &&
           Objects.equals(this.hitboxMargin, other.hitboxMargin) &&
           Objects.equals(this.mobFactor, other.mobFactor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minReach, maxReach, minCreativeReach, maxCreativeReach, hitboxMargin, mobFactor);
  }

  /**
   * Retrieves the minimum reach for this attack range component.
   * The minimum reach defines the closest distance that a player can interact with
   * in a given context.
   *
   * @return A float value representing the minimum interaction distance.
   */
  public float minReach() {

    return minReach;
  }

  /**
   * Updates the minimum reach parameter for this attack range component.
   * The minimum reach defines the closest distance that a player can interact with
   * in a given context.
   *
   * @param minReach A float value representing the minimum interaction distance.
   */
  public void minReach(final float minReach) {

    this.minReach = minReach;
  }

  /**
   * Retrieves the maximum reach for this attack range component.
   * The maximum reach defines the farthest distance that a player can
   * interact with in a given context.
   *
   * @return A float value representing the maximum reach.
   */
  public float maxReach() {

    return maxReach;
  }

  /**
   * Updates the maximum reach parameter for this attack range component.
   * The maximum reach defines the farthest distance that a player can interact with in a given context.
   *
   * @param maxReach A float value representing the maximum interaction distance.
   */
  public void maxReach(final float maxReach) {

    this.maxReach = maxReach;
  }

  /**
   * Retrieves the minimum creative reach for this attack range component.
   * The minimum creative reach defines the closest distance that a player
   * can interact with while in creative mode.
   *
   * @return A float value representing the minimum creative reach.
   */
  public float minCreativeReach() {

    return minCreativeReach;
  }

  /**
   * Updates the minimum creative reach parameter for this attack range component.
   * The minimum creative reach defines the closest distance that a player
   * can interact with in creative mode.
   *
   * @param minCreativeReach A float value representing the minimum creative reach.
   */
  public void minCreativeReach(final float minCreativeReach) {

    this.minCreativeReach = minCreativeReach;
  }

  /**
   * Retrieves the maximum creative reach for this attack range component.
   * The maximum creative reach defines the farthest distance that a player
   * can interact with in creative mode.
   *
   * @return A float value representing the maximum creative reach.
   */
  public float maxCreativeReach() {

    return maxCreativeReach;
  }

  /**
   * Updates the maximum creative reach parameter for this attack range component.
   * The maximum creative reach defines the farthest distance that a player
   * can interact with in creative mode.
   *
   * @param maxCreativeReach A float value representing the maximum creative reach.
   */
  public void maxCreativeReach(final float maxCreativeReach) {

    this.maxCreativeReach = maxCreativeReach;
  }

  /**
   * Retrieves the hitbox margin value, which defines the size of the margin
   * added around the hitbox for attack range calculations.
   *
   * @return A float representing the hitbox margin.
   */
  public float hitboxMargin() {

    return hitboxMargin;
  }

  /**
   * Updates the hitbox margin parameter, which defines the margin size
   * to be applied around the hitbox for attack range calculations.
   *
   * @param hitboxMargin A float value representing the desired hitbox margin.
   */
  public void hitboxMargin(final float hitboxMargin) {

    this.hitboxMargin = hitboxMargin;
  }

  /**
   * Retrieves the mob factor value, which influences parameters related to mob behavior
   * or attack range adjustments.
   *
   * @return A float value representing the mob factor.
   */
  public float mobFactor() {

    return mobFactor;
  }

  /**
   * Sets the mob factor, which adjusts parameters related to mob behavior or attack range.
   *
   * @param mobFactor A float value representing the mob factor to be set.
   */
  public void mobFactor(final float mobFactor) {

    this.mobFactor = mobFactor;
  }
}