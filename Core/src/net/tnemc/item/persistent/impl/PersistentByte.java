package net.tnemc.item.persistent.impl;
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

import net.tnemc.item.persistent.PersistentDataType;

/**
 * PersistentByte
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PersistentByte extends PersistentDataType<Byte> {

  public PersistentByte(final String namespace, final String key) {

    super(namespace, key);
  }

  /**
   * Returns the type of the PersistentDataType.
   *
   * @return The type of the PersistentDataType as a String.
   * @since 0.2.0.0
   */
  @Override
  public String type() {

    return "byte";
  }

  /**
   * Encodes the value of the PersistentDataType into a string representation.
   *
   * @return The encoded string representation of the value
   * @since 0.2.0.0
   */
  @Override
  public String encode() {

    return value.toString();
  }

  /**
   * Decodes the given encoded string and sets the decoded value.
   *
   * @param encoded The string to be decoded
   * @since 0.2.0.0
   */
  @Override
  public Byte decode(final String encoded) throws IllegalArgumentException {

    return Byte.parseByte(encoded);
  }
}