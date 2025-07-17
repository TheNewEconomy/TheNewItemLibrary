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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * DamageResistantComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#damage_resistant">Reference</a>
 * @since 0.2.0.0
 */
public abstract class DamageResistantComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<String> types = new ArrayList<>();

  public DamageResistantComponent() {

  }

  public DamageResistantComponent(final String type) {

    this.types.add(type);
  }

  public DamageResistantComponent(final List<String> types) {

    this.types.addAll(types);
  }

  @Override
  public String identifier() {

    return "damage_resistant";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("types", types);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    types.clear();
    if(json.has("types")) {
      types.addAll(json.getStringList("types"));
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final DamageResistantComponent<?, ?> other)) return false;

    return Objects.equals(this.types, other.types);
  }

  @Override
  public int hashCode() {

    return Objects.hash(types);
  }

  public List<String> types() {

    return types;
  }

  public void types(final List<String> types) {

    this.types.clear();
    this.types.addAll(types);
  }

  public void types(final String... types) {

    this.types.clear();
    this.types.addAll(Arrays.asList(types));
  }
}
