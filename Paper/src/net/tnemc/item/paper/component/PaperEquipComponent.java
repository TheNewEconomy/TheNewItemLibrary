package net.tnemc.item.paper.component;
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

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.component.impl.EquipComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.stream.Collectors;

/**
 * BukkitEquipComponent
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class PaperEquipComponent extends EquipComponent<ItemStack> {

  public static PaperEquipComponent create(final ItemStack stack) {

    final PaperEquipComponent component = new PaperEquipComponent();
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
        this.cameraKey = component.getCameraOverlay().key().toString();
        this.equipSound = component.getEquipSound().key().toString();
        this.modelKey = component.getModel().key().toString();
        this.slot = ParsingUtil.attributeSlot(component.getSlot());
        this.damageOnHurt = component.isDamageOnHurt();
        this.dispensable = component.isDispensable();
        this.swappable = component.isSwappable();

        if(component.getAllowedEntities() != null) {

          component.getAllowedEntities().forEach(entityType->this.entities.add(entityType.getKey().key().toString()));
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

    final RegistryKeySet<EntityType> entities = RegistrySet.keySet(
            RegistryKey.ENTITY_TYPE,
            this.entities.stream()
                    .map(entity -> TypedKey.create(RegistryKey.ENTITY_TYPE, Key.key(entity)))
                    .collect(Collectors.toSet()));


    final Equippable.Builder equippable = Equippable.equippable(ParsingUtil.attributeSlot(this.slot))
            .cameraOverlay(Key.key(this.cameraKey))
            .equipSound(Key.key(this.equipSound))
            .model(Key.key(this.modelKey))
            .damageOnHurt(this.damageOnHurt)
            .dispensable(this.dispensable)
            .swappable(this.swappable)
            .allowedEntities(entities);

    stack.setData(DataComponentTypes.EQUIPPABLE, equippable);
    return stack;
  }
}