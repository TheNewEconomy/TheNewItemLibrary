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

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.bukkitbase.platform.providers.ItemAdderProvider;
import net.tnemc.item.bukkitbase.platform.providers.MMOItemProvider;
import net.tnemc.item.bukkitbase.platform.providers.NexoProvider;
import net.tnemc.item.bukkitbase.platform.providers.NovaProvider;
import net.tnemc.item.bukkitbase.platform.providers.OraxenProvider;
import net.tnemc.item.bukkitbase.platform.providers.SlimefunProvider;
import net.tnemc.item.paper.PaperCalculationsProvider;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.VanillaProvider;
import net.tnemc.item.paper.platform.impl.modern.PaperBundleComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperContainerComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperCustomNameComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperDamageComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperEnchantmentsComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperItemModelComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperItemNameComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperLoreComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperMaxStackComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperModelDataComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperProfileComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperShulkerColorComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldModelDataLegacyComponent;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.providers.ItemProvider;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.Inventory;
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
 * PaperItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class PaperItemPlatform extends ItemPlatform<PaperItemStack, ItemStack, Inventory> {

  private static volatile PaperItemPlatform instance;

  //public static final PaperItemPlatform PLATFORM = new PaperItemPlatform();

  protected final VanillaProvider defaultProvider = new VanillaProvider();
  protected final PaperCalculationsProvider calculationsProvider = new PaperCalculationsProvider();

  private PaperItemPlatform() {

    super();
  }

  @Override
  public PaperItemStack createStack(final String material) {
    return new PaperItemStack().of(material, 1);
  }

  public static PaperItemPlatform instance() {

    final PaperItemPlatform result = instance;
    if(result != null) {
      return result;
    }

    synchronized(PaperItemPlatform.class) {

      if(instance == null) {

        instance = new PaperItemPlatform();
        instance.addDefaults();
      }
      return instance;
    }
  }

  /**
   * @return the version that is being used currently
   * @since 0.2.0.0
   */
  @Override
  public String version() {
    return Bukkit.getServer().getBukkitVersion().split("-")[0];
  }

  @Override
  public void addDefaults() {

    registerConversions();

    addMulti(new PaperBundleComponent());
    addMulti(new PaperContainerComponent());
    addMulti(new PaperCustomNameComponent());
    addMulti(new PaperDamageComponent());
    addMulti(new PaperEnchantmentsComponent());
    addMulti(new PaperItemModelComponent());
    addMulti(new PaperItemNameComponent());
    addMulti(new PaperLoreComponent());
    addMulti(new PaperMaxStackComponent());
    addMulti(new PaperModelDataComponent());
    addMulti(new PaperOldModelDataLegacyComponent());
    addMulti(new PaperProfileComponent());
    addMulti(new PaperShulkerColorComponent());


    if(Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")) {
      addItemProvider(new ItemAdderProvider());
    }

    if(Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
      addItemProvider(new MMOItemProvider());
    }

    if(Bukkit.getPluginManager().isPluginEnabled("Nexo")) {

      System.out.println("Adding nexo provider");
      addItemProvider(new NexoProvider());
    }

    if(Bukkit.getPluginManager().isPluginEnabled("Nova")) {
      addItemProvider(new NovaProvider());
    }

    if(Bukkit.getPluginManager().isPluginEnabled("Oraxen")) {
      addItemProvider(new OraxenProvider());
    }

    if(Bukkit.getPluginManager().isPluginEnabled("Slimefun")) {
      addItemProvider(new SlimefunProvider());
    }
    addItemProvider(defaultProvider);
  }

  /**
   * Retrieves the default provider for the item stack comparison.
   *
   * @return the default provider for the item stack comparison.
   * @since 0.2.0.0
   */
  @Override
  public @NotNull ItemProvider<ItemStack> defaultProvider() {

    return defaultProvider;
  }

  /**
   * Retrieves the identifier of the default provider for the item stack comparison.
   *
   * @return The identifier of the default provider for the item stack comparison.
   * @since 0.2.0.0
   */
  @Override
  public @NotNull String defaultProviderIdentifier() {

    return defaultProvider.identifier();
  }

  @Override
  public PaperCalculationsProvider calculations() {
    return calculationsProvider;
  }

  /**
   * Converts the given locale stack to an instance of {@link AbstractItemStack}
   *
   * @param locale the locale to convert
   *
   * @return the converted locale of type I
   * @since 0.2.0.0
   */
  @Override
  public PaperItemStack locale(final ItemStack locale) {

    return new PaperItemStack().of(locale);
  }

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

          final NamespacedKey key = input.getKey();

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

      converter.registerConversion(Enchantment.class, String.class, (final Enchantment input)->input.getKey().toString());

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

      converter.registerConversion(TrimMaterial.class, String.class, input->{

        final NamespacedKey key = RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_MATERIAL).getKey(input);
        if(key != null) {

          return key.asString();
        }

        throw new IllegalArgumentException("Unknown TrimMaterial: " + input);
      });

      converter.registerConversion(String.class, TrimMaterial.class, input->{
        final NamespacedKey key = NamespacedKey.fromString(input);
        if(key != null) {

          return RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_MATERIAL).getOrThrow(key);
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

      converter.registerConversion(TrimPattern.class, String.class, input->{

        final NamespacedKey key = RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_PATTERN).getKey(input);
        if(key != null) {

          return key.asString();
        }

        throw new IllegalArgumentException("Unknown TrimPattern: " + input);
      });

      converter.registerConversion(String.class, TrimPattern.class, input->{
        final NamespacedKey key = NamespacedKey.fromString(input);
        if(key != null) {

          return RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_PATTERN).getOrThrow(key);
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

      converter.registerConversion(PotionEffectType.class, String.class, input->input.getKey().toString());
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
   * @since 0.2.0.0
   */
  @Override
  public Optional<PaperItemStack> initSerialized(final JSONObject object) {

    try {
      return Optional.ofNullable(new PaperItemStack().of(object));
    } catch(final ParseException e) {
      return Optional.empty();
    }
  }
}
