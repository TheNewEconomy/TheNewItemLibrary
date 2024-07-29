package net.tnemc.item.bukkitbase.component;
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

import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.component.impl.FoodRule;
import net.tnemc.item.component.impl.JukeBoxComponent;
import net.tnemc.item.data.potion.PotionEffectData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.JukeboxPlayableComponent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * BukkitJukeBoxComponent
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitJukeBoxComponent extends JukeBoxComponent<ItemStack> {

  public static BukkitJukeBoxComponent create(ItemStack stack) {

    final BukkitJukeBoxComponent component = new BukkitJukeBoxComponent();
    component.of(stack);
    return component;
  }
  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialComponent} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    if(stack.hasItemMeta()) {
      if(stack.getItemMeta().hasJukeboxPlayable()) {

        final JukeboxPlayableComponent juke = stack.getItemMeta().getJukeboxPlayable();
        this.song = juke.getSongKey().getKey();
      }
    }
  }

  /**
   * This method is used to apply the component to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {
    ItemMeta meta = stack.getItemMeta();
    if(meta == null) {
      meta = Bukkit.getItemFactory().getItemMeta(stack.getType());
    }

    final JukeboxPlayableComponent juke = meta.getJukeboxPlayable();
    juke.setSongKey(NamespacedKey.fromString(song));

    meta.setJukeboxPlayable(juke);
    stack.setItemMeta(meta);
    return stack;
  }
}