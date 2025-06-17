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

import net.kyori.adventure.text.Component;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.tnemc.item.component.impl.CustomNameComponent;
import net.tnemc.item.component.impl.ModelDataComponent;
import net.tnemc.item.fabric.FabricItemStack;
import net.tnemc.item.fabric.Utils;

import java.util.List;
import java.util.Optional;

/**
 * FabricModelDataComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class FabricModelDataComponent extends ModelDataComponent<FabricItemStack, ItemStack> {

  public FabricModelDataComponent() {

  }

  public FabricModelDataComponent(final List<String> colours, final List<Float> floats, final List<Boolean> flags, final List<String> strings) {

    super(colours, floats, flags, strings);
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

    return item.hasChangedComponent(DataComponentTypes.CUSTOM_NAME);
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

    final Optional<FabricModelDataComponent> componentOptional = serialized.component(identifier());
    //componentOptional.ifPresent(component->item.set(DataComponentTypes.CUSTOM_MODEL_DATA, Utils.toText(component.customName())));

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

    final Optional<CustomModelDataComponent> keyOptional = Optional.ofNullable(item.get(DataComponentTypes.CUSTOM_MODEL_DATA));
    keyOptional.ifPresent((key->{

      final FabricModelDataComponent component = (serialized.fabricComponent(identifier()) instanceof final ModelDataComponent<?, ?> getComponent)?
                                                  (FabricModelDataComponent)getComponent : new FabricModelDataComponent();

      //component.colours = key.colors();
    }));
    return serialized;
  }
}