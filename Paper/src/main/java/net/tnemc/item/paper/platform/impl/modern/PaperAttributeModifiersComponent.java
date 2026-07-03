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
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.component.helper.AttributeModifier;
import net.tnemc.item.component.impl.AttributeModifiersComponent;
import net.tnemc.item.component.impl.BundleComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * PaperOldBundleComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperAttributeModifiersComponent extends AttributeModifiersComponent<PaperItemStack, ItemStack> {

  /**
   * Constructor for AttributeModifiersComponent. Initializes an empty list of AttributeModifiers.
   *
   * @since 0.2.0.0
   */
  public PaperAttributeModifiersComponent() {

  }

  /**
   * Constructor for AttributeModifiersComponent. Initializes the component with a list of
   * AttributeModifiers and a boolean flag to show in tooltip.
   *
   * @param modifiers The list of AttributeModifiers to associate with this component.
   *
   * @since 0.2.0.0
   */
  public PaperAttributeModifiersComponent(final List<AttributeModifier> modifiers) {

    super(modifiers);
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

    return VersionUtil.isOneTwentyOneFour(version);
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
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperAttributeModifiersComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.itemAttributes();

    componentOptional.get().items.forEach((slot, stack)->builder.add(stack.provider().locale(serialized)));
    builder.addModifier()
    item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder);

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
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final ItemAttributeModifiers attributes = item.getData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
    if(attributes == null) {
      return serialized;
    }

    final PaperAttributeModifiersComponent component = (serialized.paperComponent(identifier()) instanceof final AttributeModifiersComponent<?, ?> getComponent)?
                                                       (PaperAttributeModifiersComponent)getComponent : new PaperAttributeModifiersComponent();

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

    return true;
  }
}