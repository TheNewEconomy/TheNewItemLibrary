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

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.providers.ItemProvider;
import org.bukkit.inventory.ItemStack;

/**
 * OraxenAddon
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class OraxenProvider implements ItemProvider<ItemStack> {

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

    final String compareID = OraxenItems.getIdByItem(compare);
    if(compareID == null) {
      return false;
    }

    return original.providerItemID().equals(compareID);
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

    final ItemBuilder originalStack = OraxenItems.getItemById(original.providerItemID());
    if(originalStack == null) {

      return null;
    }

    final ItemStack stack = originalStack.build();
    stack.setAmount(amount);

    return stack;
  }

  /**
   * @return the identifier for this check.
   */
  @Override
  public String identifier() {

    return "oraxen";
  }
}