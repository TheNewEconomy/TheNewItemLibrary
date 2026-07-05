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
import io.papermc.paper.datacomponent.item.SuspiciousStewEffects;
import io.papermc.paper.potion.SuspiciousEffectEntry;
import net.tnemc.item.component.helper.effect.EffectInstance;
import net.tnemc.item.component.impl.SuspiciousStewEffectsComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Optional;

/**
 * PaperSuspiciousStewEffectsComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperSuspiciousStewEffectsComponent extends SuspiciousStewEffectsComponent<PaperItemStack, ItemStack> {

  public PaperSuspiciousStewEffectsComponent() {

  }

  public PaperSuspiciousStewEffectsComponent(final List<EffectInstance> effects) {

    super(effects);
  }

  public PaperSuspiciousStewEffectsComponent(final EffectInstance... effects) {

    super(effects);
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

    final Optional<PaperSuspiciousStewEffectsComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    item.setData(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS,
                 SuspiciousStewEffects.suspiciousStewEffects(componentOptional.get().effects().stream()
                                                                     .map(this::entry)
                                                                     .toList()));

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final SuspiciousStewEffects stewEffects = item.getData(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
    if(stewEffects == null) {
      return serialized;
    }

    final PaperSuspiciousStewEffectsComponent component = (serialized.paperComponent(identifier()) instanceof final SuspiciousStewEffectsComponent<?, ?> getComponent)?
                                                          (PaperSuspiciousStewEffectsComponent)getComponent : new PaperSuspiciousStewEffectsComponent();

    component.effects(stewEffects.effects().stream().map(this::effectInstance).toList());

    serialized.applyComponent(component);
    return serialized;
  }

  private SuspiciousEffectEntry entry(final EffectInstance effect) {

    final PotionEffectType type = PaperItemPlatform.instance().converter().convert(effect.id(), PotionEffectType.class);
    return SuspiciousEffectEntry.create(type, effect.duration());
  }

  private EffectInstance effectInstance(final SuspiciousEffectEntry entry) {

    return new EffectInstance(PaperItemPlatform.instance().converter().convert(entry.effect(), String.class),
                              0,
                              entry.duration(),
                              true,
                              false,
                              true);
  }
}