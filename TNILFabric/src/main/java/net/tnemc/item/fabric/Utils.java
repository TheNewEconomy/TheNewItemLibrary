package net.tnemc.item.fabric;/*
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
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.text.Text;

/**
 * Utils
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class Utils {

  public static Component toComponent(final Text text) {
    final String json = Text.Serialization.toJsonString(text, DynamicRegistryManager.EMPTY);
    return GsonComponentSerializer.gson().deserialize(json);
  }

  public static Text toText(final Component component) {
    final String json = GsonComponentSerializer.gson().serialize(component);
    return Text.Serialization.fromJson(json, DynamicRegistryManager.EMPTY);
  }
}