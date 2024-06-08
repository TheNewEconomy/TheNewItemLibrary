package net.tnemc.item.sponge;

/*
 * The New Economy Minecraft Server Plugin
 *
 * Copyright (C) 2022 Daniel "creatorfromhell" Vidmar
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


import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.firework.SerialFireworkEffect;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.item.FireworkEffect;
import org.spongepowered.api.item.FireworkShape;
import org.spongepowered.api.item.FireworkShapes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.util.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsingUtil {

    public static FireworkEffect fromSerial(final SerialFireworkEffect effect) {
        final List<Color> colors = new ArrayList<>();
        for(Integer i : effect.getColors()) {
            colors.add(Color.ofRgb(i));
        }

        final List<Color> faded = new ArrayList<>();
        for(Integer i : effect.getFadeColors()) {
            faded.add(Color.ofRgb(i));
        }

        return FireworkEffect.builder().flicker(effect.hasFlicker()).trail(effect.hasTrail())
                .colors(colors).fades(faded).shape((FireworkShape) FireworkShapes.registry().value(ResourceKey.resolve(effect.getType()))).build();
    }

    public static SerialFireworkEffect fromEffect(final FireworkEffect eff) {
        final SerialFireworkEffect effect = new SerialFireworkEffect();

        for(Color color : eff.getColors()) {
            effect.getColors().add(color.getRgb());
        }

        for(Color color : eff.getFadeColors()) {
            effect.getFadeColors().add(color.getRgb());
        }

        effect.setType(eff.getShape().getId());
        effect.setTrail(eff.hasTrail());
        effect.setFlicker(eff.flickers());

        return effect;
    }

    public static Map<String, SerialItemData<ItemStack>> parseMeta(final ItemStack stack) {

        final Map<String, SerialItemData<ItemStack>> data = new HashMap<>();

        final SpongeBookData book = new SpongeBookData();
        book.of(stack);
        if(book.applies()) {
            data.put(book.getClass().getSimpleName(), book);
        }

        final SpongeCompassData compass = new SpongeCompassData();
        compass.of(stack);
        if(compass.applies()) {
            data.put(compass.getClass().getSimpleName(), compass);
        }

        final SpongeCrossbowMeta crossbow = new SpongeCrossbowMeta();
        crossbow.of(stack);
        if(crossbow.applies()) {
            data.put(crossbow.getClass().getSimpleName(), crossbow);
        }

        final SpongeEnchantData enchant = new SpongeEnchantData();
        enchant.of(stack);
        if(enchant.applies()) {
            data.put(enchant.getClass().getSimpleName(), enchant);
        }

        final SpongeDyeData dye = new SpongeDyeData();
        dye.of(stack);
        if(dye.applies()) {
            data.put(dye.getClass().getSimpleName(), dye);
        }

        final SpongeFireworkData firework = new SpongeFireworkData();
        firework.of(stack);
        if(firework.applies()) {
            data.put(firework.getClass().getSimpleName(), firework);
        }

        final SpongePatternData pattern = new SpongePatternData();
        pattern.of(stack);
        if(pattern.applies()) {
            data.put(pattern.getClass().getSimpleName(), pattern);
        }

        final SpongePotionData potion = new SpongePotionData();
        potion.of(stack);
        if(potion.applies()) {
            data.put(potion.getClass().getSimpleName(), potion);
        }

        final SpongeSkullData skull = new SpongeSkullData();
        skull.of(stack);
        if(skull.applies()) {
            data.put(skull.getClass().getSimpleName(), skull);
        }

        return data;
    }
}