package net.tnemc.item.paper.platform;
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

import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.tnemc.item.component.helper.effect.ApplyEffectsComponentEffect;
import net.tnemc.item.component.helper.effect.ComponentEffect;
import net.tnemc.item.component.helper.effect.EffectInstance;
import net.tnemc.item.component.helper.effect.PlaySoundComponentEffect;
import net.tnemc.item.component.helper.effect.RemoveEffectsComponentEffect;
import net.tnemc.item.component.helper.effect.TeleportRandomlyComponentEffect;
import net.tnemc.item.platform.conversion.PlatformConverter;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Locale;

/**
 * ModernConverters
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class ModernConverters {

  private final PlatformConverter converter;

  public ModernConverters(final PlatformConverter converter) {
    this.converter = converter;
  }

  public void init() {

    //Enchantment
    converter.registerConversion(String.class, Enchantment.class, (final String input)->{

      final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
      if(key != null) {

        return Registry.ENCHANTMENT.getOrThrow(key);
      }
      throw new IllegalArgumentException("Unknown Enchantment: " + input);
    });

    converter.registerConversion(Enchantment.class, String.class, (final Enchantment input)->input.getKey().toString());

    //PatternType
    converter.registerConversion(PatternType.class, String.class, input->{

      final NamespacedKey key = input.getKey();

      return key.toString();
    });

    //ItemUseAnimation
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

    //Attribute
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

    //ItemType
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

    //Consume Effects
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

    //TrimMaterial
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

    //TrimPattern
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

    //PotionType
    converter.registerConversion(String.class, PotionType.class, (final String input)->{

      final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
      if(key != null) {

        return Registry.POTION.getOrThrow(key);
      }
      throw new IllegalArgumentException("Unknown PotionType: " + input);
    });

    converter.registerConversion(PotionType.class, String.class, (final PotionType input)->input.getKey().toString());

    //PotionEffectType
    converter.registerConversion(PotionEffectType.class, String.class, input->input.getKey().toString());
    converter.registerConversion(String.class, PotionEffectType.class, input->{
      final NamespacedKey key = NamespacedKey.fromString(input.toLowerCase(Locale.ROOT));
      if(key != null) {

        return Registry.EFFECT.getOrThrow(key);
      }
      throw new IllegalArgumentException("Unknown PotionEffectType: " + input);
    });
  }
}