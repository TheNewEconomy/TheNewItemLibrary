package net.tnemc.sponge.data;

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

import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.EnchantStorageData;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpongeEnchantData extends EnchantStorageData<ItemStack> {

  protected boolean applies = false;

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {

    final Optional<List<Enchantment>> enchants = stack.get(Keys.STORED_ENCHANTMENTS);

    if(enchants.isPresent()) {
      applies = true;
      for(Enchantment enchant : enchants.get()) {
        enchantments.put(enchant.type().key(RegistryTypes.ENCHANTMENT_TYPE).formatted(), enchant.level());
      }
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {

    final List<Enchantment> enchants = new ArrayList<>();
    for(final Map.Entry<String, Integer> entry : enchantments.entrySet()) {
      enchants.add(Enchantment.of((EnchantmentType)EnchantmentTypes.registry().value(ResourceKey.resolve(entry.getKey())), entry.getValue()));
    }
    stack.offer(Keys.STORED_ENCHANTMENTS, enchants);

    return stack;
  }

  @Override
  public boolean applies() {
    return applies;
  }
}