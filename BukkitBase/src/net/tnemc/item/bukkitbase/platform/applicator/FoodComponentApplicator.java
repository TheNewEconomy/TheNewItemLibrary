package net.tnemc.item.bukkitbase.platform.applicator;
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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.platform.applier.ItemApplicator;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

/**
 * FoodComponentApplication
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class FoodComponentApplicator implements ItemApplicator<AbstractItemStack<ItemStack>, ItemStack> {
  /**
   * @return the identifier for this check.
   */
  @Override
  public String identifier() {
    return "food-applicator";
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   */
  @Override
  public boolean enabled(String version) {
    return VersionUtil.isOneTwentyOne(version);
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   */
  @Override
  public ItemStack apply(AbstractItemStack<ItemStack> serialized, ItemStack item) {

    if(serialized.components().containsKey("food")) {
      return serialized.components().get("food").apply(item);
    }

    return item;
  }
}
