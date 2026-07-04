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
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import net.kyori.adventure.key.Key;
import net.tnemc.item.component.helper.effect.ComponentEffect;
import net.tnemc.item.component.impl.ConsumableComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperConsumeEffectAdapter;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.Optional;

/**
 * PaperConsumableComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperConsumableComponent extends ConsumableComponent<PaperItemStack, ItemStack> {

  public PaperConsumableComponent() {

  }

  public PaperConsumableComponent(final float consumeSeconds,
                                  final String animation,
                                  final String sound,
                                  final boolean hasConsumeParticles) {

    super(consumeSeconds, animation, sound, hasConsumeParticles);
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

    final Optional<PaperConsumableComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperConsumableComponent component = componentOptional.get();
    final Consumable.Builder builder = Consumable.consumable()
            .consumeSeconds(component.consumeSeconds())
            .animation(PaperItemPlatform.instance().converter().convert(component.animation(), ItemUseAnimation.class))
            .hasConsumeParticles(component.hasConsumeParticles());

    if(component.sound() != null && !component.sound().isEmpty()) {
      builder.sound(Key.key(component.sound()));
    }

    for(final ComponentEffect effect : component.effects()) {
      PaperConsumeEffectAdapter.toPaper(effect).ifPresent(builder::addEffect);
    }

    item.setData(DataComponentTypes.CONSUMABLE, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final Consumable consumable = item.getData(DataComponentTypes.CONSUMABLE);
    if(consumable == null) {
      return serialized;
    }

    final PaperConsumableComponent component = (serialized.paperComponent(identifier()) instanceof final ConsumableComponent<?, ?> getComponent)?
                                               (PaperConsumableComponent)getComponent : new PaperConsumableComponent();

    component.consumeSeconds(consumable.consumeSeconds());
    component.animation(PaperItemPlatform.instance().converter().convert(component.animation(), String.class));
    component.hasConsumeParticles(consumable.hasConsumeParticles());

    if(consumable.sound() != null) {
      component.sound(consumable.sound().asString());
    }

    component.effects().clear();
    consumable.consumeEffects().forEach(effect->PaperConsumeEffectAdapter.fromPaper(effect).ifPresent(component.effects()::add));

    serialized.applyComponent(component);
    return serialized;
  }
}