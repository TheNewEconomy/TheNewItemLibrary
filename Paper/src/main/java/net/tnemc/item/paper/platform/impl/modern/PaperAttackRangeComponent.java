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
import io.papermc.paper.datacomponent.item.AttackRange;
import net.tnemc.item.component.impl.AttackRangeComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperAttackRangeComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperAttackRangeComponent extends AttackRangeComponent<PaperItemStack, ItemStack> {

  public PaperAttackRangeComponent() {

  }

  public PaperAttackRangeComponent(final float minReach,
                                   final float maxReach,
                                   final float minCreativeReach,
                                   final float maxCreativeReach,
                                   final float hitboxMargin,
                                   final float mobFactor) {
    super(minReach, maxReach, minCreativeReach, maxCreativeReach, hitboxMargin, mobFactor);
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

    final Optional<PaperAttackRangeComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperAttackRangeComponent component = componentOptional.get();

    item.setData(DataComponentTypes.ATTACK_RANGE, AttackRange.attackRange()
            .minReach(component.minReach)
            .maxReach(component.maxReach)
            .minCreativeReach(component.minCreativeReach)
            .maxCreativeReach(component.maxCreativeReach)
            .hitboxMargin(component.hitboxMargin)
            .mobFactor(component.mobFactor));

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final AttackRange range = item.getData(DataComponentTypes.ATTACK_RANGE);
    if(range == null) {
      return serialized;
    }

    final PaperAttackRangeComponent component = (serialized.paperComponent(identifier()) instanceof final AttackRangeComponent<?, ?> getComponent)?
                                                (PaperAttackRangeComponent)getComponent : new PaperAttackRangeComponent();

    component.minReach(range.minReach());
    component.maxReach(range.maxReach());
    component.minCreativeReach(range.minCreativeReach());
    component.maxCreativeReach(range.maxCreativeReach());
    component.hitboxMargin(range.hitboxMargin());
    component.mobFactor(range.mobFactor());

    serialized.applyComponent(component);
    return serialized;
  }
}