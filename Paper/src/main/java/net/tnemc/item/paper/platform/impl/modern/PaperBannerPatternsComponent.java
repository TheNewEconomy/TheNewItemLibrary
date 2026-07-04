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
import io.papermc.paper.datacomponent.item.BannerPatternLayers;
import net.tnemc.item.component.helper.PatternData;
import net.tnemc.item.component.impl.BannerPatternsComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

/**
 * PaperBannerPatternsComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperBannerPatternsComponent extends BannerPatternsComponent<PaperItemStack, ItemStack> {

  public PaperBannerPatternsComponent() {

  }

  public PaperBannerPatternsComponent(final List<PatternData> patterns) {

    super(patterns);
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

    final Optional<PaperBannerPatternsComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final BannerPatternLayers.Builder builder = BannerPatternLayers.bannerPatternLayers();

    for(final PatternData pattern : componentOptional.get().patterns()) {
      final DyeColor color = PaperItemPlatform.instance().converter().convert(pattern.getColor(), DyeColor.class);
      final PatternType type = PaperItemPlatform.instance().converter().convert(pattern.getPattern(), PatternType.class);

      builder.add(new Pattern(color, type));
    }

    item.setData(DataComponentTypes.BANNER_PATTERNS, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final BannerPatternLayers layers = item.getData(DataComponentTypes.BANNER_PATTERNS);
    if(layers == null) {
      return serialized;
    }

    final PaperBannerPatternsComponent component = (serialized.paperComponent(identifier()) instanceof final BannerPatternsComponent<?, ?> getComponent)?
                                                   (PaperBannerPatternsComponent)getComponent : new PaperBannerPatternsComponent();

    component.patterns().clear();

    for(final Pattern pattern : layers.patterns()) {
      component.patterns(new PatternData(PaperItemPlatform.instance().converter().convert(pattern.getColor(), String.class),
                                         PaperItemPlatform.instance().converter().convert(pattern.getPattern(), String.class)));
    }

    serialized.applyComponent(component);
    return serialized;
  }
}