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

import net.tnemc.item.component.impl.ItemModelComponent;
import net.tnemc.sponge.SpongeItemStack;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * SpongeItemModelComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class SpongeItemModelComponent extends ItemModelComponent<SpongeItemStack, ItemStack> {

  public SpongeItemModelComponent() {

  }

  public SpongeItemModelComponent(final String model) {

    super(model);
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    try {

      final Key<Value<ResourceKey>> model = Keys.MODEL;
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
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.supports(Keys.MODEL);
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

    final Optional<SpongeItemModelComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->item.offer(Keys.MODEL, ResourceKey.resolve(component.model)));

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

    final Optional<ResourceKey> keyOptional = item.get(Keys.MODEL);
    keyOptional.ifPresent((key->{

      final SpongeItemModelComponent component = (serialized.spongeComponent(identifier()) instanceof final ItemModelComponent<?, ?> getComponent)?
                                                 (SpongeItemModelComponent)getComponent : new SpongeItemModelComponent();

      component.model = key.formatted();
    }));
    return serialized;
  }
}