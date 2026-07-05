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
import net.tnemc.item.component.impl.MinimumAttackChargeComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperMinimumAttackChargeComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperMinimumAttackChargeComponent extends MinimumAttackChargeComponent<PaperItemStack, ItemStack> {

  public PaperMinimumAttackChargeComponent() {

  }

  public PaperMinimumAttackChargeComponent(final float minimumCharge) {

    super(minimumCharge);
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

    final Optional<PaperMinimumAttackChargeComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    item.setData(DataComponentTypes.MINIMUM_ATTACK_CHARGE, componentOptional.get().minimumCharge);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final Float minimumCharge = item.getData(DataComponentTypes.MINIMUM_ATTACK_CHARGE);
    if(minimumCharge == null) {
      return serialized;
    }

    final PaperMinimumAttackChargeComponent component = (serialized.paperComponent(identifier()) instanceof final MinimumAttackChargeComponent<?, ?> getComponent)?
                                                        (PaperMinimumAttackChargeComponent)getComponent : new PaperMinimumAttackChargeComponent();

    component.minimumCharge(minimumCharge);

    serialized.applyComponent(component);
    return serialized;
  }
}