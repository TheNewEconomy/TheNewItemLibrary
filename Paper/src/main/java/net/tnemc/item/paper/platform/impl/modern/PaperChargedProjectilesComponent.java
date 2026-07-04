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
import io.papermc.paper.datacomponent.item.ChargedProjectiles;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.component.impl.ChargedProjectilesComponent;
import net.tnemc.item.component.impl.ContainerComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * PaperChargedProjectileComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperChargedProjectilesComponent extends ChargedProjectilesComponent<PaperItemStack, ItemStack> {

  public PaperChargedProjectilesComponent() {

  }

  public PaperChargedProjectilesComponent(final Map<Integer, AbstractItemStack<ItemStack>> items) {

    super(items);
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

    final Optional<PaperChargedProjectilesComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final List<ItemStack> projectiles = new ArrayList<>();
    componentOptional.get().items().forEach((slot, stack)->projectiles.add(stack.provider().locale(serialized)));

    item.setData(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectiles.chargedProjectiles(projectiles));
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final ChargedProjectiles charged = item.getData(DataComponentTypes.CHARGED_PROJECTILES);
    if(charged == null) {
      return serialized;
    }

    final PaperChargedProjectilesComponent component = (serialized.paperComponent(identifier()) instanceof final ContainerComponent<?, ?> getComponent)?
                                                       (PaperChargedProjectilesComponent)getComponent : new PaperChargedProjectilesComponent();

    int i = 0;
    for(final ItemStack projectile : charged.projectiles()) {
      if(projectile == null || projectile.getType().equals(Material.AIR)) {
        continue;
      }

      final PaperItemStack projectileSerial = new PaperItemStack().of(projectile);
      PaperItemPlatform.instance().providerApplies(projectileSerial, projectile);
      component.items().put(i, projectileSerial);
      i++;
    }

    serialized.applyComponent(component);
    return serialized;
  }
}