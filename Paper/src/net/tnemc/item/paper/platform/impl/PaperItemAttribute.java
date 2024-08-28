package net.tnemc.item.paper.platform.impl;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.item.attribute.SerialAttribute;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.platform.impl.ItemAttribute;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

/**
 * PaperItemAttribute
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class PaperItemAttribute extends ItemAttribute<PaperItemStack, ItemStack> {
  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   */
  @Override
  public ItemStack apply(PaperItemStack serialized, ItemStack item) {

    final ItemMeta meta = item.getItemMeta();
    if(meta != null) {

      for(Map.Entry<String, SerialAttribute> entry : serialized.attributes().entrySet()) {

        final SerialAttribute attribute = entry.getValue();
        final AttributeModifier attr = new AttributeModifier(attribute.getIdentifier(),
                attribute.getName(),
                attribute.getAmount(),
                ParsingUtil.attributeOperation(attribute.getOperation()),
                ParsingUtil.attributeSlot(attribute.getSlot()));


        meta.addAttributeModifier(Attribute.valueOf(entry.getKey()), attr);
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
  public PaperItemStack deserialize(ItemStack item, PaperItemStack serialized) {

    final ItemMeta meta = item.getItemMeta();
    if(meta != null && meta.hasAttributeModifiers()) {

      for(final Map.Entry<Attribute, AttributeModifier> entry : meta.getAttributeModifiers().entries()) {

        final AttributeModifier attribute = entry.getValue();
        if(attribute != null) {

          final SerialAttribute attr = new SerialAttribute(attribute.getUniqueId(),
                  attribute.getName(),
                  attribute.getAmount(),
                  ParsingUtil.attributeOperation(attribute.getOperation()));

          if(attribute.getSlot() != null) {
            attr.setSlot(ParsingUtil.attributeSlot(attribute.getSlot()));
          }
          serialized.attributes().put(entry.getKey().name(), attr);
        }
      }
    }
    return serialized;
  }
}