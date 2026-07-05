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

import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.bukkitbase.platform.providers.ItemAdderProvider;
import net.tnemc.item.bukkitbase.platform.providers.MMOItemProvider;
import net.tnemc.item.bukkitbase.platform.providers.SlimefunProvider;
import net.tnemc.item.component.helper.EquipSlot;
import net.tnemc.item.component.helper.effect.ApplyEffectsComponentEffect;
import net.tnemc.item.component.helper.effect.ComponentEffect;
import net.tnemc.item.component.helper.effect.EffectInstance;
import net.tnemc.item.component.helper.effect.PlaySoundComponentEffect;
import net.tnemc.item.component.helper.effect.RemoveEffectsComponentEffect;
import net.tnemc.item.component.helper.effect.TeleportRandomlyComponentEffect;
import net.tnemc.item.paper.PaperCalculationsProvider;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.VanillaProvider;
import net.tnemc.item.paper.platform.impl.modern.PaperAttackRangeComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperAttributeModifiersComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperBannerPatternsComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperBaseColorComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperBreakSoundComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperBundleComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperCanBreakComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperCanPlaceOnComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperChargedProjectilesComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperConsumableComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperContainerComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperContainerLootComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperCustomNameComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperDamageComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperDamageTypeComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperDeathProtectionComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperDyedColorComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperEnchantableComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperEnchantmentGlintOverrideComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperEnchantmentsComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperEquipComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperFireworkExplosionComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperFireworksComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperFoodComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperGliderComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperInstrumentComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperIntangibleProjectileComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperItemModelComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperItemNameComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperJukeBoxComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperKineticWeaponComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperLodestoneTrackerComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperLoreComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperMapColorComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperMapIDComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperMaxDamageComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperMaxStackComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperMinimumAttackChargeComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperModelDataComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperNoteBlockSoundComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperOminousBottleAmplifierComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperPiercingWeaponComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperPotDecorationsComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperPotionContentsComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperPotionDurationScaleComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperProfileComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperProvidesTrimMaterialComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperRarityComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperRecipesComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperRepairCostComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperRepairableComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperShulkerColorComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperStoredEnchantmentsComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperSuspiciousStewEffectsComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperSwingAnimationComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperToolComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperTooltipDisplayComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperTooltipStyleComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperTrimComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperUnbreakableComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperUseCooldownComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperUseEffectsComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperUseRemainderComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperWeaponComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperWritableBookContentComponent;
import net.tnemc.item.paper.platform.impl.modern.PaperWrittenBookContentComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldAttributeModifiersComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldBannerPatternsComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldBaseColorComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldBundleComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldChargedProjectilesComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldContainerComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldCustomNameComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldDamageComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldDyedColorComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldEnchantableComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldEnchantmentsComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldFoodComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldGliderComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldHideTooltipComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldItemModelComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldItemNameComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldLoreComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldMaxDamageComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldMaxStackSizeComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldModelDataComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldModelDataLegacyComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldProfileComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldRarityComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldRepairCostComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldShulkerColorComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldStoredEnchantmentsComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldSuspiciousStewEffectsComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldTooltipStyleComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldTrimComponent;
import net.tnemc.item.paper.platform.impl.old.PaperOldUnbreakableComponent;
import net.tnemc.item.paper.platform.providers.NexoProvider;
import net.tnemc.item.paper.platform.providers.NovaProvider;
import net.tnemc.item.paper.platform.providers.OraxenProvider;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.providers.ItemProvider;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.bukkit.inventory.EquipmentSlotGroup.MAINHAND;
import static org.bukkit.inventory.EquipmentSlotGroup.OFFHAND;

