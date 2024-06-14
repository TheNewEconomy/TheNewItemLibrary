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

import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.SerialPotionData;
import net.tnemc.item.data.potion.PotionEffectData;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.weighted.TableEntry;
import org.spongepowered.api.util.weighted.WeightedTable;

import java.util.Optional;

public class SpongePotionData extends SerialPotionData<ItemStack> {

  protected boolean applies = false;

  protected WeightedTable<PotionEffectData> customEffects = new WeightedTable<>();

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {

      final Optional<WeightedTable<PotionEffect>> effects = stack.get(Keys.APPLICABLE_POTION_EFFECTS);
      if(effects.isPresent()) {
        applies = true;

        while(effects.get().iterator().hasNext()) {
          final TableEntry<PotionEffect> entry = effects.get().iterator().next();

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
    return stack;
  }

  @Override
  public boolean applies() {
    return applies;
  }
}