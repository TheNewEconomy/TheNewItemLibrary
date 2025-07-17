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

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.tnemc.item.component.impl.MaxStackSizeComponent;
import net.tnemc.item.fabric.FabricItemStack;

import java.util.Optional;

/**
 * FabricMaxStackComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class FabricMaxStackComponent extends MaxStackSizeComponent<FabricItemStack, ItemStack> {

  public FabricMaxStackComponent() {

  }

  public FabricMaxStackComponent(final int maxStackSize) {

    super(maxStackSize);
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

    return true;
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

    return item.hasChangedComponent(DataComponentTypes.MAX_STACK_SIZE);
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

    final Optional<FabricMaxStackComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->item.set(DataComponentTypes.MAX_STACK_SIZE, component.maxStackSize));

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

    final Optional<Integer> keyOptional = Optional.ofNullable(item.get(DataComponentTypes.MAX_STACK_SIZE));
    keyOptional.ifPresent((key->{

      final FabricMaxStackComponent component = (serialized.fabricComponent(identifier()) instanceof final MaxStackSizeComponent<?, ?> getComponent)?
                                                (FabricMaxStackComponent)getComponent : new FabricMaxStackComponent();

      component.maxStackSize = key;
    }));
    return serialized;
  }
}