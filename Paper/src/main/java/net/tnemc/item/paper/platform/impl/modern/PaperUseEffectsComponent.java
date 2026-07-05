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
import io.papermc.paper.datacomponent.item.UseEffects;
import net.tnemc.item.component.impl.UseEffectsComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperUseEffectsComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperUseEffectsComponent extends UseEffectsComponent<PaperItemStack, ItemStack> {

  public PaperUseEffectsComponent() {

  }

  public PaperUseEffectsComponent(final boolean canSprint,
                                  final float speedMultiplier,
                                  final boolean interactVibrations) {

    super(canSprint, speedMultiplier, interactVibrations);
  }

  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneEleven(version);
  }

  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }

  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperUseEffectsComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperUseEffectsComponent component = componentOptional.get();

    item.setData(DataComponentTypes.USE_EFFECTS,
                 UseEffects.useEffects()
                         .canSprint(component.canSprint())
                         .speedMultiplier(component.speedMultiplier())
                         .interactVibrations(component.interactVibrations()));

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final UseEffects useEffects = item.getData(DataComponentTypes.USE_EFFECTS);
    if(useEffects == null) {
      return serialized;
    }

    final PaperUseEffectsComponent component = (serialized.paperComponent(identifier()) instanceof final UseEffectsComponent<?, ?> getComponent)?
                                               (PaperUseEffectsComponent)getComponent : new PaperUseEffectsComponent();

    component.canSprint(useEffects.canSprint());
    component.speedMultiplier(useEffects.speedMultiplier());
    component.interactVibrations(useEffects.interactVibrations());

    serialized.applyComponent(component);
    return serialized;
  }
}