package net.tnemc.item.fabric.platform.impl;/*
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

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.tnemc.item.component.impl.ProfileComponent;
import net.tnemc.item.fabric.FabricItemStack;
import net.tnemc.item.providers.SkullProfile;

import java.util.Optional;

/**
 * FabricProfileComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class FabricProfileComponent extends ProfileComponent<FabricItemStack, ItemStack> {

  public FabricProfileComponent() {

  }

  public FabricProfileComponent(final SkullProfile profile) {

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
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.hasChangedComponent(DataComponentTypes.PROFILE);
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
  public ItemStack apply(final FabricItemStack serialized, final ItemStack item) {

    final Optional<FabricProfileComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      if(component.profile.uuid() != null && component.profile.name() != null) {

        final GameProfile profile = new GameProfile(component.profile.uuid(), component.profile.name());
        if(component.profile.texture() != null) {

          profile.getProperties().put("textures", new Property("textures", component.profile.texture()));
        }

        item.set(DataComponentTypes.PROFILE, new net.minecraft.component.type.ProfileComponent(profile));
      }
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
  public FabricItemStack serialize(final ItemStack item, final FabricItemStack serialized) {

    final Optional<net.minecraft.component.type.ProfileComponent> keyOptional = Optional.ofNullable(item.get(DataComponentTypes.PROFILE));
    keyOptional.ifPresent((key->{

      final FabricProfileComponent component = (serialized.fabricComponent(identifier()) instanceof final ProfileComponent<?, ?> getComponent)?
                                                  (FabricProfileComponent)getComponent : new FabricProfileComponent();

      final SkullProfile profile = new SkullProfile();
      profile.uuid(key.gameProfile().getId());
      profile.name(key.gameProfile().getName());
      if(key.gameProfile().getProperties().containsKey("textures")) {

        profile.texture(key.gameProfile().getProperties().get("textures").toString());
      }

      component.profile = profile;
    }));
    return serialized;
  }
}