package net.tnemc.sponge;

/*
 * The New Item Library Minecraft Server Plugin
 *
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

import net.tnemc.item.providers.HelperMethods;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.RegistryTypes;

import java.util.LinkedList;

/**
 * SpongeHelper
 *
 * @author creatorfromhell
 * @since 0.1.7.5-Pre-5
 */
public class SpongeHelper implements HelperMethods {

  final LinkedList<String> materialKeys = new LinkedList<>();
  final LinkedList<String> enchantmentKeys = new LinkedList<>();
  final LinkedList<String> itemFlagKeys = new LinkedList<>();

  public SpongeHelper() {

    Sponge.game().registry(RegistryTypes.ITEM_TYPE).stream().forEach(itemType->materialKeys.add(Sponge.game().registry(RegistryTypes.ITEM_TYPE).valueKey(itemType).asString()));
    Sponge.game().registry(RegistryTypes.ENCHANTMENT_TYPE).stream().forEach(enchantment->enchantmentKeys.add(Sponge.game().registry(RegistryTypes.ENCHANTMENT_TYPE).valueKey(enchantment).asString()));

    itemFlagKeys.add("HIDE_ATTRIBUTES");
    itemFlagKeys.add("HIDE_DESTROYS");
    itemFlagKeys.add("HIDE_ENCHANTS");
    itemFlagKeys.add("HIDE_MISCELLANEOUS");
    itemFlagKeys.add("HIDE_UNBREAKABLE");
    itemFlagKeys.add("HIDE_PLACES");
  }

  @Override
  public LinkedList<String> materials() {

    return materialKeys;
  }

  @Override
  public LinkedList<String> enchantments() {

    return enchantmentKeys;
  }

  @Override
  public LinkedList<String> flags() {

    return itemFlagKeys;
  }
}
