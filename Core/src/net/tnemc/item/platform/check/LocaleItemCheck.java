package net.tnemc.item.platform.check;
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

import net.tnemc.item.AbstractItemStack;

/**
 * LocaleItemCheck
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public interface LocaleItemCheck<T> extends ItemCheck<T> {

  /**
   * Checks if the given LocaleItemCheck applies based on the original item and the item to check.
   *
   * @param original the original item
   * @param check    the item to check
   *
   * @return true if the LocaleItemCheck applies, false otherwise
   */
  boolean applies(final T original, final T check);

  /**
   * @param original the original stack
   * @param check    the stack to use for the check
   *
   * @return True if the check passes, otherwise false.
   */
  boolean check(final T original, final T check);

  /**
   * @param original the original stack
   * @param check    the stack to use for the check
   *
   * @return True if the check passes, otherwise false.
   */
  @Override
  default boolean check(final AbstractItemStack<T> original, final AbstractItemStack<T> check) {

    return true;//always return true because this shouldn't be used for locale checks.
  }
}