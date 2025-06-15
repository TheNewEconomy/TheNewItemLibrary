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
 * EntityVariantComponent - Holds information about the entity variant that should be carried over
 * for items such as mob bucket, spawn egg, etc. Added in MC 1.21.5
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class EntityVariantComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected String entity = "";
  protected String variant = "";

  public EntityVariantComponent() {

  }

  public EntityVariantComponent(final String entity, final String variant) {

    this.entity = entity;
    this.variant = variant;
  }

  /**
   * @return the type of component this is.
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {

    return "entity_variant";
  }

  /**
   * Converts the {@link SerialComponent} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialComponent}.
   * @since 0.2.0.0
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    json.put("entity", entity);
    json.put("variant", variant);

    return json;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialComponent} object.
   *
   * @param json The JSONHelper instance of the json data.
   * @since 0.2.0.0
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    this.entity = json.getString("entity");
    this.variant = json.getString("variant");
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data. For instance, book copies will return false when compared to the original.
   *
   * @param component The component to compare.
   *
   * @return True if similar, otherwise false.
   * @since 0.2.0.0
   */
  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final EntityVariantComponent<?, ?> other)) return false;

    return entity.equals(other.entity) && variant.equals(other.variant);
  }

  @Override
  public int hashCode() {

    return Objects.hash(entity, variant);
  }

  public String entity() {

    return entity;
  }

  public void entity(final String entity) {

    this.entity = entity;
  }

  public String variant() {

    return variant;
  }

  public void variant(final String variant) {

    this.variant = variant;
  }
}