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

import net.tnemc.item.component.helper.EquipSlot;
import net.tnemc.item.platform.conversion.PlatformConverter;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.Locale;

import static org.bukkit.inventory.EquipmentSlotGroup.MAINHAND;
import static org.bukkit.inventory.EquipmentSlotGroup.OFFHAND;

/**
 * OneTwentyOneConverters
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class OneTwentyOneConverters {

  private final PlatformConverter converter;

  public OneTwentyOneConverters(final PlatformConverter converter) {
    this.converter = converter;
  }

  public void init() {

    //EquipmentSlotGroup
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

    //PatternType
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

    if(!VersionUtil.isOneTwentyOneFour(PaperItemPlatform.instance().version())) {
      converter.registerConversion(PatternType.class, String.class, input->{

        final NamespacedKey key = input.getKey();

        return key.toString();
      });
    }
  }
}
