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

import net.tnemc.item.component.SerialComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * RepairableComponent
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public abstract class RepairableComponent<T> implements SerialComponent<T> {

  protected final List<String> repairableTypes = new ArrayList<>();

  /**
   * @return the type of component this is.
   */
  @Override
  public String getType() {

    return "repairable";
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

    if(component instanceof final RepairableComponent<?> repair) {

      return this.repairableTypes.equals(repair.repairableTypes);
    }
    return false;
  }
}