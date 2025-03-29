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
import net.tnemc.item.providers.VersionUtil;
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
public class PaperBundleComponent extends BundleComponent<PaperItemStack, ItemStack> {

  public PaperBundleComponent() {

  }

  public PaperBundleComponent(final Map<Integer, AbstractItemStack<ItemStack>> items) {

    super(items);
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
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
   */
  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperBundleComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final BundleContents.Builder builder = BundleContents.bundleContents();

    componentOptional.get().items.forEach((slot, stack)->builder.add(stack.locale()));
    item.setData(DataComponentTypes.BUNDLE_CONTENTS, builder);

    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   */
  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final BundleContents contents = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
    if(contents == null) {
      return serialized;
    }

    int i = 0;
    for(final ItemStack stack : contents.contents()) {

      items.put(i, new PaperItemStack().of(stack));
      i++;
    }

    serialized.applyComponent(this);
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

    return true;
  }
}