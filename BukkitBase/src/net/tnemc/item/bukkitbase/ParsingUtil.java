package net.tnemc.item.bukkitbase;

/*
 * The New Item Library Minecraft Server Plugin
 *
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

import net.tnemc.item.attribute.SerialAttributeOperation;
import net.tnemc.item.attribute.SerialAttributeSlot;
import net.tnemc.item.data.firework.SerialFireworkEffect;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ParsingUtil {

  public static ItemMeta buildFor(ItemStack stack, Class<? extends ItemMeta> type) {
    ItemMeta meta;

    if(stack.hasItemMeta() && type.isInstance(stack.getItemMeta())) {
      meta = stack.getItemMeta();
    } else {
      meta = Bukkit.getItemFactory().getItemMeta(stack.getType());
    }
    return meta;
  }

  public static FireworkEffect fromSerial(final SerialFireworkEffect effect) {
    final List<Color> colors = new ArrayList<>();
    for(Integer i : effect.getColors()) {
      colors.add(Color.fromRGB(i));
    }

    final List<Color> faded = new ArrayList<>();
    for(Integer i : effect.getFadeColors()) {
      faded.add(Color.fromRGB(i));
    }

    return FireworkEffect.builder().flicker(effect.hasFlicker()).trail(effect.hasTrail())
        .withColor(colors).withFade(faded).build();
  }

  public static SerialFireworkEffect fromEffect(final FireworkEffect eff) {
    final SerialFireworkEffect effect = new SerialFireworkEffect();

    for(Color color : eff.getColors()) {
      effect.getColors().add(color.asRGB());
    }

    for(Color color : eff.getFadeColors()) {
      effect.getFadeColors().add(color.asRGB());
    }

    effect.setType(eff.getType().name());
    effect.setTrail(eff.hasTrail());
    effect.setFlicker(eff.hasFlicker());

    return effect;
  }

  public static SerialAttributeSlot attributeSlot(final EquipmentSlot slot) {
    if(slot == null) return null;

    switch(slot) {

      case OFF_HAND:
        return SerialAttributeSlot.OFF_HAND;

      case HEAD:
        return SerialAttributeSlot.HEAD;

      case CHEST:
        return SerialAttributeSlot.CHEST;

      case LEGS:
        return SerialAttributeSlot.LEGS;

      case FEET:
        return SerialAttributeSlot.FEET;

      case HAND:
      default:
        return SerialAttributeSlot.MAIN_HAND;
    }
  }

  public static EquipmentSlot attributeSlot(final SerialAttributeSlot slot) {
    if(slot == null) return null;

    switch(slot) {

      case OFF_HAND:
        return EquipmentSlot.OFF_HAND;

      case HEAD:
        return EquipmentSlot.HEAD;

      case CHEST:
        return EquipmentSlot.CHEST;

      case LEGS:
        return EquipmentSlot.LEGS;

      case FEET:
        return EquipmentSlot.FEET;

      case MAIN_HAND:
      default:
        return EquipmentSlot.HAND;
    }
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