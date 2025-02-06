package net.tnemc.item.bukkitbase;

/*
 * The New Item Library Minecraft Server Plugin
 *
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

import net.tnemc.item.attribute.SerialAttributeOperation;
import net.tnemc.item.component.helper.EquipSlot;
import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static net.tnemc.item.component.helper.EquipSlot.HAND;

public class ParsingUtil {

  public static ItemMeta buildFor(final ItemStack stack, final Class<? extends ItemMeta> type) {

    return stack.getItemMeta();
  }

  /*public static FireworkEffect fromSerial(final SerialFireworkEffect effect) {

    final List<Color> colors = new ArrayList<>();
    for(final Integer i : effect.getColors()) {
      colors.add(Color.fromRGB(i));
    }

    final List<Color> faded = new ArrayList<>();
    for(final Integer i : effect.getFadeColors()) {
      faded.add(Color.fromRGB(i));
    }

    return FireworkEffect.builder().flicker(effect.hasFlicker()).trail(effect.hasTrail())
            .withColor(colors).withFade(faded).build();
  }

  public static SerialFireworkEffect fromEffect(final FireworkEffect eff) {

    final SerialFireworkEffect effect = new SerialFireworkEffect();

    for(final Color color : eff.getColors()) {
      effect.getColors().add(color.asRGB());
    }

    for(final Color color : eff.getFadeColors()) {
      effect.getFadeColors().add(color.asRGB());
    }

    effect.setType(eff.getType().name());
    effect.setTrail(eff.hasTrail());
    effect.setFlicker(eff.hasFlicker());

    return effect;
  }*/

  public static EquipSlot attributeSlot(final EquipmentSlot slot) {

    if(slot == null) return null;

    return switch(slot) {
      case BODY -> EquipSlot.BODY;
      case HEAD -> EquipSlot.HEAD;
      case CHEST -> EquipSlot.CHEST;
      case LEGS -> EquipSlot.LEGS;
      case FEET -> EquipSlot.FEET;
      case OFF_HAND -> EquipSlot.OFF_HAND;
      default -> HAND;
    };
  }

  public static EquipmentSlot attributeSlot(final EquipSlot slot) {

    if(slot == null) return null;

    return switch(slot) {
      case BODY -> EquipmentSlot.BODY;
      case HEAD -> EquipmentSlot.HEAD;
      case CHEST -> EquipmentSlot.CHEST;
      case LEGS -> EquipmentSlot.LEGS;
      case FEET -> EquipmentSlot.FEET;
      case OFF_HAND -> EquipmentSlot.OFF_HAND;
      default -> EquipmentSlot.HAND;
    };
  }

  public static SerialAttributeOperation attributeOperation(final AttributeModifier.Operation operation) {

    switch(operation) {

      case ADD_SCALAR:
        return SerialAttributeOperation.MULTIPLY_BASE;

      case MULTIPLY_SCALAR_1:
        return SerialAttributeOperation.MULTIPLY;

      case ADD_NUMBER:
      default:
        return SerialAttributeOperation.ADD;
    }
  }

  public static AttributeModifier.Operation attributeOperation(final SerialAttributeOperation operation) {

    switch(operation) {

      case MULTIPLY_BASE:
        return AttributeModifier.Operation.ADD_SCALAR;

      case MULTIPLY:
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;

      case ADD:
      default:
        return AttributeModifier.Operation.ADD_NUMBER;
    }
  }

  public static String version() {

    return Bukkit.getServer().getBukkitVersion().split("-")[0];
  }
}