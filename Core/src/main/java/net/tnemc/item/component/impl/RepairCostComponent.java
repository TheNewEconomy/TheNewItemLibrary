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
 * RepairCostComponent - The number of experience levels to add to the base level cost when
 * repairing, combining, or renaming this item with an anvil. Must be a non-negative integer,
 * defaults to 0.
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#repair_cost">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class RepairCostComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected int repairCost = 0;

  public RepairCostComponent() {

  }

  public RepairCostComponent(final int repairCost) {

    this.repairCost = repairCost;
  }

  @Override
  public String identifier() {

    return "repair_cost";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("repair_cost", repairCost);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    repairCost = json.getInteger("repair_cost");
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final RepairCostComponent<?, ?> other)) return false;
    return this.repairCost == other.repairCost;
  }

  @Override
  public int hashCode() {

    return Objects.hash(repairCost);
  }

  public int repairCost() {

    return repairCost;
  }

  public void repairCost(final int repairCost) {

    this.repairCost = repairCost;
  }
}