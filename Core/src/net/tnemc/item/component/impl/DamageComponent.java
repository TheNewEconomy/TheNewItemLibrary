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

import java.util.Objects;

/**
 * DamageComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#damage">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class DamageComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected int damage = 0; // The number of uses consumed

  /**
   * Represents a component that manages damage information.
   * This component stores and provides methods for handling damage values.
   */
  public DamageComponent() {

  }

  /**
   * Constructs a new DamageComponent with the specified damage amount.
   *
   * @param damage the amount of damage for the component
   */
  public DamageComponent(final int damage) {

    this.damage = damage;
  }

  @Override
  public String identifier() {
    return "damage";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("damage", damage);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    if (json.has("damage")) {
      damage = json.getInteger("damage");
    }
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if (!(component instanceof final DamageComponent<?, ?> other)) return false;

    return this.damage == other.damage;
  }

  @Override
  public int hashCode() {
    return Objects.hash(damage);
  }

  /**
   * Retrieves the current damage value.
   *
   * @return the current damage value
   */
  public int damage() {

    return damage;
  }

  /**
   * Sets the amount of damage.
   *
   * @param damage the amount of damage to set
   */
  public void damage(final int damage) {

    this.damage = damage;
  }
}