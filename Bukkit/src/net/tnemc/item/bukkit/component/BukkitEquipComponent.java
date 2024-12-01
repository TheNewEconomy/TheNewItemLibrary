package net.tnemc.item.bukkit.component;
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

import net.kyori.adventure.key.Key;
import net.tnemc.item.bukkit.BukkitHelper;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.component.helper.EquipSlot;
import net.tnemc.item.component.impl.EquipComponent;
import net.tnemc.item.component.impl.UseComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * BukkitEquipComponent
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitEquipComponent extends EquipComponent<ItemStack> {

  public static BukkitEquipComponent create(final ItemStack stack) {

    final BukkitEquipComponent component = new BukkitEquipComponent();
    component.of(stack);
    return component;
  }

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialComponent} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(final ItemStack stack) {

    if(stack.hasItemMeta()) {

      final ItemMeta meta = stack.getItemMeta();
      if(meta.hasEquippable()) {

        final org.bukkit.inventory.meta.components.EquippableComponent component = meta.getEquippable();
        this.cameraKey = component.getCameraOverlay().toString();
        this.equipSound = component.getEquipSound().getKey().toString();
        this.modelKey = component.getModel().toString();
        this.slot = ParsingUtil.attributeSlot(component.getSlot());
        this.damageWithEntity = component.isDamageOnHurt();
        this.dispensable = component.isDispensable();
        this.swappable = component.isSwappable();

        if(component.getAllowedEntities() != null) {

          component.getAllowedEntities().forEach(entityType ->this.entities.add(entityType.getKey().toString()));
        }
      }
    }
  }

  /**
   * This method is used to apply the component to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(final ItemStack stack) {

    ItemMeta meta = stack.getItemMeta();
    if(meta == null) {
      meta = Bukkit.getItemFactory().getItemMeta(stack.getType());
    }

    final Collection<EntityType> types = this.entities.stream()
            .map(entity -> Registry.ENTITY_TYPE.get(NamespacedKey.fromString(entity)))
            .collect(Collectors.toSet());

    final org.bukkit.inventory.meta.components.EquippableComponent component = meta.getEquippable();
    component.setCameraOverlay(NamespacedKey.fromString(this.cameraKey));
    component.setEquipSound(Registry.SOUNDS.get(NamespacedKey.fromString(this.equipSound)));
    component.setModel(NamespacedKey.fromString(this.modelKey));
    component.setSlot(ParsingUtil.attributeSlot(this.slot));
    component.setDamageOnHurt(this.damageWithEntity);
    component.setDispensable(this.dispensable);
    component.setSwappable(this.swappable);

    component.setAllowedEntities(types);

    meta.setEquippable(component);
    stack.setItemMeta(meta);
    return stack;
  }
}