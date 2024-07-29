package net.tnemc.item.paper.data;
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

import net.kyori.adventure.key.Key;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.data.InstrumentData;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MusicInstrumentMeta;

/**
 * BukkitInstrumentData
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class PaperInstrumentData extends InstrumentData<ItemStack> {
  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final MusicInstrumentMeta meta = (MusicInstrumentMeta)stack.getItemMeta();

    if(meta != null && meta.getInstrument() != null) {

      try {
        this.instrument = meta.getInstrument().getKey().getKey();
      } catch(Exception ignore) {
        this.instrument = meta.getInstrument().key().asString();
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

    final MusicInstrumentMeta meta = (MusicInstrumentMeta)ParsingUtil.buildFor(stack, MusicInstrumentMeta.class);

    if(this.instrument != null) {

      try {
        meta.setInstrument(Registry.INSTRUMENT.get(NamespacedKey.fromString(this.instrument)));
      } catch(Exception ignore) {
        meta.setInstrument(Registry.INSTRUMENT.get(Key.key(this.instrument)));
      }
    }
    stack.setItemMeta(meta);

    return stack;
  }
}