package net.tnemc.item.bukkitbase.platform.check;
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

import net.tnemc.item.platform.check.LocaleItemCheck;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

/**
 * PDCCheck
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public abstract class PDCCheck implements LocaleItemCheck<ItemStack> {

  protected final NamespacedKey key;
  protected final PersistentDataType<?, ?> type;

  public PDCCheck(final NamespacedKey key, final PersistentDataType<?, ?> type) {
    this.key = key;
    this.type = type;
  }

  /**
   * @return the identifier for this check.
   */
  @Override
  public String identifier() {
    return "";
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   */
  @Override
  public boolean enabled(String version) {
    return false;
  }

  /**
   * @param original the original stack
   * @param check    the stack to use for the check
   *
   * @return True if the check passes, otherwise false.
   */
  @Override
  public boolean check(ItemStack original, ItemStack check) {

    if(original.getItemMeta() != null && original.getItemMeta().getPersistentDataContainer().has(key, type)) {
      if(check.getItemMeta() == null || !check.getItemMeta().getPersistentDataContainer().has(key, type)) {
        return false;
      }
      return Objects.equals(check.getItemMeta().getPersistentDataContainer().get(key, type), original.getItemMeta().getPersistentDataContainer().get(key, type));
    }

    if(check.getItemMeta() != null && check.getItemMeta().getPersistentDataContainer().has(key, type)) {
      return false;
    }
    return true;
  }
}