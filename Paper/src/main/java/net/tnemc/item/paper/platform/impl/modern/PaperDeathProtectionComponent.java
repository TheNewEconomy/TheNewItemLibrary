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
import io.papermc.paper.datacomponent.item.DeathProtection;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import net.tnemc.item.component.helper.effect.ComponentEffect;
import net.tnemc.item.component.impl.DeathProtectionComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PaperDeathProtectionComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperDeathProtectionComponent extends DeathProtectionComponent<PaperItemStack, ItemStack> {

  public PaperDeathProtectionComponent() {

  }

  public PaperDeathProtectionComponent(final List<ComponentEffect> deathEffects) {

    this.deathEffects.addAll(deathEffects);
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

    final Optional<PaperDeathProtectionComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final List<ConsumeEffect> effects = new ArrayList<>();

    for(final ComponentEffect effect : componentOptional.get().deathEffects()) {
      effects.add(PaperItemPlatform.instance().converter().convert(effect, ConsumeEffect.class));
    }

    item.setData(DataComponentTypes.DEATH_PROTECTION, DeathProtection.deathProtection(effects));
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final DeathProtection protection = item.getData(DataComponentTypes.DEATH_PROTECTION);
    if(protection == null) {
      return serialized;
    }

    final PaperDeathProtectionComponent component = (serialized.paperComponent(identifier()) instanceof final DeathProtectionComponent<?, ?> getComponent)?
                                                    (PaperDeathProtectionComponent)getComponent : new PaperDeathProtectionComponent();

    component.deathEffects().clear();

    for(final ConsumeEffect effect : protection.deathEffects()) {
      component.deathEffects().add(PaperItemPlatform.instance().converter().convert(effect, ComponentEffect.class));
    }

    serialized.applyComponent(component);
    return serialized;
  }
}