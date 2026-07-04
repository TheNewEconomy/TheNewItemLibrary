package net.tnemc.item.paper.platform.impl.modern;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import net.kyori.adventure.key.Key;
import net.tnemc.item.component.helper.AttributeModifier;
import net.tnemc.item.component.helper.EquipSlot;
import net.tnemc.item.component.impl.AttributeModifiersComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.Optional;

/**
 * PaperAttributeModifiersComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperAttributeModifiersComponent extends AttributeModifiersComponent<PaperItemStack, ItemStack> {

  public PaperAttributeModifiersComponent() {

  }

  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneFour(version);
  }

  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }

  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperAttributeModifiersComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.itemAttributes();

    for(final AttributeModifier modifier : componentOptional.get().modifiers()) {
      final org.bukkit.attribute.AttributeModifier.Operation operation = PaperItemPlatform.instance().converter().convert(modifier.getOperation(), org.bukkit.attribute.AttributeModifier.Operation.class);
      final EquipmentSlotGroup slot = PaperItemPlatform.instance().converter().convert(modifier.getSlot(), EquipmentSlotGroup.class);
      final org.bukkit.attribute.AttributeModifier attr = new org.bukkit.attribute.AttributeModifier(NamespacedKey.fromString(modifier.getType()),
                                                                                                     modifier.getAmount(),
                                                                                                     operation,
                                                                                                     slot);
      final Attribute attribute = PaperItemPlatform.instance().converter().convert(modifier.getType(), Attribute.class);

      builder.addModifier(attribute, attr);
    }

    item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final ItemAttributeModifiers modifiers = item.getData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
    if(modifiers == null) {
      return serialized;
    }

    final PaperAttributeModifiersComponent component = (serialized.paperComponent(identifier()) instanceof final AttributeModifiersComponent<?, ?> getComponent)?
                                                       (PaperAttributeModifiersComponent)getComponent : new PaperAttributeModifiersComponent();

    component.modifiers().clear();

    modifiers.modifiers().forEach(entry->{
      final AttributeModifier modifier = new AttributeModifier(
              PaperItemPlatform.instance().converter().convert(entry.attribute(), String.class),
              entry.modifier().getKey().asString(),
              entry.modifier().getOperation().name().toLowerCase(Locale.ROOT)
      );

      modifier.setAmount(entry.modifier().getAmount());
      modifier.setSlot(PaperItemPlatform.instance().converter().convert(entry.modifier().getSlotGroup(), EquipSlot.class));
      component.modifiers().add(modifier);
    });

    serialized.applyComponent(component);
    return serialized;
  }
}