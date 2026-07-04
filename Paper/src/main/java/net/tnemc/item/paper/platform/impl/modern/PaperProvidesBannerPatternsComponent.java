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
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.tnemc.item.component.impl.ProvidesBannerPatternsComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperProvidesBannerPAtternsComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperProvidesBannerPatternsComponent extends ProvidesBannerPatternsComponent<PaperItemStack, ItemStack> {

  public PaperProvidesBannerPatternsComponent() {

  }

  public PaperProvidesBannerPatternsComponent(final String patternTag) {

    super(patternTag);
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

    final Optional<PaperProvidesBannerPatternsComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final TypedKey<PatternType> key = TypedKey.create(RegistryKey.BANNER_PATTERN, Key.key(componentOptional.get().patternTag()));
    item.setData(DataComponentTypes.PROVIDES_BANNER_PATTERNS, RegistrySet.keySet(RegistryKey.BANNER_PATTERN, key));

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final RegistryKeySet<PatternType> patterns = item.getData(DataComponentTypes.PROVIDES_BANNER_PATTERNS);
    if(patterns == null || patterns.values().isEmpty()) {
      return serialized;
    }

    final PaperProvidesBannerPatternsComponent component = (serialized.paperComponent(identifier()) instanceof final ProvidesBannerPatternsComponent<?, ?> getComponent)?
                                                           (PaperProvidesBannerPatternsComponent)getComponent : new PaperProvidesBannerPatternsComponent();

    component.patternTag(patterns.values().iterator().next().key().asString());

    serialized.applyComponent(component);
    return serialized;
  }
}