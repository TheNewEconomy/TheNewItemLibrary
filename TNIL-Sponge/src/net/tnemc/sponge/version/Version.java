package net.tnemc.sponge.version;

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
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public interface Version {

  /**
   * Used to attempt to get the {@link SerialItemData item data} from an item stack.
   *
   * @param stack    The stack to use for this operation.
   * @param itemType The item type, including the namespace to use.
   *
   * @return An optional containing the item data if it was possible to get, otherwise an empty
   * optional.
   */
  Optional<SerialItemData<ItemStack>> findData(final ItemStack stack, final String itemType);
}