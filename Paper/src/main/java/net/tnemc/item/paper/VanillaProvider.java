package net.tnemc.item.paper;
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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.ItemProvider;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;

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

    final PaperItemStack compareStack = new PaperItemStack().of(compare);

    return PaperItemPlatform.instance().check((PaperItemStack)original, compareStack);
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

    if(original instanceof final PaperItemStack bukkit) {

      if(!bukkit.isDirty()) {
        return bukkit.cacheLocale();
      }

      Material material = null;

      //System.out.println("Creating paper locale");
      try {
        final NamespacedKey key = NamespacedKey.fromString(bukkit.material());

        //System.out.println("looking for locale mat key");
        if(key != null) {

          //System.out.println("locale key found");

          material = Registry.MATERIAL.get(key);
        }
      } catch(final NoSuchMethodError ignore) {

        //System.out.println("locale key not found");

        material = Material.matchMaterial(bukkit.material());
      }

      if(material == null) {

        //System.out.println("locale material null");

        return null;
      }
      ItemStack stack = new ItemStack(material, amount);

      //System.out.println("locale Material: " + material.translationKey());

      stack = PaperItemPlatform.instance().apply(bukkit, stack);

      //System.out.println("locale Material v2: " + stack.getType().translationKey());

      bukkit.updateCache(stack);
      bukkit.resetDirty();

      //System.out.println("locale Material v3: " + stack.getType().translationKey());

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
