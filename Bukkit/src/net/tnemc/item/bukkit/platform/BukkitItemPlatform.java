package net.tnemc.item.bukkit.platform;
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

import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.platform.ItemPlatform;
import org.bukkit.inventory.ItemStack;

/**
 * BukkitItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitItemPlatform extends ItemPlatform<BukkitItemStack, ItemStack> {

  public static final BukkitItemPlatform PLATFORM = new BukkitItemPlatform();

  private BukkitItemPlatform() {

  }
  /**
   * @return the version that is being used currently
   */
  @Override
  public String version() {
    return ParsingUtil.version();
  }

  @Override
  public void addDefaults() {

  }
}