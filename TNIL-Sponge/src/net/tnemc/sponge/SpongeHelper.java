package net.tnemc.sponge;

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

import net.tnemc.item.providers.HelperMethods;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.RegistryTypes;

import java.util.Collection;
import java.util.LinkedList;

/**
 * SpongeHelper
 *
 * @author creatorfromhell
 * @since 0.1.7.5-Pre-2
 */
public class SpongeHelper implements HelperMethods {
  @Override
  public Collection<String> materials() {
    final LinkedList<String> materials = new LinkedList<>();

    Sponge.game().registry(RegistryTypes.ITEM_TYPE).stream().forEach(itemType ->materials.add(Sponge.game().registry(RegistryTypes.ITEM_TYPE).valueKey(itemType).asString()));

    return materials;
  }

  @Override
  public Collection<String> enchantments() {
    final LinkedList<String> enchantments = new LinkedList<>();

    Sponge.game().registry(RegistryTypes.ENCHANTMENT_TYPE).stream().forEach(enchantment ->enchantments.add(Sponge.game().registry(RegistryTypes.ENCHANTMENT_TYPE).valueKey(enchantment).asString()));

    return enchantments;
  }

  @Override
  public Collection<String> flags() {
    final LinkedList<String> flags = new LinkedList<>();

    flags.add("HIDE_ATTRIBUTES");
    flags.add("HIDE_DESTROYS");
    flags.add("HIDE_ENCHANTS");
    flags.add("HIDE_MISCELLANEOUS");
    flags.add("HIDE_UNBREAKABLE");
    flags.add("HIDE_PLACES");

    return flags;
  }
}
