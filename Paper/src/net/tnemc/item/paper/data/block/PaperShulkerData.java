package net.tnemc.item.paper.data.block;

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

import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.data.ShulkerData;
import net.tnemc.item.paper.PaperItemStack;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PaperShulkerData extends ShulkerData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(final ItemStack stack) {

    final BlockStateMeta meta = (BlockStateMeta)stack.getItemMeta();

    if(meta != null && meta.getBlockState() instanceof final ShulkerBox box) {

      if(box.getColor() != null) {
        colorRGB = box.getColor().getColor().asRGB();
      }

      final Inventory inventory = box.getInventory();
      for(int i = 0; i < inventory.getSize(); i++) {
        if(inventory.getItem(i) != null && !inventory.getItem(i).getType().equals(Material.AIR)) {
          items.put(i, new SerialItem<>(PaperItemStack.locale(inventory.getItem(i))));
        }
      }
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(final ItemStack stack) {

    final BlockStateMeta meta = (BlockStateMeta)ParsingUtil.buildFor(stack, BlockStateMeta.class);

    if(meta.getBlockState() instanceof final ShulkerBox box) {

      items.forEach((slot, item)->box.getInventory().setItem(slot, item.getStack().locale()));
      box.update(true);
      meta.setBlockState(box);
      stack.setItemMeta(meta);
    }
    return stack;
  }
}