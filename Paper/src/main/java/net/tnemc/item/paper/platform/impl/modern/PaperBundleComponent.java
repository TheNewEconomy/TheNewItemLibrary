package net.tnemc.item.paper.platform.impl.modern;
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

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BundleContents;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.component.impl.BundleComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.paper.platform.impl.PaperSerialComponent;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;

import java.util.Map;
import java.util.Optional;

/**
 * PaperOldBundleComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperBundleComponent extends BundleComponent<PaperItemStack, ItemStack> implements PaperSerialComponent<PaperItemStack, ItemStack> {

  public PaperBundleComponent() {

  }

  public PaperBundleComponent(final Map<Integer, AbstractItemStack<ItemStack>> items) {

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

    return VersionUtil.isOneTwentyOneFour(version);
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   * @since 0.2.0.0
   */
  @Override
  public ItemStack applyModern(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperBundleComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final BundleContents.Builder builder = BundleContents.bundleContents();

    componentOptional.get().items.forEach((slot, stack)->builder.add(stack.provider().locale(serialized)));
    item.setData(DataComponentTypes.BUNDLE_CONTENTS, builder);

    return item;
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
  public ItemStack applyLegacy(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperBundleComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      if(item.hasItemMeta() && item.getItemMeta() instanceof final BundleMeta meta) {

        componentOptional.get().items.forEach((slot, stack)->meta.addItem(stack.provider().locale(serialized)));

        item.setItemMeta(meta);
      }
    });
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
  public PaperItemStack serializeModern(final ItemStack item, final PaperItemStack serialized) {

    final BundleContents contents = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
    if(contents == null) {
      return serialized;
    }

    final PaperBundleComponent component = (serialized.paperComponent(identifier()) instanceof final BundleComponent<?, ?> getComponent)?
                                           (PaperBundleComponent)getComponent : new PaperBundleComponent();

    int i = 0;
    for(final ItemStack stack : contents.contents()) {

      if(stack.getType().equals(Material.AIR)) {
        continue;
      }

      final PaperItemStack containerSerial = new PaperItemStack().of(stack);
      PaperItemPlatform.instance().providerApplies(containerSerial, stack);
      component.items.put(i, containerSerial);
      i++;
    }

    serialized.applyComponent(component);
    return serialized;
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
  public PaperItemStack serializeLegacy(final ItemStack item, final PaperItemStack serialized) {

    if(item.hasItemMeta() && item.getItemMeta() instanceof final BundleMeta meta) {

      final PaperBundleComponent component = (serialized.paperComponent(identifier()) instanceof final BundleComponent<?, ?> getComponent)?
                                             (PaperBundleComponent)getComponent : new PaperBundleComponent();

      int i = 0;
      for(final ItemStack stack : meta.getItems()) {
        if(stack == null) {
          continue;
        }

        if(stack.getType().equals(Material.AIR)) {
          continue;
        }

        final PaperItemStack containerSerial = new PaperItemStack().of(stack);
        PaperItemPlatform.instance().providerApplies(containerSerial, stack);
        component.items.put(i, containerSerial);
        i++;
      }

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

    return true;
  }
}