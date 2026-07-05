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

import net.tnemc.item.component.helper.DyeColor;
import net.tnemc.item.component.helper.EquipSlot;
import net.tnemc.item.component.helper.effect.EffectInstance;
import net.tnemc.item.platform.conversion.PlatformConverter;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Locale;

import static net.tnemc.item.component.helper.EquipSlot.ARMOUR;

/**
 * SharedConverters
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public final class SharedConverters {

  private final PlatformConverter converter;

  public SharedConverters(final PlatformConverter converter) {
    this.converter = converter;
  }

  public void init() {

    //Enchantment
    if(!VersionUtil.isOneTwentyOneFour(PaperItemPlatform.instance().version())
       && !VersionUtil.isOneThirteen(PaperItemPlatform.instance().version())) {

      converter.registerConversion(String.class, Enchantment.class, (final String input)->{

        final Enchantment enchantment = Enchantment.getByName(input);
        if(enchantment == null) {

          throw new IllegalArgumentException("Unknown Enchantment: " + input);
        }
        return enchantment;
      });

      converter.registerConversion(Enchantment.class, String.class, Enchantment::getName);
    }

    //PotionType
    if(!VersionUtil.isOneTwentyOneFour(PaperItemPlatform.instance().version())
       && !VersionUtil.isOneThirteen(PaperItemPlatform.instance().version())) {
      converter.registerConversion(String.class, PotionType.class, (final String input)->{

        final PotionType potion = PotionType.valueOf(input.toUpperCase(Locale.ROOT));
        if(potion == null) {

          throw new IllegalArgumentException("Unknown PotionType: " + input);
        }
        return potion;
      });

      converter.registerConversion(PotionType.class, String.class, (final PotionType input)->input.name());
    }

    //PotionEffectType
    if(!VersionUtil.isOneTwentyOneFour(PaperItemPlatform.instance().version())) {
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
}