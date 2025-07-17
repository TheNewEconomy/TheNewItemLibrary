package net.tnemc.item.platform.applier;

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
 * The ItemApplicator interface represents an applicator that can be used to apply changes to an
 * item.
 *
 * @param <I> the type of AbstractItemStack that this applicator can work with
 * @param <T> the type of item that this applicator can modify
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public interface ItemApplicator<I extends AbstractItemStack<T>, T> extends Identifiable {

  /**
   * @param version the version being used when this applicator is called.
   *
   * @return true if this applicator is enabled for the version, otherwise false
   *
   * @since 0.2.0.0
   */
  boolean enabled(final String version);

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   *
   * @since 0.2.0.0
   */
  T apply(final I serialized, T item);
}