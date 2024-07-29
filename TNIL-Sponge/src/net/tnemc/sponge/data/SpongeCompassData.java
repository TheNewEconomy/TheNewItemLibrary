package net.tnemc.sponge.data;

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
import net.tnemc.item.data.CompassData;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.math.vector.Vector3d;

import java.util.Optional;

public class SpongeCompassData extends CompassData<ItemStack> {

  protected boolean applies = false;

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {

    final Optional<ServerLocation> location = stack.get(Keys.LODESTONE);
    if(location.isPresent()) {
      tracked = true;
      applies = true;

      this.world = location.get().world().uniqueId();
      this.x = location.get().x();
      this.y = location.get().y();
      this.z = location.get().z();
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {
    if(tracked) {
      final Optional<ResourceKey> key = Sponge.server().worldManager().worldKey(world);
      key.ifPresent(resourceKey -> stack.offer(Keys.LODESTONE, ServerLocation.of(resourceKey, new Vector3d(x, y, z))));
    }

    return stack;
  }

  @Override
  public boolean applies() {
    return applies;
  }
}
