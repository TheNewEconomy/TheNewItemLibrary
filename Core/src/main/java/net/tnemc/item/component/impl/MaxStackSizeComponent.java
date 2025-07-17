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
 * MaxStackSizeComponent - The maximum number of items that can fit in a stack. Must be a positive
 * integer between 1 and 99 (inclusive). Cannot be combined with max_damage.
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#max_stack_size">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class MaxStackSizeComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected int maxStackSize;

  public MaxStackSizeComponent() {

  }

  public MaxStackSizeComponent(final int maxStackSize) {

    this.maxStackSize = maxStackSize;
  }

  @Override
  public String identifier() {

    return "max_stack_size";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("max_stack_size", maxStackSize);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    maxStackSize = json.getInteger("max_stack_size");
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final MaxStackSizeComponent<?, ?> other)) return false;
    return this.maxStackSize == other.maxStackSize;
  }

  @Override
  public int hashCode() {

    return Objects.hash(maxStackSize);
  }

  public int maxStackSize() {

    return maxStackSize;
  }

  public void maxStackSize(final int maxStackSize) {

    this.maxStackSize = maxStackSize;
  }
}