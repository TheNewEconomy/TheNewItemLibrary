package net.tnemc.item.platform.serialize;

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
 * ItemApplier
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public interface ItemSerializer<I extends AbstractItemStack<T>, T> extends Identifiable {

  /**
   * @param version the version being used when this deserializer is called.
   *
   * @return true if this deserializer is enabled for the version, otherwise false
   *
   * @since 0.2.0.0
   */
  boolean enabled(final String version);

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   *
   * @since 0.2.0.0
   */
  I serialize(final T item, I serialized);
}