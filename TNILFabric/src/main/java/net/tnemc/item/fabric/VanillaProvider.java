package net.tnemc.item.fabric;
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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.fabric.platform.FabricItemPlatform;
import net.tnemc.item.providers.ItemProvider;

/**
 * VanillaProvider
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class VanillaProvider implements ItemProvider<ItemStack> {

  /**
   * Checks if the given serialized item stack applies to the specified item.
   *
   * @param serialized The serialized item stack to check against the item.
   * @param item       The item to check against.
   *
   * @return True if the serialized item stack applies to the item, false otherwise.
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final AbstractItemStack<? extends ItemStack> serialized, final ItemStack item) {

    return true;
  }

  /**
   * Checks if the provided item stack is similar to the original item stack.
   *
   * @param original The original item stack to compare against.
   * @param compare  The item stack to compare.
   *
   * @return True if the two item stacks are similar, otherwise false.
   * @since 0.2.0.0
   */
  @Override
  public boolean similar(final AbstractItemStack<? extends ItemStack> original, final ItemStack compare) {

    final FabricItemStack compareStack = new FabricItemStack().of(compare);

    return FabricItemPlatform.instance().check((FabricItemStack)original, compareStack);
  }

  /**
   * Creates a copy of the original item stack with a specified amount.
   *
   * @param original The original item stack to copy.
   * @param amount   The amount for the new item stack.
   *
   * @return A new item stack with the specified amount.
   * @since 0.2.0.0
   */
  @Override
  public ItemStack locale(final AbstractItemStack<? extends ItemStack> original, final int amount) {


    if(original instanceof final FabricItemStack fabric) {

      if(!fabric.isDirty()) {

        return fabric.cacheLocale();
      }

      final Item item = Registries.ITEM.get(fabric.id());

      ItemStack stack = new ItemStack(item, amount);

      stack = FabricItemPlatform.instance().apply(fabric, stack);

      fabric.updateCache(stack);
      fabric.resetDirty();

      return stack;
    }
    return null;
  }

  /**
   * @return the identifier for this check.
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {

    return "vanilla";
  }
}
