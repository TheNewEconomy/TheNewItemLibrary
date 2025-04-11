package net.tnemc.item.platform.check;

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
import net.tnemc.item.platform.Identifiable;

/**
 * Represents a check to be performed on an item. Implementations should provide logic to determine if the check applies for a specific version,
 * as well as perform the actual check on the item stack.
 *
 * @param <T> the type of item stack being checked
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public interface ItemCheck<T> extends Identifiable {

  /**
   * @return true if the checks after this one should be skipped.
   * @since 0.2.0.0
   * @since 0.2.0.0
   */
  default boolean skipRest() {

    return false;
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   * @since 0.2.0.0
   * @since 0.2.0.0
   */
  boolean enabled(final String version);

  /**
   * Determines if a given check should be applied to an original item stack for a specific version.
   *
   * @param original the original item stack to check against
   * @param check the item stack to use for the check
   * @return true if the check applies, false otherwise
   * @since 0.2.0.0
   * @since 0.2.0.0
   */
  boolean applies(final AbstractItemStack<T> original, final AbstractItemStack<T> check);

  /**
   * @param original the original stack
   * @param check    the stack to use for the check
   *
   * @return True if the check passes, otherwise false.
   * @since 0.2.0.0
   * @since 0.2.0.0
   */
  boolean check(final AbstractItemStack<T> original, final AbstractItemStack<T> check);
}