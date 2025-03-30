package net.tnemc.item.bukkit.platform;
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

import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.bukkit.platform.impl.BukkitBundleComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitContainerComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitCustomNameComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitDamageComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitEnchantmentsComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitItemModelComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitLoreComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitMaxStackSizeComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitProfileComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitShulkerColorComponent;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.providers.ItemProvider;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.Locale;
import java.util.Optional;

/**
 * BukkitItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitItemPlatform extends ItemPlatform<BukkitItemStack, ItemStack> {

  public static final BukkitItemPlatform PLATFORM = new BukkitItemPlatform();

  private BukkitItemPlatform() {

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

    registerConversions();

    //bukkit base implementation.
    addMulti(new BukkitBundleComponent());
    addMulti(new BukkitContainerComponent());
    addMulti(new BukkitCustomNameComponent());
    addMulti(new BukkitDamageComponent());
    addMulti(new BukkitEnchantmentsComponent());
    addMulti(new BukkitItemModelComponent());
    addMulti(new BukkitLoreComponent());
    addMulti(new BukkitMaxStackSizeComponent());
    addMulti(new BukkitProfileComponent());
    addMulti(new BukkitShulkerColorComponent());

  }

  /**
   * Retrieves the default provider for the item stack comparison.
   *
   * @return the default provider for the item stack comparison.
   */
  @Override
  public @NotNull ItemProvider<ItemStack> defaultProvider() {

    return null;
  }

  /**
   * Retrieves the identifier of the default provider for the item stack comparison.
   *
   * @return The identifier of the default provider for the item stack comparison.
   */
  @Override
  public @NotNull String defaultProviderIdentifier() {

    return "";
  }

  @SuppressWarnings({"deprecation", "UnstableApiUsage" })
  private void registerConversions() {

    //RegisterConversion for EquipmentSlot
    converter.registerConversion(String.class, EquipmentSlot.class, input -> {
      switch (input.toUpperCase()) {
        case "HAND": return EquipmentSlot.HAND;
        case "OFF_HAND": return EquipmentSlot.OFF_HAND;
        case "FEET": return EquipmentSlot.FEET;
        case "LEGS": return EquipmentSlot.LEGS;
        case "CHEST": return EquipmentSlot.CHEST;
        case "HEAD": return EquipmentSlot.HEAD;
        case "BODY": return EquipmentSlot.BODY;
        default: return EquipmentSlot.HAND;
      }
    });

    converter.registerConversion(EquipmentSlot.class, String.class, input->switch(input) {
      case HAND -> "HAND";
      case OFF_HAND -> "OFF_HAND";
      case FEET -> "FEET";
      case LEGS -> "LEGS";
      case CHEST -> "CHEST";
      case HEAD -> "HEAD";
      case BODY -> "BODY";
      case SADDLE -> "SADDLE";
      default -> "HAND";
    });

    //RegisterConversion for EquipmentSlotGroup
    converter.registerConversion(String.class, EquipmentSlotGroup.class, input -> {
      final EquipmentSlotGroup group = EquipmentSlotGroup.getByName(input);
      if(group == null) {
        throw new IllegalArgumentException("Unknown input: " + input);
      }
      return group;
    });

    converter.registerConversion(EquipmentSlotGroup.class, String.class, EquipmentSlotGroup::toString);

    //Register Conversions for AttributeModifier
    converter.registerConversion(String.class, AttributeModifier.Operation.class, input->switch(input.toLowerCase()) {
      case "add_value" -> AttributeModifier.Operation.ADD_NUMBER;
      case "add_multiplied_base" -> AttributeModifier.Operation.ADD_SCALAR;
      case "add_multiplied_total" -> AttributeModifier.Operation.MULTIPLY_SCALAR_1;
      default -> throw new IllegalArgumentException("Unknown input: " + input);
    });

    converter.registerConversion(AttributeModifier.Operation.class, String.class, input ->switch(input) {
      case ADD_NUMBER -> "add_value";
      case ADD_SCALAR -> "add_multiplied_base";
      case MULTIPLY_SCALAR_1 -> "add_multiplied_total";
    });

    //Register conversions for DyeColor
    converter.registerConversion(String.class, DyeColor.class, input ->switch(input.toLowerCase(Locale.ROOT)) {
      case "white" -> DyeColor.WHITE;
      case "orange" -> DyeColor.ORANGE;
      case "magenta" -> DyeColor.MAGENTA;
      case "light_blue" -> DyeColor.LIGHT_BLUE;
      case "yellow" -> DyeColor.YELLOW;
      case "lime" -> DyeColor.LIME;
      case "pink" -> DyeColor.PINK;
      case "gray" -> DyeColor.GRAY;
      case "light_gray" -> DyeColor.LIGHT_GRAY;
      case "cyan" -> DyeColor.CYAN;
      case "purple" -> DyeColor.PURPLE;
      case "blue" -> DyeColor.BLUE;
      case "brown" -> DyeColor.BROWN;
      case "green" -> DyeColor.GREEN;
      case "red" -> DyeColor.RED;
      case "black" -> DyeColor.BLACK;
      default -> throw new IllegalArgumentException("Unknown DyeColor: " + input);
    });

    converter.registerConversion(DyeColor.class, String.class, input ->switch(input) {
      case WHITE -> "white";
      case ORANGE -> "orange";
      case MAGENTA -> "magenta";
      case LIGHT_BLUE -> "light_blue";
      case YELLOW -> "yellow";
      case LIME -> "lime";
      case PINK -> "pink";
      case GRAY -> "gray";
      case LIGHT_GRAY -> "light_gray";
      case CYAN -> "cyan";
      case PURPLE -> "purple";
      case BLUE -> "blue";
      case BROWN -> "brown";
      case GREEN -> "green";
      case RED -> "red";
      case BLACK -> "black";
    });

    //Register Conversions for PatternType, which will be dependent on versions
    //We'll separate the legacy find methods from the modern ones in order to maintain one component
    // class for both.
    if(VersionUtil.isOneTwentyOne(version())) {

      converter.registerConversion(String.class, PatternType.class, input->{

        final NamespacedKey key = NamespacedKey.fromString(input);
        if(key != null) {

          final PatternType patternType = Registry.BANNER_PATTERN.get(key);
          if(patternType != null) {

            return patternType;
          }
        }
        throw new IllegalArgumentException("Unknown PatternType: " + input);
      });

      if(VersionUtil.isOneTwentyOneFour(version())) {

        converter.registerConversion(PatternType.class, String.class, input->{

          final NamespacedKey key = input.getKeyOrThrow();

          return key.toString();
        });
      } else {

        converter.registerConversion(PatternType.class, String.class, input->{

          final NamespacedKey key = input.getKey();

          return key.toString();
        });
      }

    } else {

      converter.registerConversion(String.class, PatternType.class, PatternType::valueOf);

      converter.registerConversion(PatternType.class, String.class, PatternType::getIdentifier);
    }

    //Register Conversions for Enchantment, which will be dependent on versions
    //We'll separate the legacy find methods from the modern ones in order to maintain one component
    // class for both.
    if(VersionUtil.isOneTwentyOneFour(version())) {

      converter.registerConversion(String.class, Enchantment.class, (final String input)->{

        final NamespacedKey key = NamespacedKey.fromString(input);
        if(key != null) {

          return Registry.ENCHANTMENT.getOrThrow(key);
        }
        throw new IllegalArgumentException("Unknown Enchantment: " + input);
      });

      converter.registerConversion(Enchantment.class, String.class, (final Enchantment input)->input.getKeyOrThrow().toString());

    } else if(VersionUtil.isOneThirteen(version())) {
      converter.registerConversion(String.class, Enchantment.class, (final String input)->{

        final Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(input));
        if(enchantment == null) {

          throw new IllegalArgumentException("Unknown Enchantment: " + input);
        }
        return enchantment;
      });

      converter.registerConversion(Enchantment.class, String.class, (final Enchantment input)->input.getKey().getKey());
    } else {

      converter.registerConversion(String.class, Enchantment.class, (final String input)->{

        final Enchantment enchantment = Enchantment.getByName(input);
        if(enchantment == null) {

          throw new IllegalArgumentException("Unknown Enchantment: " + input);
        }
        return enchantment;
      });

      converter.registerConversion(Enchantment.class, String.class, Enchantment::getName);
    }

    //Register Conversions for Trim Material, which will be dependent on versions
    //We'll separate the legacy find methods from the modern ones in order to maintain one component
    // class for both.
    if(VersionUtil.isOneTwentyOneFour(version())) {

      converter.registerConversion(TrimMaterial.class, String.class, input->input.getKeyOrThrow().toString());

      converter.registerConversion(String.class, TrimMaterial.class, input->{
        final NamespacedKey key = NamespacedKey.fromString(input);
        if(key != null) {

          return Registry.TRIM_MATERIAL.getOrThrow(key);
        }
        throw new IllegalArgumentException("Unknown TrimMaterial: " + input);
      });

    } else if(VersionUtil.isOneTwenty(version())) {

      converter.registerConversion(TrimMaterial.class, String.class, input->input.getKey().toString());

      converter.registerConversion(String.class, TrimMaterial.class, input->{
        final NamespacedKey key = NamespacedKey.fromString(input);
        if(key != null) {

          return Registry.TRIM_MATERIAL.get(key);
        }
        throw new IllegalArgumentException("Unknown TrimMaterial: " + input);
      });
    }

    //Register Conversions for NamespacedKey, which will be dependent on versions
    //We'll separate the legacy find methods from the modern ones in order to maintain one component
    // class for both.
    converter.registerConversion(ItemRarity.class, String.class, input->switch(input) {
      case EPIC -> "epic";
      case RARE -> "rare";
      case UNCOMMON -> "uncommon";
      default -> "common";
    });

    converter.registerConversion(String.class, ItemRarity.class, input->switch(input.toLowerCase()) {
      case "epic" -> ItemRarity.EPIC;
      case "rare" -> ItemRarity.RARE;
      case "uncommon" -> ItemRarity.UNCOMMON;
      default -> ItemRarity.COMMON;
    });

    //Register Conversions for Trim Pattern, which will be dependent on versions
    //We'll separate the legacy find methods from the modern ones in order to maintain one component
    // class for both.
    if(VersionUtil.isOneTwentyOneFour(version())) {

      converter.registerConversion(TrimPattern.class, String.class, input->input.getKeyOrThrow().toString());

      converter.registerConversion(String.class, TrimPattern.class, input->{
        final NamespacedKey key = NamespacedKey.fromString(input);
        if(key != null) {

          return Registry.TRIM_PATTERN.getOrThrow(key);
        }
        throw new IllegalArgumentException("Unknown TrimPattern: " + input);
      });

    } else if(VersionUtil.isOneTwenty(version())) {

      converter.registerConversion(TrimPattern.class, String.class, input->input.getKey().toString());

      converter.registerConversion(String.class, TrimPattern.class, input->{
        final NamespacedKey key = NamespacedKey.fromString(input);
        if(key != null) {

          return Registry.TRIM_PATTERN.get(key);
        }
        throw new IllegalArgumentException("Unknown TrimPattern: " + input);
      });
    }

    if(VersionUtil.isOneTwentyOneFour(version())) {

      converter.registerConversion(PotionEffectType.class, String.class, input->input.getKeyOrThrow().toString());
      converter.registerConversion(String.class, PotionEffectType.class, input->{
        final NamespacedKey key = NamespacedKey.fromString(input);
        if(key != null) {

          return Registry.EFFECT.getOrThrow(key);
        }
        throw new IllegalArgumentException("Unknown PotionEffectType: " + input);
      });
    } else {

      converter.registerConversion(PotionEffectType.class, String.class, PotionEffectType::getName);
      converter.registerConversion(String.class, PotionEffectType.class, input->{

        final PotionEffectType type = PotionEffectType.getByName(input);
        if(type != null) {

          return type;
        }
        throw new IllegalArgumentException("Unknown PotionEffectType: " + input);
      });
    }
  }

  /**
   * Initializes and returns an AbstractItemStack object by deserializing the provided JSON object.
   *
   * @param object the JSON object to deserialize
   *
   * @return an initialized AbstractItemStack object
   */
  @Override
  public Optional<BukkitItemStack> initSerialized(final JSONObject object) {

    try {
      return Optional.ofNullable(new BukkitItemStack().of(object));
    } catch(final ParseException e) {
      return Optional.empty();
    }
  }
}