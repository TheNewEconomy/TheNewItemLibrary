package net.tnemc.item.paper.platform;
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

import net.tnemc.item.SerialItemData;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.platform.ItemPlatform;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.Optional;

/**
 * PaperItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class PaperItemPlatform extends ItemPlatform<PaperItemStack, ItemStack> {

  public static final PaperItemPlatform PLATFORM = new PaperItemPlatform();

  private PaperItemPlatform() {

    super();
  }

  /**
   * @return the version that is being used currently
   */
  @Override
  public String version() {

    return ParsingUtil.version();
  }

  @Override
  public void addDefaults() {

    //bukkit base implementation.

    //Paper-specific
  }

  /**
   * Initializes and returns an AbstractItemStack object by deserializing the provided JSON object.
   *
   * @param object the JSON object to deserialize
   *
   * @return an initialized AbstractItemStack object
   */
  @Override
  public Optional<PaperItemStack> initSerialized(final JSONObject object) {

    try {
      return Optional.ofNullable(new PaperItemStack().of(object));
    } catch(final ParseException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<SerialItemData<ItemStack>> parseMeta(final ItemStack stack) {

    final String currentVersion = Bukkit.getServer().getBukkitVersion().split("-")[0];

    if(stack.hasItemMeta()) {
      /*final ItemMeta meta = stack.getItemMeta();

      if(VersionUtil.isOneTwentyOne(currentVersion)) {
        if(meta instanceof WritableBookMeta) {
          return Optional.of(new BukkitWritableBookData());
        } else if(meta instanceof BannerMeta) {
          return Optional.of(new PaperBannerModernData());
        } else if(meta instanceof OminousBottleMeta) {

          return Optional.of(new BukkitOminousBottleData());

        }
      }

      if(meta instanceof BannerMeta && !VersionUtil.isOneTwentyOne(currentVersion)) {

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
      }*/
    }
    return Optional.empty();
  }
}
