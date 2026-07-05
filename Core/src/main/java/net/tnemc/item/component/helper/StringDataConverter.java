package net.tnemc.item.component.helper;


/*
 * The New Item Library
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 * StringDataConverter
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public interface StringDataConverter {

  String get(final String key);

  default boolean asBoolean(final String key) {
    return Boolean.parseBoolean(get(key));
  }

  default byte asByte(final String key) {
    return Byte.parseByte(get(key));
  }

  default short asShort(final String key) {
    return Short.parseShort(get(key));
  }

  default int asInt(final String key) {
    return Integer.parseInt(get(key));
  }

  default long asLong(final String key) {
    return Long.parseLong(get(key));
  }

  default float asFloat(final String key) {
    return Float.parseFloat(get(key));
  }

  default double asDouble(final String key) {
    return Double.parseDouble(get(key));
  }

  default BigInteger asBigInteger(final String key) {
    return new BigInteger(get(key));
  }

  default BigDecimal asBigDecimal(final String key) {
    return new BigDecimal(get(key));
  }

  default UUID asUUID(final String key) {
    return UUID.fromString(get(key));
  }

  default char asChar(final String key) {
    final String value = get(key);

    if(value == null || value.isEmpty()) {
      throw new IllegalStateException("No value present for key '" + key + "'.");
    }

    return value.charAt(0);
  }
}