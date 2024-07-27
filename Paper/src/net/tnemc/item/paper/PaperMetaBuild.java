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
import net.tnemc.item.bukkitbase.data.BukkitBannerData;
import net.tnemc.item.bukkitbase.data.BukkitBookData;
import net.tnemc.item.bukkitbase.data.BukkitCompassData;
import net.tnemc.item.bukkitbase.data.BukkitDamageData;
import net.tnemc.item.bukkitbase.data.BukkitEnchantData;
import net.tnemc.item.bukkitbase.data.BukkitFireworkData;
import net.tnemc.item.bukkitbase.data.BukkitFireworkEffectData;
import net.tnemc.item.bukkitbase.data.BukkitKnowledgeBookData;
import net.tnemc.item.bukkitbase.data.BukkitLeatherData;
import net.tnemc.item.bukkitbase.data.BukkitMapData;
import net.tnemc.item.bukkitbase.data.BukkitPotionData;
import net.tnemc.item.bukkitbase.data.BukkitRepairableData;
import net.tnemc.item.bukkitbase.data.BukkitSkullData;
import net.tnemc.item.bukkitbase.data.BukkitSpawnEggData;
import net.tnemc.item.bukkitbase.data.BukkitSuspiciousStewData;
import net.tnemc.item.bukkitbase.data.BukkitTropicalFishData;
import net.tnemc.item.bukkitbase.data.BukkitWritableBookData;
import net.tnemc.item.paper.data.PaperArmourData;
import net.tnemc.item.paper.data.PaperBundleData;
import net.tnemc.item.paper.data.PaperColourArmourData;
import net.tnemc.item.paper.data.PaperCrossbowData;
import net.tnemc.item.paper.data.PaperInstrumentData;
import net.tnemc.item.paper.data.block.PaperShulkerData;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.bukkit.inventory.meta.WritableBookMeta;

import java.util.Optional;

/**
 * MetaBuild
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class PaperMetaBuild {

  public static Optional<SerialItemData<ItemStack>> parseMeta(final ItemStack stack) {

    final String currentVersion = Bukkit.getServer().getBukkitVersion().split("-")[0];

    if(stack.hasItemMeta()) {
      final ItemMeta meta = stack.getItemMeta();

      if(VersionUtil.isOneTwentyOne(currentVersion)) {
        if(meta instanceof WritableBookMeta) {
          return Optional.of(new BukkitWritableBookData());
        }
      }

      if(meta instanceof BannerMeta) {

        return Optional.of(new BukkitBannerData());

      } else if(meta instanceof BookMeta) {

        return Optional.of(new BukkitBookData());

      } else if(meta instanceof EnchantmentStorageMeta) {

        return Optional.of(new BukkitEnchantData());

      } else if(meta instanceof FireworkEffectMeta) {

        return Optional.of(new BukkitFireworkEffectData());

      } else if(meta instanceof FireworkMeta) {

        return Optional.of(new BukkitFireworkData());

      } else if(meta instanceof LeatherArmorMeta) {

        return Optional.of(new BukkitLeatherData());

      } else if(meta instanceof MapMeta) {

        return Optional.of(new BukkitMapData());

      } else if(meta instanceof PotionMeta) {

        return Optional.of(new BukkitPotionData());

      } else if(meta instanceof SkullMeta) {

        return Optional.of(new BukkitSkullData());

      } else if(meta instanceof BlockStateMeta) {
        if(VersionUtil.isOneEleven(currentVersion) && ((BlockStateMeta)meta).getBlockState() instanceof ShulkerBox) {

          return Optional.of(new PaperShulkerData());
        }
      }

      if(VersionUtil.isOneEleven(currentVersion)) {
        if(meta instanceof SpawnEggMeta) {

          return Optional.of(new BukkitSpawnEggData());
        }
      }

      if(VersionUtil.isOneTwelve(currentVersion)) {
        if(meta instanceof KnowledgeBookMeta) {

          return Optional.of(new BukkitKnowledgeBookData());
        }
      }

      if(VersionUtil.isOneThirteen(currentVersion)) {
        if(meta instanceof Damageable) {

          return Optional.of(new BukkitDamageData());
        } else if(meta instanceof Repairable) {

          return Optional.of(new BukkitRepairableData());
        } else if(meta instanceof TropicalFishBucketMeta) {

          return Optional.of(new BukkitTropicalFishData());
        }
      }

      if(VersionUtil.isOneFourteen(currentVersion)) {
        if(meta instanceof CrossbowMeta) {

          return Optional.of(new PaperCrossbowData());
        }
      }

      if(VersionUtil.isOneFifteen(currentVersion)) {
        if(meta instanceof SuspiciousStewMeta) {

          return Optional.of(new BukkitSuspiciousStewData());
        }
      }

      if(VersionUtil.isOneSeventeen(currentVersion)) {
        if(meta instanceof AxolotlBucketMeta) {

          return Optional.of(new BukkitAxolotlData());
        } else if(meta instanceof BundleMeta) {

          return Optional.of(new PaperBundleData());
        } else if(meta instanceof CompassMeta) {

          return Optional.of(new BukkitCompassData());
        }
      }

      if(VersionUtil.isOneTwenty(currentVersion)) {

        if(meta instanceof ColorableArmorMeta) {

          return Optional.of(new PaperColourArmourData());

        } else if(meta instanceof MusicInstrumentMeta) {

          return Optional.of(new PaperInstrumentData());

        } else if(meta instanceof ArmorMeta) {

          return Optional.of(new PaperArmourData());
        }
      }
    }
    return Optional.empty();
  }
}
