package net.tnemc.sponge.platform.registry;
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

import net.tnemc.item.platform.registry.BaseHelper;
import net.tnemc.item.platform.registry.SupplierRegistryHandler;
import net.tnemc.item.providers.VersionUtil;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.RegistryTypes;

import java.util.LinkedList;

/**
 * SpongeHelper
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class SpongeHelper extends BaseHelper {

  public SpongeHelper() {
    registerHandler("materials", new SupplierRegistryHandler(() -> {

      final LinkedList<String> keys = new LinkedList<>();

      Sponge.game().registry(RegistryTypes.ITEM_TYPE).stream().forEach(itemType->keys.add(Sponge.game().registry(RegistryTypes.ITEM_TYPE).valueKey(itemType).asString()));;
      return keys;
    }));

    registerHandler("enchantments", new SupplierRegistryHandler(() -> {

      final LinkedList<String> keys = new LinkedList<>();
      Sponge.game().registry(RegistryTypes.ENCHANTMENT_TYPE).stream().forEach(enchantment->keys.add(Sponge.game().registry(RegistryTypes.ENCHANTMENT_TYPE).valueKey(enchantment).asString()));

      return keys;
    }));

    registerHandler("flags", new SupplierRegistryHandler(() -> {
      final LinkedList<String> keys = new LinkedList<>();

      keys.add("HIDE_ATTRIBUTES");
      keys.add("HIDE_DESTROYS");
      keys.add("HIDE_ENCHANTS");
      keys.add("HIDE_MISCELLANEOUS");
      keys.add("HIDE_UNBREAKABLE");
      keys.add("HIDE_PLACES");

      return keys;
    }));

    initializeHandlers();
  }
}