package net.tnemc.item.sponge.data;

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

import net.kyori.adventure.text.Component;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.BookData;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SpongeBookData extends BookData<ItemStack> {

  protected boolean applies = false;

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {

    final Optional<List<Component>> pages = stack.get(Keys.PAGES);
    if(pages.isPresent()) {
      applies = true;
      for(Component comp : pages.get()) {
        this.pages.add(comp.toString());
      }
    }

    final Optional<Component> author = stack.get(Keys.AUTHOR);
    author.ifPresent(component->{
      this.author = component.toString();
      applies = true;
    });

    final Optional<Integer> generation = stack.get(Keys.GENERATION);
    generation.ifPresent(integer->{
      this.generation = String.valueOf(integer);
      applies = true;
    });
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {

    if(!author.equalsIgnoreCase("")) {
      stack.offer(Keys.AUTHOR, Component.text(author));
    }

    if(!this.generation.equalsIgnoreCase("")) {
      stack.offer(Keys.GENERATION, Integer.valueOf(this.generation));
    }

    final List<Component> pages = new LinkedList<>();
    for(String page : this.pages) {
      pages.add(Component.text(page));
    }
    stack.offer(Keys.PAGES, pages);

    return stack;
  }

  @Override
  public boolean applies() {

    return applies;
  }
}