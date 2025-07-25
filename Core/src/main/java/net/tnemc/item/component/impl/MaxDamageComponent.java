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
 * MaxDamageComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#max_damage">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class MaxDamageComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected int maxDamage;

  public MaxDamageComponent() {

  }

  public MaxDamageComponent(final int maxDamage) {

    this.maxDamage = maxDamage;
  }

  @Override
  public String identifier() {

    return "max_damage";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("max_damage", maxDamage);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    maxDamage = json.getInteger("max_damage");
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final MaxDamageComponent<?, ?> other)) return false;
    return this.maxDamage == other.maxDamage;
  }

  @Override
  public int hashCode() {

    return Objects.hash(maxDamage);
  }

  public int maxDamage() {

    return maxDamage;
  }

  public void maxDamage(final int maxDamage) {

    this.maxDamage = maxDamage;
  }
}