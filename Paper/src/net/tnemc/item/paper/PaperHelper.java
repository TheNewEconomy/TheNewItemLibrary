package net.tnemc.item.paper;

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
import org.bukkit.Registry;
import org.bukkit.inventory.ItemFlag;

import java.util.LinkedList;

/**
 * BukkitHelper
 *
 * @author creatorfromhell
 * @since 0.1.7.5-Pre-5
 */
public class PaperHelper implements HelperMethods {
  final LinkedList<String> materialKeys = new LinkedList<>();
  final LinkedList<String> enchantmentKeys = new LinkedList<>();
  final LinkedList<String> itemFlagKeys = new LinkedList<>();

  public PaperHelper() {

    Registry.MATERIAL.forEach((material)->{

      if(material.isItem()) {
        materialKeys.add(material.getKey().getKey());
      }
    });

    Registry.ENCHANTMENT.forEach((enchant)->{
      enchantmentKeys.add(enchant.getKey().getKey());
    });

    for(ItemFlag itemFlag : ItemFlag.values()) {
      itemFlagKeys.add(itemFlag.name());
    }
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