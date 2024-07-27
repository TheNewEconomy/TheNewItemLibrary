package net.tnemc.item.bukkitbase.data;
/*
 * The New Item Library
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
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.data.BookData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * BukkitBookMeta
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class BukkitBookData extends BookData<ItemStack> {


  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final BookMeta meta = (BookMeta)stack.getItemMeta();

    if(meta != null) {
      this.title = meta.getTitle();
      this.author = meta.getAuthor();
      this.pages.clear();
      this.pages.addAll(meta.getPages());

      if(meta.getGeneration() != null) {
        this.generation = meta.getGeneration().name();
      } else {
        this.generation = "";
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

    final BookMeta meta = (BookMeta)ParsingUtil.buildFor(stack, BookMeta.class);

    meta.setTitle(title);
    meta.setAuthor(author);

    if(!this.generation.equalsIgnoreCase("")) {
      meta.setGeneration(BookMeta.Generation.valueOf(generation));
    }
    meta.setPages(pages);
    stack.setItemMeta(meta);

    return stack;
  }
}
