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

/**
 * DamageReduction - Controls how much damage should be blocked in a given attack
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class DamageReduction {

  protected final String type;
  protected final float base;
  protected final float factor;

  public DamageReduction(final String type, final float base, final float factor) {

    this.type = type;
    this.base = base;
    this.factor = factor;
  }

  public String type() {

    return type;
  }

  public float base() {

    return base;
  }

  public float factor() {

    return factor;
  }
}