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
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.component.impl.BundleComponent;
import net.tnemc.item.component.impl.ContainerComponent;
import net.tnemc.item.component.impl.CustomNameComponent;
import net.tnemc.item.fabric.FabricItemStack;
import net.tnemc.item.fabric.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * FabricBundleComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class FabricBundleComponent extends BundleComponent<FabricItemStack, ItemStack> {

  public FabricBundleComponent() {

  }

  public FabricBundleComponent(final Map<Integer, AbstractItemStack<ItemStack>> items) {

    super(items);
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

    return item.hasChangedComponent(DataComponentTypes.BUNDLE_CONTENTS);
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

    final Optional<FabricBundleComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      final LinkedList<ItemStack> contents = new LinkedList<>();
      for(final Map.Entry<Integer, AbstractItemStack<ItemStack>> entry : componentOptional.get().items.entrySet()) {

        if(entry.getValue() instanceof final FabricItemStack fabricItemStack) {

          contents.set(entry.getKey(), fabricItemStack.cacheLocale());
        }
      }

      final BundleContentsComponent bundleContents = new BundleContentsComponent(contents);

      item.set(DataComponentTypes.BUNDLE_CONTENTS, bundleContents);
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

    final Optional<BundleContentsComponent> keyOptional = Optional.ofNullable(item.get(DataComponentTypes.BUNDLE_CONTENTS));
    keyOptional.ifPresent((key->{

      final FabricBundleComponent component = (serialized.fabricComponent(identifier()) instanceof final BundleComponent<?, ?> getComponent)?
                                                  (FabricBundleComponent)getComponent : new FabricBundleComponent();


      for(int i = 0; i < key.size(); i++) {


        component.items.put(i, new FabricItemStack().of(key.get(i)));

      }
    }));
    return serialized;
  }
}