package net.tnemc.item.bukkit.platform.impl;
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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.component.impl.AttributeModifiersComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Optional;

/**
 * BukkitAttributeModifiersComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitAttributeModifiersComponent extends AttributeModifiersComponent<BukkitItemStack, ItemStack> {

  /**
   * Constructor for AttributeModifiersComponent. Initializes an empty list of AttributeModifiers.
   */
  public BukkitAttributeModifiersComponent() {

  }

  /**
   * Constructor for AttributeModifiersComponent. Initializes the component with a list of
   * AttributeModifiers and a boolean flag to show in tooltip.
   *
   * @param modifiers The list of AttributeModifiers to associate with this component.
   */
  public BukkitAttributeModifiersComponent(final List<net.tnemc.item.component.helper.AttributeModifier> modifiers) {

    super(modifiers);
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
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
   */
  @Override
  public ItemStack apply(final BukkitItemStack serialized, final ItemStack item) {

    final ItemMeta meta = item.getItemMeta();
    final Optional<AttributeModifiersComponent<AbstractItemStack<ItemStack>, ItemStack>> componentOptional = serialized.attributeModifiers();
    if(meta != null && componentOptional.isPresent()) {

      for(final net.tnemc.item.component.helper.AttributeModifier attribute : componentOptional.get().modifiers()) {

        final AttributeModifier.Operation operation = BukkitItemPlatform.PLATFORM.converter().convert(attribute.getOperation(), AttributeModifier.Operation.class);
        final EquipmentSlotGroup slot = BukkitItemPlatform.PLATFORM.converter().convert(attribute.getSlot(), EquipmentSlotGroup.class);
        final AttributeModifier attr = new AttributeModifier(NamespacedKey.fromString(attribute.getType()),
                                                             attribute.getAmount(),
                                                             operation,
                                                             slot);

        meta.addAttributeModifier(Attribute.valueOf(attribute.getId()), attr);
      }
    }

    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   */
  @Override
  public BukkitItemStack serialize(final ItemStack item, final BukkitItemStack serialized) {

    return null;
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