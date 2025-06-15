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
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.profile.property.ProfileProperty;

import java.util.NoSuchElementException;
import java.util.Optional;

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
    
    try {

      final Key<Value<GameProfile>> gameProfile = Keys.GAME_PROFILE;
      return true;
    } catch(final NoSuchElementException ignore) {

      return false;
    }
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

    return item.supports(Keys.GAME_PROFILE);
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

    final Optional<SpongeProfileComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      final SkullProfile profile = component.profile;
      item.offer(Keys.GAME_PROFILE, GameProfile.of(profile.uuid(), profile.name()).withProperty(ProfileProperty.of("textures", profile.texture())));
    });
    return item;
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

    final Optional<GameProfile> keyOptional = item.get(Keys.GAME_PROFILE);
    keyOptional.ifPresent(key->{

      final SpongeProfileComponent component = (serialized.spongeComponent(identifier()) instanceof final ProfileComponent<?, ?> getComponent)?
                                               (SpongeProfileComponent)getComponent : new SpongeProfileComponent();

      final SkullProfile profile = new SkullProfile(key.name().get(), key.uuid());
      for(final ProfileProperty property : key.properties()) {

        if(property.name().equalsIgnoreCase("textures")) {

          profile.texture(property.value());
        }
      }
      component.profile = profile;
    });
    return serialized;
  }
}