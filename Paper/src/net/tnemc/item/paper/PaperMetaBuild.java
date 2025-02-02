package net.tnemc.item.paper;
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

import net.tnemc.item.SerialItemData;
import net.tnemc.item.bukkitbase.data.BukkitAxolotlData;
import net.tnemc.item.bukkitbase.data.BukkitBookData;
import net.tnemc.item.bukkitbase.data.BukkitCompassData;
import net.tnemc.item.bukkitbase.data.BukkitEnchantData;
import net.tnemc.item.bukkitbase.data.BukkitFireworkData;
import net.tnemc.item.bukkitbase.data.BukkitFireworkEffectData;
import net.tnemc.item.bukkitbase.data.BukkitKnowledgeBookData;
import net.tnemc.item.bukkitbase.data.BukkitLeatherData;
import net.tnemc.item.bukkitbase.data.BukkitMapData;
import net.tnemc.item.bukkitbase.data.BukkitPotionData;
import net.tnemc.item.bukkitbase.data.BukkitRepairableMeta;
import net.tnemc.item.bukkitbase.data.BukkitSkullData;
import net.tnemc.item.bukkitbase.data.BukkitSuspiciousStewData;
import net.tnemc.item.bukkitbase.data.BukkitTropicalFishData;
import net.tnemc.item.paper.data.PaperBannerData;
import net.tnemc.item.paper.data.PaperBundleData;
import net.tnemc.item.paper.data.PaperCrossbowMeta;
import net.tnemc.item.paper.data.PaperShulkerData;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

import java.util.Optional;

/**
 * MetaBuild
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class PaperMetaBuild {

  public static Optional<SerialItemData<ItemStack>> parseMeta(final ItemStack stack) {

    SerialItemData<ItemStack> data = null;

    if(stack.hasItemMeta()) {
      final ItemMeta meta = stack.getItemMeta();
      if(meta instanceof AxolotlBucketMeta) {
        data = new BukkitAxolotlData();
      } else if(meta instanceof BannerMeta) {
        data = new PaperBannerData();
      } else if(meta instanceof BlockStateMeta) {
        if(((BlockStateMeta)meta).getBlockState() instanceof ShulkerBox) {
          data = new PaperShulkerData();
        }
      } else if(meta instanceof BookMeta) {
        data = new BukkitBookData();
      } else if(meta instanceof BundleMeta) {
        data = new PaperBundleData();
      } else if(meta instanceof CompassMeta) {
        data = new BukkitCompassData();
      } else if(meta instanceof CrossbowMeta) {
        data = new PaperCrossbowMeta();
      } else if(meta instanceof EnchantmentStorageMeta) {
        data = new BukkitEnchantData();
      } else if(meta instanceof FireworkEffectMeta) {
        data = new BukkitFireworkEffectData();
      } else if(meta instanceof FireworkMeta) {
        data = new BukkitFireworkData();
      } else if(meta instanceof KnowledgeBookMeta) {
        data = new BukkitKnowledgeBookData();
      } else if(meta instanceof LeatherArmorMeta) {
        data = new BukkitLeatherData();
      } else if(meta instanceof MapMeta) {
        data = new BukkitMapData();
      } else if(meta instanceof PotionMeta) {
        data = new BukkitPotionData();
      } else if(meta instanceof Repairable) {
        data = new BukkitRepairableMeta();
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
