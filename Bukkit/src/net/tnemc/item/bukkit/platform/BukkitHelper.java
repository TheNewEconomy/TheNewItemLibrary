package net.tnemc.item.bukkit.platform;
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
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.LinkedList;

/**
 * BukkitHelper
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitHelper extends BaseHelper {

  public BukkitHelper() {
    registerHandler("materials", new SupplierRegistryHandler(() -> {

      final LinkedList<String> keys = new LinkedList<>();

      Registry.MATERIAL.forEach(material -> {
        if(material.isItem()) {
          keys.add(material.getKey().getKey());
        }
      });
      return keys;
    }));

    registerHandler("enchantments", new SupplierRegistryHandler(() -> {

      final LinkedList<String> keys = new LinkedList<>();
      if(VersionUtil.isVersion(BukkitItemPlatform.instance().version(), "1.20.3")) {

        Registry.ENCHANTMENT.forEach((enchantment) -> {
          if(enchantment != null) {

            keys.add(enchantment.getKey().toString());
          }
        });

      } else {

        for(final Enchantment enchantment : Enchantment.values()) {
          keys.add(enchantment.getKey().toString());
        }
      }
      return keys;
    }));

    registerHandler("flags", new SupplierRegistryHandler(() -> {
      final LinkedList<String> keys = new LinkedList<>();

      for(final ItemFlag itemFlag : ItemFlag.values()) {
        keys.add(itemFlag.name());
      }
      return keys;
    }));

    initializeHandlers();
  }
}