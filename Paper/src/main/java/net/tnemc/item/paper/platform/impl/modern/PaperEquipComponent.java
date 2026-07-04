package net.tnemc.item.paper.platform.impl.modern;
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

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.tnemc.item.component.helper.EquipSlot;
import net.tnemc.item.component.impl.EquipComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperEquipComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperEquipComponent extends EquipComponent<PaperItemStack, ItemStack> {

  public PaperEquipComponent() {

    super(null);
  }

  public PaperEquipComponent(final String cameraKey,
                             final String equipSound,
                             final String modelKey,
                             final EquipSlot slot) {

    super(cameraKey, equipSound, modelKey, slot);
  }

  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneFour(version);
  }

  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }

  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperEquipComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperEquipComponent component = componentOptional.get();
    final Equippable.Builder builder = Equippable.equippable(PaperItemPlatform.instance().converter().convert(component.slot(), EquipmentSlot.class))
            .damageOnHurt(component.damageOnHurt())
            .dispensable(component.dispensable())
            .swappable(component.swappable())
            .equipOnInteract(component.equipOnInteract())
            .canBeSheared(component.canBeSheared());

    if(component.equipSound() != null && !component.equipSound().isEmpty()) {
      builder.equipSound(Key.key(component.equipSound()));
    }

    if(component.modelKey() != null && !component.modelKey().isEmpty()) {
      builder.assetId(Key.key(component.modelKey()));
    }

    if(component.cameraKey() != null && !component.cameraKey().isEmpty()) {
      builder.cameraOverlay(Key.key(component.cameraKey()));
    }

    if(component.shearSound() != null && !component.shearSound().isEmpty()) {
      builder.shearSound(Key.key(component.shearSound()));
    }

    if(!component.entities().isEmpty()) {
      final TypedKey<EntityType>[] keys = component.entities().stream()
              .map(entity->TypedKey.create(RegistryKey.ENTITY_TYPE, Key.key(entity)))
              .toArray(TypedKey[]::new);
      builder.allowedEntities(RegistrySet.keySet(RegistryKey.ENTITY_TYPE, keys));
    }

    item.setData(DataComponentTypes.EQUIPPABLE, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final Equippable equippable = item.getData(DataComponentTypes.EQUIPPABLE);
    if(equippable == null) {
      return serialized;
    }

    final PaperEquipComponent component = (serialized.paperComponent(identifier()) instanceof final EquipComponent<?, ?> getComponent)?
                                          (PaperEquipComponent)getComponent : new PaperEquipComponent();

    component.slot(PaperItemPlatform.instance().converter().convert(equippable.slot(), EquipSlot.class));
    component.damageOnHurt(equippable.damageOnHurt());
    component.dispensable(equippable.dispensable());
    component.swappable(equippable.swappable());
    component.equipOnInteract(equippable.equipOnInteract());
    component.canBeSheared(equippable.canBeSheared());

    if(equippable.equipSound() != null) {
      component.equipSound(equippable.equipSound().asString());
    }

    if(equippable.assetId() != null) {
      component.modelKey(equippable.assetId().asString());
    }

    if(equippable.cameraOverlay() != null) {
      component.cameraKey(equippable.cameraOverlay().asString());
    }

    if(equippable.shearSound() != null) {
      component.shearSound(equippable.shearSound().asString());
    }

    if(equippable.allowedEntities() != null) {
      component.entities().clear();
      equippable.allowedEntities().values().forEach(key->component.entities().add(key.key().asString()));
    }

    serialized.applyComponent(component);
    return serialized;
  }
}