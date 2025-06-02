package net.tnemc.item.bukkit.platform.impl;
/*
 * The New Item Library
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

import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.component.impl.ProfileComponent;
import net.tnemc.item.component.impl.RarityComponent;
import net.tnemc.item.providers.SkullProfile;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Optional;

/**
 * BukkitProfileComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitProfileComponent extends ProfileComponent<BukkitItemStack, ItemStack> {

  public BukkitProfileComponent() {

  }

  public BukkitProfileComponent(final SkullProfile profile) {

    super(profile);
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    return true;
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   * @since 0.2.0.0
   */
  @Override
  public ItemStack apply(final BukkitItemStack serialized, final ItemStack item) {

    final ItemMeta meta = item.getItemMeta();
    final Optional<BukkitProfileComponent> componentOptional = serialized.component(identifier());
    if(meta instanceof final SkullMeta skullMeta && componentOptional.isPresent()) {

      final SkullProfile profile = componentOptional.get().profile;

      if(profile != null) {

        try {

          if(profile.getUuid() != null) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(profile.getUuid()));
          }

        } catch(final Exception ignore) {

          skullMeta.setOwner(profile.getName());
        }
      }
      item.setItemMeta(meta);
    }
    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack serialize(final ItemStack item, final BukkitItemStack serialized) {

    if(item.getItemMeta() instanceof final SkullMeta meta) {

      final BukkitProfileComponent component = (serialized.bukkitComponent(identifier()) instanceof final ProfileComponent<?, ?> getComponent)?
                                              (BukkitProfileComponent)getComponent : new BukkitProfileComponent();

      final SkullProfile profile = new SkullProfile();

      try {

        if(meta.getOwningPlayer() != null) {

          profile.setUuid(meta.getOwningPlayer().getUniqueId());
        }

      } catch(final Exception ignore) {

        profile.setName(meta.getOwner());
      }

      component.profile(profile);

      serialized.applyComponent(component);
    }
    return serialized;
  }

  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.getItemMeta() instanceof SkullMeta;
  }
}