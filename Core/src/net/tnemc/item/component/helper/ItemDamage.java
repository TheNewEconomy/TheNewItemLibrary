package net.tnemc.item.component.helper;
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

import java.util.ArrayList;

/**
 * ItemDamage - Controls how much damage should be applied to the item from a given attack
 * If not specified, a point of durability is removed for every point of damage dealt
 * The final damage applied to the item is determined by: floor(base + factor * dealt_damage)
 * The final value may be negative, causing the item to be repaired
 * @author creatorfromhell
 * @since 0.2.0.0
 * @see net.tnemc.item.component.impl.BlocksAttacksComponent
 */
public class ItemDamage {

  protected final float threshold;
  protected final float base;
  protected final float factor;

  public ItemDamage(final float threshold, final float base, final float factor) {

    this.threshold = threshold;
    this.base = base;
    this.factor = factor;
  }

  public float threshold() {

    return threshold;
  }

  public float base() {

    return base;
  }

  public float factor() {

    return factor;
  }

  @Override
  public ItemDamage clone() throws CloneNotSupportedException {
    return new ItemDamage(this.threshold, this.base, this.factor);
  }
}