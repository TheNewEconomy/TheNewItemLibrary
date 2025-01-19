package net.tnemc.item.bukkit;
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
import net.tnemc.item.bukkit.data.BukkitArmourData;
import net.tnemc.item.bukkit.data.BukkitBannerModernData;
import net.tnemc.item.bukkit.data.BukkitBundleData;
import net.tnemc.item.bukkit.data.BukkitColourArmourData;
import net.tnemc.item.bukkit.data.BukkitCrossbowData;
import net.tnemc.item.bukkit.data.BukkitInstrumentData;
import net.tnemc.item.bukkit.data.BukkitShieldData;
import net.tnemc.item.bukkit.data.block.BukkitShulkerData;
import net.tnemc.item.bukkit.platform.implold.BukkitItemAttribute;
import net.tnemc.item.bukkit.platform.implold.BukkitItemDisplay;
import net.tnemc.item.bukkit.platform.implold.BukkitItemEnchant;
import net.tnemc.item.bukkit.platform.implold.BukkitItemEquip;
import net.tnemc.item.bukkit.platform.implold.BukkitItemFood;
import net.tnemc.item.bukkit.platform.implold.BukkitItemJuke;
import net.tnemc.item.bukkit.platform.implold.BukkitItemLore;
import net.tnemc.item.bukkit.platform.implold.BukkitItemMaterial;
import net.tnemc.item.bukkit.platform.implold.BukkitItemProfile;
import net.tnemc.item.bukkit.platform.implold.BukkitItemSerialData;
import net.tnemc.item.bukkit.platform.implold.BukkitItemTool;
import net.tnemc.item.bukkit.platform.implold.BukkitItemUse;
import net.tnemc.item.bukkitbase.ParsingUtil;
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
import net.tnemc.item.bukkitbase.data.BukkitOminousBottleData;
import net.tnemc.item.bukkitbase.data.BukkitPotionData;
import net.tnemc.item.bukkitbase.data.BukkitRepairableData;
import net.tnemc.item.bukkitbase.data.BukkitSkullData;
import net.tnemc.item.bukkitbase.data.BukkitSpawnEggData;
import net.tnemc.item.bukkitbase.data.BukkitSuspiciousStewData;
import net.tnemc.item.bukkitbase.data.BukkitTropicalFishData;
import net.tnemc.item.bukkitbase.data.BukkitWritableBookData;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemDamage;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemEnchantGlint;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemFlag;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemGlider;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemMaxStack;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemModel;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemModelData;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemRarity;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemTooltip;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemTooltipStyle;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemUnbreakable;
import net.tnemc.item.platform.ItemPlatform;
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
import org.bukkit.inventory.meta.OminousBottleMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.ShieldMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.bukkit.inventory.meta.WritableBookMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.Optional;

/**
 * BukkitItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitItemRevampPlatform extends ItemPlatform<BukkitItemStackRevamp, ItemStack> {

  public static final BukkitItemRevampPlatform PLATFORM = new BukkitItemRevampPlatform();

  private BukkitItemRevampPlatform() {

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
    addMulti(new BukkitItemDamage<>());
    addMulti(new BukkitItemEnchantGlint<>());
    addMulti(new BukkitItemFlag<>());
    addMulti(new BukkitItemGlider<>());
    addMulti(new BukkitItemMaxStack<>());
    addMulti(new BukkitItemModel<>());
    addMulti(new BukkitItemModelData<>());
    addMulti(new BukkitItemRarity<>());
    addMulti(new BukkitItemTooltip<>());
    addMulti(new BukkitItemTooltipStyle<>());
    addMulti(new BukkitItemUnbreakable<>());

    //Bukkit-specific
    addMulti(new BukkitItemSerialData());
    addMulti(new BukkitItemAttribute());
    addMulti(new BukkitItemDisplay());
    addMulti(new BukkitItemEnchant());
    addMulti(new BukkitItemEquip());
    addMulti(new BukkitItemFood());
    addMulti(new BukkitItemJuke());
    addMulti(new BukkitItemLore());
    addMulti(new BukkitItemMaterial());
    addMulti(new BukkitItemProfile());
    addMulti(new BukkitItemTool());
    addMulti(new BukkitItemUse());
  }

  /**
   * Initializes and returns an AbstractItemStack object by deserializing the provided JSON object.
   *
   * @param object the JSON object to deserialize
   *
   * @return an initialized AbstractItemStack object
   */
  @Override
  public Optional<BukkitItemStackRevamp> initSerialized(final JSONObject object) {

    try {
      return Optional.ofNullable(new BukkitItemStackRevamp().of(object));
    } catch(final ParseException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<SerialItemData<ItemStack>> parseMeta(final ItemStack stack) {

    final String currentVersion = Bukkit.getServer().getBukkitVersion().split("-")[0];

    if(stack.hasItemMeta()) {
      final ItemMeta meta = stack.getItemMeta();

      if(VersionUtil.isOneTwentyOne(currentVersion)) {
        if(meta instanceof ShieldMeta) {
          return Optional.of(new BukkitShieldData());
        } else if(meta instanceof WritableBookMeta) {
          return Optional.of(new BukkitWritableBookData());
        } else if(meta instanceof BannerMeta) {
          return Optional.of(new BukkitBannerModernData());
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

          return Optional.of(new BukkitShulkerData());
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

          return Optional.of(new BukkitCrossbowData());
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

          return Optional.of(new BukkitBundleData());
        } else if(meta instanceof CompassMeta) {

          return Optional.of(new BukkitCompassData());
        }
      }

      if(VersionUtil.isOneTwenty(currentVersion)) {

        if(meta instanceof ColorableArmorMeta) {

          return Optional.of(new BukkitColourArmourData());

        } else if(meta instanceof MusicInstrumentMeta) {

          return Optional.of(new BukkitInstrumentData());

        } else if(meta instanceof ArmorMeta) {

          return Optional.of(new BukkitArmourData());
        }
      }
    }
    return Optional.empty();
  }
}