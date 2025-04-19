package net.tnemc.item.component.impl;
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
import net.tnemc.item.JSONHelper;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * ModelDataOldComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class ModelDataOldComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected int modelData;

  public ModelDataOldComponent() {
    this.modelData = -1;
  }

  public ModelDataOldComponent(final int modelData) {

    this.modelData = modelData;
  }

  /**
   * @return the type of component this is.
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {
    return "model-data-old";
  }

  /**
   * Converts this component's data to a JSON object.
   *
   * @return The JSONObject representing this component's data.
   * @since 0.2.0.0
   */
  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("modelData", modelData);
    return json;
  }

  /**
   * Reads JSON data and converts it back to this component's data.
   *
   * @param json      The JSONHelper instance of the json data.
   * @param platform  The ItemPlatform instance.
   * @since 0.2.0.0
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    this.modelData = json.getInteger("modelData");
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data.
   *
   * @param component The component to compare.
   * @return True if similar, otherwise false.
   * @since 0.2.0.0
   */
  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if(!(component instanceof final ModelDataOldComponent<?, ?> other)) return false;
    return this.modelData == other.modelData();
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelData);
  }

  public int modelData() {

    return modelData;
  }

  public void modelData(final int modelData) {

    this.modelData = modelData;
  }
}