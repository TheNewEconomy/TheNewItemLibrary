package net.tnemc.item.sponge.data;

/*
 * The New Item Library Minecraft Server Plugin
 *
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

import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.FireworkData;
import net.tnemc.item.data.firework.SerialFireworkEffect;
import net.tnemc.sponge.ParsingUtil;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.FireworkEffect;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.Ticks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpongeFireworkData extends FireworkData<ItemStack> {

  protected boolean applies = false;

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {

    final Optional<Ticks> power = stack.get(Keys.FIREWORK_FLIGHT_MODIFIER);
    power.ifPresent(ticks->{
      this.power = ticks.ticks();
      applies = true;
    });

    final Optional<List<FireworkEffect>> effs = stack.get(Keys.FIREWORK_EFFECTS);
    if(effs.isPresent()) {
      applies = true;
      for(FireworkEffect effect : effs.get()) {
        effects.add(ParsingUtil.fromEffect(effect));
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

    stack.offer(Keys.FIREWORK_FLIGHT_MODIFIER, Ticks.of(power));

    final List<FireworkEffect> effs = new ArrayList<>();
    for(SerialFireworkEffect effect : effects) {
      effs.add(ParsingUtil.fromSerial(effect));
    }

    if(effs.size() > 0) {
      stack.offer(Keys.FIREWORK_EFFECTS, effs);
    }

    return stack;
  }

  @Override
  public boolean applies() {

    return applies;
  }
}