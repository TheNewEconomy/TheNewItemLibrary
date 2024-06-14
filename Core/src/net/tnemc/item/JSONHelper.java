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

/**
 * TheNewItemLibrary
 * <p>
 * Created by Daniel on 12/1/2018.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
public class JSONHelper {

  private JSONObject object;

  public JSONHelper(JSONObject object) {
    this.object = object;
  }

  public boolean has(String identifier) {
    return object.containsKey(identifier);
  }

  public boolean isNull(String identifier) {
    return object.get(identifier) == null;
  }

  public JSONHelper getHelper(String identifier) {
    return new JSONHelper(getJSON(identifier));
  }

  public JSONObject getJSON(String identifier) {
    return (JSONObject)object.get(identifier);
  }

  public Short getShort(String identifier) {
    return Short.valueOf(getString(identifier));
  }

  public Float getFloat(String identifier) {
    return Float.valueOf(getString(identifier));
  }

  public Double getDouble(String identifier) {
    return Double.valueOf(getString(identifier));
  }

  public Integer getInteger(String identifier) {
    return Integer.valueOf(getString(identifier));
  }

  public Boolean getBoolean(String identifier) {
    return Boolean.valueOf(getString(identifier));
  }

  public String getString(String identifier) {
    return object.get(identifier).toString();
  }

  public UUID getUUID(String identifier) {
    return UUID.fromString(object.get(identifier).toString());
  }

  public JSONObject getObject() {
    return object;
  }

  public void setObject(JSONObject object) {
    this.object = object;
  }
}