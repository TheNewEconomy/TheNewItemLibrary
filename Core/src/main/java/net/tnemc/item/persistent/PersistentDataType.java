package net.tnemc.item.persistent;
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

import net.tnemc.item.JSONHelper;
import org.json.simple.JSONObject;

/**
 * PersistentDataType
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class PersistentDataType<T> {

  protected String namespace;
  protected String key;

  protected T value;

  public PersistentDataType(final String namespace, final String key) {

    this.namespace = namespace;
    this.key = key;
  }

  public String identifier() {

    return namespace + ":" + key;
  }

  /**
   * Returns the type of the PersistentDataType.
   *
   * @return The type of the PersistentDataType as a String.
   *
   * @since 0.2.0.0
   */
  public abstract String type();

  /**
   * Encodes the value of the PersistentDataType into a string representation.
   *
   * @return The encoded string representation of the value
   *
   * @since 0.2.0.0
   */
  public abstract String encode();

  /**
   * Decodes the given encoded string and sets the decoded value.
   *
   * @param encoded The string to be decoded
   *
   * @return The decoded value of type T
   *
   * @throws IllegalArgumentException if the encoded string is invalid
   * @since 0.2.0.0
   */
  public abstract T decode(final String encoded) throws IllegalArgumentException;

  /**
   * Decodes the {@link JSONHelper JSON object} and sets the values in the current instance.
   *
   * @param json The {@link JSONHelper JSON object} to be decoded
   *
   * @since 0.2.0.0
   */
  public void readJSON(final JSONHelper json) {

    this.value = decode(json.getString("value"));
  }

  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("namespace", this.namespace);
    json.put("key", this.key);
    json.put("type", type());
    json.put("value", encode());
    return json;
  }
}