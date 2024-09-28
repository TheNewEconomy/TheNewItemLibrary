package net.tnemc.item;

/*
 * The New Item Library Minecraft Server Plugin
 *
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

import org.json.simple.JSONObject;

import java.util.UUID;

public class JSONHelper {

  private JSONObject object;

  public JSONHelper(final JSONObject object) {

    this.object = object;
  }

  public boolean has(final String identifier) {

    return object.containsKey(identifier);
  }

  public boolean isNull(final String identifier) {

    return object.get(identifier) == null;
  }

  public JSONHelper getHelper(final String identifier) {

    return new JSONHelper(getJSON(identifier));
  }

  public JSONObject getJSON(final String identifier) {

    return (JSONObject)object.get(identifier);
  }

  public Short getShort(final String identifier) {

    return Short.valueOf(getString(identifier));
  }

  public Float getFloat(final String identifier) {

    return Float.valueOf(getString(identifier));
  }

  public Double getDouble(final String identifier) {

    return Double.valueOf(getString(identifier));
  }

  public Integer getInteger(final String identifier) {

    return Integer.valueOf(getString(identifier));
  }

  public Boolean getBoolean(final String identifier) {

    return Boolean.valueOf(getString(identifier));
  }

  public String getString(final String identifier) {

    return object.get(identifier).toString();
  }

  public UUID getUUID(final String identifier) {

    return UUID.fromString(object.get(identifier).toString());
  }

  public JSONObject getObject() {

    return object;
  }

  public void setObject(final JSONObject object) {

    this.object = object;
  }
}