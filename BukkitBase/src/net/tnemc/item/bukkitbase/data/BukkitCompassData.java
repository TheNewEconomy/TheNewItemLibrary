package net.tnemc.item.bukkitbase.data;

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
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.data.CompassData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class BukkitCompassData extends CompassData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(final ItemStack stack) {

    final CompassMeta meta = (CompassMeta)stack.getItemMeta();

    if(meta != null && meta.hasLodestone() && meta.isLodestoneTracked()) {
      this.tracked = true;

      final Location loc = meta.getLodestone();

      if(loc.getWorld() != null) {
        this.world = loc.getWorld().getUID();
      }
      this.x = loc.getX();
      this.y = loc.getY();
      this.z = loc.getZ();

      this.yaw = loc.getYaw();
      this.pitch = loc.getPitch();
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(final ItemStack stack) {

    final CompassMeta meta = (CompassMeta)ParsingUtil.buildFor(stack, CompassMeta.class);

    meta.setLodestoneTracked(tracked);
    if(tracked) {
      meta.setLodestone(new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch));
    }

    stack.setItemMeta(meta);

    return stack;
  }
}
