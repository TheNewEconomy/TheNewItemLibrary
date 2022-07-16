package net.tnemc.item;

/*
 * The New Economy Minecraft Server Plugin
 *
 * Copyright (C) 2022 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.item.attribute.SerialAttributeOperation;
import net.tnemc.item.attribute.SerialAttributeSlot;
import net.tnemc.item.data.BukkitAxolotlData;
import net.tnemc.item.data.BukkitBannerData;
import net.tnemc.item.data.BukkitBookData;
import net.tnemc.item.data.BukkitBundleData;
import net.tnemc.item.data.BukkitCompassData;
import net.tnemc.item.data.BukkitEnchantData;
import net.tnemc.item.data.BukkitKnowledgeBookData;
import net.tnemc.item.data.BukkitLeatherData;
import net.tnemc.item.data.BukkitMapData;
import net.tnemc.item.data.BukkitPotionData;
import net.tnemc.item.data.BukkitSkullData;
import net.tnemc.item.data.BukkitSuspiciousStewData;
import net.tnemc.item.data.BukkitTropicalFishData;
import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

import java.util.Optional;

public class ParsingUtil {

  public static SerialAttributeSlot attributeSlot(final EquipmentSlot slot) {
    if(slot == null) return null;

    switch(slot) {

      case OFF_HAND:
        return SerialAttributeSlot.OFF_HAND;

      case HEAD:
        return SerialAttributeSlot.HEAD;

      case CHEST:
        return SerialAttributeSlot.CHEST;

      case LEGS:
        return SerialAttributeSlot.LEGS;

      case FEET:
        return SerialAttributeSlot.FEET;

      case HAND:
      default:
        return SerialAttributeSlot.MAIN_HAND;
    }
  }

  public static EquipmentSlot attributeSlot(final SerialAttributeSlot slot) {
    if(slot == null) return null;

    switch(slot) {

      case OFF_HAND:
        return EquipmentSlot.OFF_HAND;

      case HEAD:
        return EquipmentSlot.HEAD;

      case CHEST:
        return EquipmentSlot.CHEST;

      case LEGS:
        return EquipmentSlot.LEGS;

      case FEET:
        return EquipmentSlot.FEET;

      case MAIN_HAND:
      default:
        return EquipmentSlot.HAND;
    }
  }

  public static SerialAttributeOperation attributeOperation(final AttributeModifier.Operation operation) {
    switch(operation) {

      case ADD_SCALAR:
        return SerialAttributeOperation.MULTIPLY_BASE;

      case MULTIPLY_SCALAR_1:
        return SerialAttributeOperation.MULTIPLY;

      case ADD_NUMBER:
      default:
        return SerialAttributeOperation.ADD;
    }
  }

  public static AttributeModifier.Operation attributeOperation(final SerialAttributeOperation operation) {
    switch(operation) {

      case MULTIPLY_BASE:
        return AttributeModifier.Operation.ADD_SCALAR;

      case MULTIPLY:
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;

      case ADD:
      default:
        return AttributeModifier.Operation.ADD_NUMBER;
    }
  }
  
  public static Optional<SerialItemData<ItemStack>> parseMeta(final ItemStack stack) {

    SerialItemData<ItemStack> data = null;

    if(stack.hasItemMeta()) {
      final ItemMeta meta = stack.getItemMeta();
      if(meta instanceof AxolotlBucketMeta) {
        data = new BukkitAxolotlData();
      } else if(meta instanceof BookMeta) {
        data = new BukkitBookData();
      } else if(meta instanceof BannerMeta) {
        data = new BukkitBannerData();
      } else if(meta instanceof BundleMeta) {
        data = new BukkitBundleData();
      } else if(meta instanceof CompassMeta) {
        data = new BukkitCompassData();
      } else if(meta instanceof EnchantmentStorageMeta) {
        data = new BukkitEnchantData();
      } else if(meta instanceof KnowledgeBookMeta) {
        data = new BukkitKnowledgeBookData();
      } else if(meta instanceof LeatherArmorMeta) {
        data = new BukkitLeatherData();
      } else if(meta instanceof MapMeta) {
        data = new BukkitMapData();
      } else if(meta instanceof PotionMeta) {
        data = new BukkitPotionData();
      } else if(meta instanceof SkullMeta) {
        data = new BukkitSkullData();
      } else if(meta instanceof SuspiciousStewMeta) {
        data = new BukkitSuspiciousStewData();
      } else if(meta instanceof TropicalFishBucketMeta) {
        data = new BukkitTropicalFishData();
      }

      if(data != null) {
        data.of(stack);
      }
    }
    return Optional.ofNullable(data);
  }
}