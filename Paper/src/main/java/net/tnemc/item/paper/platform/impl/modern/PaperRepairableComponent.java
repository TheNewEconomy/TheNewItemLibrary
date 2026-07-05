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
import io.papermc.paper.datacomponent.item.Repairable;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.tnemc.item.component.impl.RepairableComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;

import java.util.List;
import java.util.Optional;

/**
 * PaperRepairableComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperRepairableComponent extends RepairableComponent<PaperItemStack, ItemStack> {

  public PaperRepairableComponent() {

  }

  public PaperRepairableComponent(final List<String> repairItems) {

    super(repairItems);
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

    final Optional<PaperRepairableComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final TypedKey<ItemType>[] keys = componentOptional.get().repairItems().stream()
            .map(value->TypedKey.create(RegistryKey.ITEM, Key.key(value)))
            .toArray(TypedKey[]::new);

    item.setData(DataComponentTypes.REPAIRABLE, Repairable.repairable(RegistrySet.keySet(RegistryKey.ITEM, keys)));
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final Repairable repairable = item.getData(DataComponentTypes.REPAIRABLE);
    if(repairable == null) {
      return serialized;
    }

    final PaperRepairableComponent component = (serialized.paperComponent(identifier()) instanceof final RepairableComponent<?, ?> getComponent)?
                                               (PaperRepairableComponent)getComponent : new PaperRepairableComponent();

    component.repairItems().clear();
    repairable.types().values().forEach(key->component.repairItem(key.key().asString()));

    serialized.applyComponent(component);
    return serialized;
  }
}