/**
 * PaperItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class PaperItemPlatform extends ItemPlatform<PaperItemStack, ItemStack, Inventory> {

  private static final Pattern DIGITS = Pattern.compile("\\d+");

  private static volatile PaperItemPlatform instance;

  //public static final PaperItemPlatform PLATFORM = new PaperItemPlatform();

  protected final VanillaProvider defaultProvider = new VanillaProvider();
  protected final PaperCalculationsProvider calculationsProvider = new PaperCalculationsProvider();
  protected boolean useModern = false;

  private PaperItemPlatform() {

    super();
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

  @Override
  public PaperItemStack createStack(final String material) {

    return new PaperItemStack(material, 1);
  }

  /**
   * @return the version that is being used currently
   *
   * @since 0.2.0.0
   */
  @Override
  public String version() {

    String version = Bukkit.getServer().getBukkitVersion();
    if (version.indexOf('-') != -1)
      version = version.substring(0, version.indexOf('-'));

    final String[] split = version.split("\\.");
    if(split.length == 1) {
      return split[0] + ".0.0";
    }

    if (split.length == 2) {
      return split[0] + "." + split[1] + ".0";
    }

    String minorSlice = split[2];
    if (minorSlice.indexOf('-') != -1)
      minorSlice = minorSlice.substring(0, minorSlice.indexOf('-'));

    int patch = 0;
    if (DIGITS.matcher(minorSlice).matches()) {
      patch = Integer.parseInt(minorSlice);
    }
    return split[0] + "." + split[1] + "." + patch;
  }

  @Override
  public void addDefaults() {

    this.useModern = VersionUtil.isOneTwentyOneFour(version());

    registerConversions();

    if(useModern) {

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
      addMulti(new PaperPotionContentsComponent());
      addMulti(new PaperPotionDurationScaleComponent());
      addMulti(new PaperWritableBookContentComponent());
      addMulti(new PaperConsumableComponent());
      addMulti(new PaperProvidesTrimMaterialComponent());
      addMulti(new PaperRarityComponent());
      addMulti(new PaperRecipesComponent());
      addMulti(new PaperRepairableComponent());
      addMulti(new PaperRepairCostComponent());
      addMulti(new PaperStoredEnchantmentsComponent());

      addMulti(new PaperAttackRangeComponent());
      addMulti(new PaperAttributeModifiersComponent());
      addMulti(new PaperBannerPatternsComponent());
      addMulti(new PaperBaseColorComponent());
      addMulti(new PaperBreakSoundComponent());
      addMulti(new PaperCanBreakComponent());
      addMulti(new PaperCanPlaceOnComponent());
      addMulti(new PaperChargedProjectilesComponent());
      addMulti(new PaperContainerLootComponent());
      addMulti(new PaperDamageComponent());
      addMulti(new PaperDamageTypeComponent());
      addMulti(new PaperDeathProtectionComponent());
      addMulti(new PaperDyedColorComponent());
      addMulti(new PaperEnchantableComponent());
      addMulti(new PaperEnchantmentGlintOverrideComponent());
      addMulti(new PaperEquipComponent());
      addMulti(new PaperFireworkExplosionComponent());
      addMulti(new PaperFireworksComponent());
      addMulti(new PaperFoodComponent());
      addMulti(new PaperGliderComponent());
      addMulti(new PaperInstrumentComponent());
      addMulti(new PaperIntangibleProjectileComponent());
      addMulti(new PaperJukeBoxComponent());
      addMulti(new PaperKineticWeaponComponent());
      addMulti(new PaperLodestoneTrackerComponent());
      addMulti(new PaperMapColorComponent());
      addMulti(new PaperMapIDComponent());
      addMulti(new PaperMaxDamageComponent());
      addMulti(new PaperMaxStackComponent());
      addMulti(new PaperMinimumAttackChargeComponent());
      addMulti(new PaperNoteBlockSoundComponent());
      addMulti(new PaperOminousBottleAmplifierComponent());
      addMulti(new PaperPiercingWeaponComponent());
      addMulti(new PaperPotDecorationsComponent());
      addMulti(new PaperSuspiciousStewEffectsComponent());
      addMulti(new PaperSwingAnimationComponent());
      addMulti(new PaperToolComponent());
      addMulti(new PaperTooltipDisplayComponent());
      addMulti(new PaperTooltipStyleComponent());
      addMulti(new PaperTrimComponent());
      addMulti(new PaperUnbreakableComponent());
      addMulti(new PaperUseCooldownComponent());
      addMulti(new PaperUseEffectsComponent());
      addMulti(new PaperUseRemainderComponent());
      addMulti(new PaperWeaponComponent());
      addMulti(new PaperWrittenBookContentComponent());
    } else {
      addMulti(new PaperOldAttributeModifiersComponent());
      addMulti(new PaperOldBannerPatternsComponent());
      addMulti(new PaperOldBaseColorComponent());
      addMulti(new PaperOldBundleComponent());
      addMulti(new PaperOldChargedProjectilesComponent());
      addMulti(new PaperOldContainerComponent());
      addMulti(new PaperOldCustomNameComponent());
      addMulti(new PaperOldDamageComponent());
      addMulti(new PaperOldDyedColorComponent());
      addMulti(new PaperOldEnchantableComponent());
      addMulti(new PaperOldEnchantmentsComponent());
      addMulti(new PaperOldFoodComponent());
      addMulti(new PaperOldGliderComponent());
      addMulti(new PaperOldHideTooltipComponent());
      addMulti(new PaperOldItemModelComponent());
      addMulti(new PaperOldItemNameComponent());
      addMulti(new PaperOldLoreComponent());
      addMulti(new PaperOldMaxDamageComponent());
      addMulti(new PaperOldMaxStackSizeComponent());
      addMulti(new PaperOldModelDataComponent());
      addMulti(new PaperOldModelDataLegacyComponent());
      addMulti(new PaperOldProfileComponent());
      addMulti(new PaperOldRarityComponent());
      addMulti(new PaperOldRepairCostComponent());
      addMulti(new PaperOldShulkerColorComponent());
      addMulti(new PaperOldStoredEnchantmentsComponent());
      addMulti(new PaperOldSuspiciousStewEffectsComponent());
      addMulti(new PaperOldTooltipStyleComponent());
      addMulti(new PaperOldTrimComponent());
      addMulti(new PaperOldUnbreakableComponent());
    }


    if(Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")) {
      addItemProvider(new ItemAdderProvider());
    }

    if(Bukkit.getPluginManager().isPluginEnabled("MMOItems")) {
      addItemProvider(new MMOItemProvider());
    }

    if(Bukkit.getPluginManager().isPluginEnabled("Nexo")) {

      //System.out.println("Adding nexo provider");
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
   *
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
   *
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
   *
   * @since 0.2.0.0
   */
  @Override
  public PaperItemStack locale(final ItemStack locale) {

    return new PaperItemStack().of(locale);
  }

  private void registerConversions() {

    if(useModern) {
      converter.registerConversion(String.class, ItemUseAnimation.class, input -> {
        if (input == null || input.isBlank()) {
          return ItemUseAnimation.EAT;
        }

        try {
          return ItemUseAnimation.valueOf(input.trim().toUpperCase(Locale.ROOT));
        } catch (final IllegalArgumentException ex) {
          return ItemUseAnimation.EAT;
        }
      });

      converter.registerConversion(ItemUseAnimation.class, String.class, input -> input.name().toLowerCase(Locale.ROOT));
    }

    //ConsumeEffect converter
    if(useModern) {
      converter.registerConversion(ComponentEffect.class, ConsumeEffect.class, effect->{

        if(effect instanceof final ApplyEffectsComponentEffect apply) {
          return ConsumeEffect.applyStatusEffects(apply.getEffects().stream()
                                                          .map(effectInstance->PaperItemPlatform.instance().converter().convert(effectInstance, PotionEffect.class))
                                                          .toList(), apply.probability());
        }

        if(effect instanceof final RemoveEffectsComponentEffect remove) {
          if(!remove.removeAll()) {
            return ConsumeEffect.removeEffects(RegistrySet.keySet(RegistryKey.MOB_EFFECT,
                                                                  remove.getEffectIds().stream()
                                                                          .map(id->TypedKey.create(RegistryKey.MOB_EFFECT,
                                                                                                   PaperItemPlatform.instance().converter().convert(id, PotionEffectType.class).getKey()))
                                                                          .toList()));
          }
          return ConsumeEffect.clearAllStatusEffects();
        }

        if(effect instanceof final PlaySoundComponentEffect sound) {
          return ConsumeEffect.playSoundConsumeEffect(Key.key(sound.sound()));
        }

        if(effect instanceof final TeleportRandomlyComponentEffect teleport) {
          return ConsumeEffect.teleportRandomlyEffect(teleport.getDiameter());
        }

        return null;
      });

      converter.registerConversion(ConsumeEffect.class, ComponentEffect.class, effect->{

        if(effect instanceof final ConsumeEffect.ApplyStatusEffects apply) {
          final ApplyEffectsComponentEffect tnil = new ApplyEffectsComponentEffect();
          tnil.probability(apply.probability());
          tnil.getEffects().addAll(apply.effects().stream()
                                           .map(effectInstance->PaperItemPlatform.instance().converter().convert(effectInstance, EffectInstance.class))
                                           .toList());
          return tnil;
        }

        if(effect instanceof final ConsumeEffect.RemoveStatusEffects remove) {
          final RemoveEffectsComponentEffect tnil = new RemoveEffectsComponentEffect();
          remove.removeEffects().forEach(type->tnil.getEffectIds().add(PaperItemPlatform.instance().converter().convert(type, String.class)));
          return tnil;
        }

        if(effect instanceof final ConsumeEffect.ClearAllStatusEffects clear) {
          return new RemoveEffectsComponentEffect();
        }

        if(effect instanceof final ConsumeEffect.PlaySound sound) {
          final PlaySoundComponentEffect tnil = new PlaySoundComponentEffect();
          tnil.sound(sound.sound().asString());
          return tnil;
        }

        if(effect instanceof final ConsumeEffect.TeleportRandomly teleport) {
          final TeleportRandomlyComponentEffect tnil = new TeleportRandomlyComponentEffect();
          tnil.setDiameter(teleport.diameter());
          return tnil;
        }

        return null;
      });
    }

    //ItemType
    if(useModern) {
      converter.registerConversion(String.class, ItemType.class, input -> {
        if (input == null || input.isBlank()) {
          throw new IllegalArgumentException("Item type cannot be null or blank.");
        }

        final ItemType type = Registry.ITEM.get(NamespacedKey.fromString(input.toLowerCase(Locale.ROOT)));

        if (type == null) {
          throw new IllegalArgumentException("Unknown item type: " + input);
        }

        return type;
      });

      converter.registerConversion(ItemType.class, String.class, input -> input.getKey().toString().toLowerCase(Locale.ROOT));
    }


    //RegisterConversion for EquipmentSlot
    converter.registerConversion(String.class, EquipmentSlot.class, input -> {
      if (input == null) {
        return EquipmentSlot.HAND;
      }

      try {
        return EquipmentSlot.valueOf(input.trim().toUpperCase(Locale.ROOT));
      } catch (final IllegalArgumentException ex) {
        return EquipmentSlot.HAND; // fallback for unknown values
      }
    });


    converter.registerConversion(EquipmentSlot.class, String.class, EquipmentSlot::name);

    // EquipmentSlot -> EquipSlot
    converter.registerConversion(EquipmentSlot.class, EquipSlot.class, input -> {
      if (input == null) {
        return EquipSlot.HAND;
      }

      final String name = input.name();

      if ("HAND".equals(name)) {
        return EquipSlot.HAND;
      }

      try {
        return EquipSlot.valueOf(name);
      } catch (final IllegalArgumentException ignored) {
        return EquipSlot.HAND;
      }
    });

    // EquipSlot -> EquipmentSlot
    converter.registerConversion(EquipSlot.class, EquipmentSlot.class, input -> {
      if (input == null) {
        return EquipmentSlot.HAND;
      }

      final String name = switch (input) {
        case MAIN_HAND -> "HAND";
        case ANY, SADDLE, ARMOUR -> "HAND";
        default -> input.name();
      };

      try {
        return EquipmentSlot.valueOf(name);
      } catch (final IllegalArgumentException ignored) {
        return EquipmentSlot.HAND;
      }
    });

    converter.registerConversion(EffectInstance.class, PotionEffect.class, effect -> {
      final PotionEffectType type = converter.convert(effect.id(), PotionEffectType.class);

      return new PotionEffect(
              type,
              effect.duration(),
              effect.amplifier(),
              effect.ambient(),
              effect.showParticles(),
              effect.showIcon()
      );
    });

    converter.registerConversion(PotionEffect.class, EffectInstance.class, effect -> {
      return new EffectInstance(
              converter.convert(effect.getType(), String.class),
              effect.getAmplifier(),
              effect.getDuration(),
              effect.hasParticles(),
              effect.isAmbient(),
              effect.hasIcon()
      );
    });

    converter.registerConversion(EquipSlot.class, EquipmentSlotGroup.class, input -> {
      if (input == null || input == EquipSlot.ANY) {
        return EquipmentSlotGroup.ANY;
      }

      return switch (input) {
        case ARMOUR -> EquipmentSlotGroup.CHEST;
        case MAIN_HAND -> MAINHAND;
        case OFF_HAND -> OFFHAND;
        default -> {
          try {
            yield EquipmentSlotGroup.getByName(input.name());
          } catch (final IllegalArgumentException ex) {
            yield EquipmentSlotGroup.ANY;
          }
        }
      };
    });

    converter.registerConversion(EquipmentSlotGroup.class, EquipSlot.class, input -> {
      if (input == null) {
        return EquipSlot.ANY;
      }

      return switch (input.toString().toUpperCase(Locale.ROOT)) {
        case "HEAD" -> EquipSlot.HEAD;
        case "CHEST" -> EquipSlot.CHEST;
        case "LEGS" -> EquipSlot.LEGS;
        case "FEET" -> EquipSlot.FEET;
        case "HAND" -> EquipSlot.HAND;
        case "MAINHAND" -> EquipSlot.MAIN_HAND;
        case "OFFHAND" -> EquipSlot.OFF_HAND;
        default -> EquipSlot.ANY;
      };
    });

    //RegisterConversion for EquipmentSlotGroup
    try {
      converter.registerConversion(String.class, EquipmentSlotGroup.class, input->{
        final EquipmentSlotGroup group = EquipmentSlotGroup.getByName(input);
        if(group == null) {
          throw new IllegalArgumentException("Unknown input: " + input);
        }
        return group;
      });

      converter.registerConversion(EquipmentSlotGroup.class, String.class, EquipmentSlotGroup::toString);
    } catch(final NoClassDefFoundError ignore) {

    }

    //Register Conversions for Attribute.class
    if(useModern) {

      converter.registerConversion(String.class, Attribute.class, input -> {
        if (input == null) {
          throw new IllegalArgumentException("Attribute cannot be null.");
        }

        final Attribute attribute = Registry.ATTRIBUTE.get(NamespacedKey.fromString(input.toLowerCase(Locale.ROOT)));

        if (attribute == null) {
          throw new IllegalArgumentException("Unknown attribute: " + input);
        }

        return attribute;
      });

      converter.registerConversion(Attribute.class, String.class, input -> input.getKey().toString().toLowerCase(Locale.ROOT));
    } else {
      converter.registerConversion(String.class, Attribute.class, input -> {
        final Attribute attribute = Attribute.valueOf(input.toUpperCase(Locale.ROOT));
        return attribute;
      });

      converter.registerConversion(Attribute.class, String.class,
                                   input -> input.name().toLowerCase(Locale.ROOT)
                                  );
    }

    //Register Conversions for AttributeModifier
    converter.registerConversion(String.class, AttributeModifier.Operation.class, input->switch(input.toLowerCase()) {
      case "add_value" -> AttributeModifier.Operation.ADD_NUMBER;
      case "add_multiplied_base" -> AttributeModifier.Operation.ADD_SCALAR;
      case "add_multiplied_total" -> AttributeModifier.Operation.MULTIPLY_SCALAR_1;
      default -> throw new IllegalArgumentException("Unknown input: " + input);
    });

    converter.registerConversion(AttributeModifier.Operation.class, String.class, input->switch(input) {
      case ADD_NUMBER -> "add_value";
      case ADD_SCALAR -> "add_multiplied_base";
      case MULTIPLY_SCALAR_1 -> "add_multiplied_total";
    });

    //Register conversions for DyeColor
    converter.registerConversion(String.class, DyeColor.class, input->switch(input.toLowerCase(Locale.ROOT)) {
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

    converter.registerConversion(DyeColor.class, String.class, input->switch(input) {
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

        final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
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

    }

    //Register Conversions for Enchantment, which will be dependent on versions
    //We'll separate the legacy find methods from the modern ones in order to maintain one component
    // class for both.
    if(VersionUtil.isOneTwentyOneFour(version())) {

      converter.registerConversion(String.class, Enchantment.class, (final String input)->{

        final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
        if(key != null) {

          return Registry.ENCHANTMENT.getOrThrow(key);
        }
        throw new IllegalArgumentException("Unknown Enchantment: " + input);
      });

      converter.registerConversion(Enchantment.class, String.class, (final Enchantment input)->input.getKey().toString());

    } else if(VersionUtil.isOneThirteen(version())) {
      converter.registerConversion(String.class, Enchantment.class, (final String input)->{

        final Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(input.toLowerCase(Locale.ROOT)));
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
        final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
        if(key != null) {

          return RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_MATERIAL).getOrThrow(key);
        }
        throw new IllegalArgumentException("Unknown TrimMaterial: " + input);
      });

    } else if(VersionUtil.isOneTwenty(version())) {

      converter.registerConversion(TrimMaterial.class, String.class, input->input.getKey().toString());

      converter.registerConversion(String.class, TrimMaterial.class, input->{
        final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
        if(key != null) {

          return Registry.TRIM_MATERIAL.get(key);
        }
        throw new IllegalArgumentException("Unknown TrimMaterial: " + input);
      });
    }

    //Register Conversions for NamespacedKey, which will be dependent on versions
    //We'll separate the legacy find methods from the modern ones in order to maintain one component
    // class for both.
    try {
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
    } catch(final NoClassDefFoundError ignore) {

    }

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
        final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
        if(key != null) {

          return RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_PATTERN).getOrThrow(key);
        }
        throw new IllegalArgumentException("Unknown TrimPattern: " + input);
      });

    } else if(VersionUtil.isOneTwenty(version())) {

      converter.registerConversion(TrimPattern.class, String.class, input->input.getKey().toString());

      converter.registerConversion(String.class, TrimPattern.class, input->{
        final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
        if(key != null) {

          return Registry.TRIM_PATTERN.get(key);
        }
        throw new IllegalArgumentException("Unknown TrimPattern: " + input);
      });
    }

    if(VersionUtil.isOneTwentyOneFour(version())) {

      converter.registerConversion(PotionEffectType.class, String.class, input->input.getKey().toString());
      converter.registerConversion(String.class, PotionEffectType.class, input->{
        final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
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

    // Register conversions for PotionType.
    if(VersionUtil.isOneTwentyOneFour(version())) {

      converter.registerConversion(String.class, PotionType.class, (final String input)->{

        final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
        if(key != null) {

          return Registry.POTION.getOrThrow(key);
        }
        throw new IllegalArgumentException("Unknown PotionType: " + input);
      });

      converter.registerConversion(PotionType.class, String.class, (final PotionType input)->input.getKey().toString());

    } else if(VersionUtil.isOneThirteen(version())) {

      converter.registerConversion(String.class, PotionType.class, (final String input)->{

        final PotionType potion = PotionType.valueOf(input.toUpperCase(Locale.ROOT));
        if(potion == null) {

          throw new IllegalArgumentException("Unknown PotionType: " + input);
        }
        return potion;
      });

      converter.registerConversion(PotionType.class, String.class, (final PotionType input)->input.name().toLowerCase(Locale.ROOT));

    } else {

      converter.registerConversion(String.class, PotionType.class, (final String input)->{

        final PotionType potion = PotionType.valueOf(input.toUpperCase(Locale.ROOT));
        if(potion == null) {

          throw new IllegalArgumentException("Unknown PotionType: " + input);
        }
        return potion;
      });

      converter.registerConversion(PotionType.class, String.class, (final PotionType input)->input.name());
    }
  }

  /**
   * Initializes and returns an AbstractItemStack object by deserializing the provided JSON object.
   *
   * @param object the JSON object to deserialize
   *
   * @return an initialized AbstractItemStack object
   *
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

  public boolean useModern() {

    return useModern;
  }
}
