package net.tnemc.item.bukkitbase.platform.providers;
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

import dev.lone.itemsadder.api.CustomStack;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.providers.ItemProvider;
import org.bukkit.inventory.ItemStack;

/**
 * ItemAdderProvider
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class ItemAdderProvider implements ItemProvider<ItemStack> {

  /**
   * Checks if the given serialized item stack applies to the specified item.
   *
   * @param serialized The serialized item stack to check against the item.
   * @param item       The item to check against.
   *
   * @return True if the serialized item stack applies to the item, false otherwise.
   */
  @Override
  public boolean appliesTo(final AbstractItemStack<? extends ItemStack> serialized, final ItemStack item) {

    final CustomStack customStack = CustomStack.byItemStack(item);
    if(customStack == null) {
      return false;
    }

    serialized.setItemProvider(identifier());
    serialized.setProviderItemID(customStack.getId());

    return true;
  }

  /**
   * Checks if the provided item stack is similar to the original item stack.
   *
   * @param original The original item stack to compare against.
   * @param compare  The item stack to compare.
   *
   * @return True if the two item stacks are similar, otherwise false.
   */
  @Override
  public boolean similar(final AbstractItemStack<? extends ItemStack> original, final ItemStack compare) {

    final CustomStack originalStack = CustomStack.getInstance(original.providerItemID());
    if(originalStack == null) {
      return false;
    }

    final CustomStack compareStack = CustomStack.byItemStack(compare);
    if(compareStack == null) {
      return false;
    }

    return originalStack.matchNamespacedID(compareStack);
  }

  /**
   * Creates a copy of the original item stack with a specified amount.
   *
   * @param original The original item stack to copy.
   * @param amount   The amount for the new item stack.
   *
   * @return A new item stack with the specified amount.
   */
  @Override
  public ItemStack locale(final AbstractItemStack<? extends ItemStack> original, final int amount) {

    final CustomStack customStack = CustomStack.getInstance(original.providerItemID());
    if(customStack == null) {
      return null;
    }

    final ItemStack stack = customStack.getItemStack();
    stack.setAmount(amount);

    return stack;
  }

  /**
   * @return the identifier for this check.
   */
  @Override
  public String identifier() {

    return "itemsadder";
  }
}