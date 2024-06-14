package net.tnemc.sponge.data;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.item.data.BannerData;
import net.tnemc.item.data.banner.PatternData;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.meta.BannerPatternLayer;
import org.spongepowered.api.data.type.BannerPatternShape;
import org.spongepowered.api.data.type.BannerPatternShapes;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryTypes;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SpongePatternData extends BannerData<ItemStack> {

    protected boolean applies = false;

    @Override
    public void of(ItemStack stack) {
        final Optional<List<BannerPatternLayer>> patterns = stack.get(Keys.BANNER_PATTERN_LAYERS);
        if(patterns.isPresent()) {
            applies = true;

            for(BannerPatternLayer pattern : patterns.get()) {
                this.patterns.add(new PatternData(pattern.color().key(RegistryTypes.DYE_COLOR).formatted(), pattern.shape().key(RegistryTypes.BANNER_PATTERN_SHAPE).formatted()));
            }
        }
    }

    @Override
    public ItemStack apply(ItemStack stack) {

        if(patterns.size() > 0) {
            final List<BannerPatternLayer> layers = new LinkedList<>();

            for(PatternData data : patterns) {
                layers.add(BannerPatternLayer.of((BannerPatternShape)BannerPatternShapes.registry().value(ResourceKey.resolve(data.getPattern())),
                           (DyeColor)DyeColors.registry().value(ResourceKey.resolve(data.getColor()))));
            }
            stack.offer(Keys.BANNER_PATTERN_LAYERS, layers);
        }
        return stack;
    }

    @Override
    public boolean applies() {
        return applies;
    }
}
