package net.tnemc.item.paper.platform.impl.old;
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

import net.tnemc.item.component.impl.ProfileComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.SkullProfile;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Optional;
import java.util.UUID;

/**
 * PaperOldProfileComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperOldProfileComponent extends ProfileComponent<PaperItemStack, ItemStack> {

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
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
   */
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final ItemMeta meta = item.getItemMeta();
    final Optional<PaperOldProfileComponent> componentOptional = serialized.component(identifier());
    if(meta instanceof final SkullMeta skullMeta && componentOptional.isPresent()) {

      if(profile != null) {

        try {

          final UUID uuid = invokeProfile(profile, "uuid", "getUuid");
          if(uuid != null) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
          }

        } catch(final Exception ignore) {

          final String name = invokeProfile(profile, "name", "getName");
          if(name != null) {
            skullMeta.setOwner(name);
          }
        }
      }
      item.setItemMeta(meta);
    }
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    if(item.getItemMeta() instanceof final SkullMeta meta) {

      profile = new SkullProfile();

      try {

        if(meta.getOwningPlayer() != null) {
          invokeProfileVoid(profile, meta.getOwningPlayer().getUniqueId(), "uuid", "setUuid");
        }

      } catch(final Exception ignore) {

        invokeProfileVoid(profile, meta.getOwner(), "name", "setName");
      }
      serialized.applyComponent(this);
    }
    return serialized;
  }

  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.getItemMeta() instanceof SkullMeta;
  }

  @SuppressWarnings("unchecked")
  private static <T> T invokeProfile(final SkullProfile profile, final String... methods) {

    for(final String method : methods) {
      try {
        return (T)profile.getClass().getMethod(method).invoke(profile);
      } catch(final Exception ignore) {
      }
    }
    return null;
  }

  private static void invokeProfileVoid(final SkullProfile profile, final Object value, final String... methods) {

    for(final String method : methods) {
      try {
        profile.getClass().getMethod(method, value.getClass()).invoke(profile, value);
        return;
      } catch(final Exception ignore) {
      }
    }
  }
}