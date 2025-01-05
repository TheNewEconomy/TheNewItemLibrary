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
import net.tnemc.item.SerialItemData;
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
public abstract class DamageComponent<T> implements SerialComponent<T> {

  protected int damage = 0; // The number of uses consumed

  @Override
  public String getType() {
    return "damage";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("damage", damage);
    return json;
  }

  @Override
  public <I extends AbstractItemStack<T>> void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    if (json.has("damage")) {
      damage = json.getInteger("damage");
    }
  }

  @Override
  public boolean equals(final SerialComponent<? extends T> component) {
    if (!(component instanceof final DamageComponent<?> other)) return false;

    return this.damage == other.damage;
  }

  @Override
  public int hashCode() {
    return Objects.hash(damage);
  }

  /**
   * @return The number of uses consumed.
   */
  public int getDamage() {
    return damage;
  }

  /**
   * Sets the number of uses consumed.
   *
   * @param damage The amount of damage to set.
   */
  public void setDamage(final int damage) {
    this.damage = damage;
  }
}