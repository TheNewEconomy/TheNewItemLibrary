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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.JSONHelper;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * CustomNameComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#custom_name">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class CustomNameComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected Component customName; // Stores the item's custom name as a string

  public CustomNameComponent() {

  }

  public CustomNameComponent(final Component customName) {

    this.customName = customName;
  }

  @Override
  public String identifier() {

    return "custom_name";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("custom_name", LegacyComponentSerializer.legacySection().serialize(customName));
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    if(json.has("custom_name")) {
      customName = LegacyComponentSerializer.legacySection().deserialize(json.getString("custom_name"));
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final CustomNameComponent<?, ?> other)) return false;

    return Component.EQUALS.test(this.customName, other.customName);
  }

  @Override
  public int hashCode() {

    return Objects.hash(customName);
  }

  public Component customName() {

    return customName;
  }

  public void customName(final Component customName) {

    this.customName = customName;
  }
}