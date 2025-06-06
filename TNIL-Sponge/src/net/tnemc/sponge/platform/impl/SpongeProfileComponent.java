package net.tnemc.sponge.platform.impl;/*
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
import net.tnemc.item.providers.SkullProfile;
import net.tnemc.sponge.SpongeItemStack;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.profile.GameProfile;

/**
 * SpongeProfileComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class SpongeProfileComponent extends ProfileComponent<SpongeItemStack, ItemStack> {

  public SpongeProfileComponent() {

  }

  public SpongeProfileComponent(final SkullProfile profile) {

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
   *
   * @since 0.2.0.0
   */
  @Override
  public ItemStack apply(final SpongeItemStack serialized, final ItemStack item) {

    return null;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   *
   * @since 0.2.0.0
   */
  @Override
  public SpongeItemStack serialize(final ItemStack item, final SpongeItemStack serialized) {

    return null;
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

    return item.supports(Key.from(ResourceKey.sponge("game_profile"), GameProfile.class));
  }
}