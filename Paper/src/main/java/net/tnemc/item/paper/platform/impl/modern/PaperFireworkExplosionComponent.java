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
import net.tnemc.item.component.helper.ExplosionData;
import net.tnemc.item.component.impl.FireworkExplosionComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * PaperFireworkExplosionComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperFireworkExplosionComponent extends FireworkExplosionComponent<PaperItemStack, ItemStack> {

  public PaperFireworkExplosionComponent() {

  }

  public PaperFireworkExplosionComponent(final String shape, final boolean hasTrail, final boolean hasTwinkle, final List<Integer> colors, final List<Integer> fadeColors) {

    super(shape, hasTrail, hasTwinkle, colors, fadeColors);
  }

  public PaperFireworkExplosionComponent(final ExplosionData explosion) {

    super(explosion);
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

    final Optional<PaperFireworkExplosionComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty() || componentOptional.get().explosion() == null) {
      return item;
    }

    item.setData(DataComponentTypes.FIREWORK_EXPLOSION, fireworkEffect(componentOptional.get().explosion()));
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final FireworkEffect effect = item.getData(DataComponentTypes.FIREWORK_EXPLOSION);
    if(effect == null) {
      return serialized;
    }

    final PaperFireworkExplosionComponent component = (serialized.paperComponent(identifier()) instanceof final FireworkExplosionComponent<?, ?> getComponent)?
                                                      (PaperFireworkExplosionComponent)getComponent : new PaperFireworkExplosionComponent();

    component.explosion(explosionData(effect));

    serialized.applyComponent(component);
    return serialized;
  }

  private FireworkEffect fireworkEffect(final ExplosionData explosion) {

    return FireworkEffect.builder()
            .with(FireworkEffect.Type.valueOf(explosion.shape().toUpperCase(Locale.ROOT)))
            .trail(explosion.hasTrail())
            .flicker(explosion.hasTwinkle())
            .withColor(explosion.colors().stream().map(Color::fromRGB).toList())
            .withFade(explosion.fadeColors().stream().map(Color::fromRGB).toList())
            .build();
  }

  private ExplosionData explosionData(final FireworkEffect effect) {

    return new ExplosionData(effect.getType().name().toLowerCase(Locale.ROOT),
                             effect.hasTrail(),
                             effect.hasFlicker(),
                             effect.getColors().stream().map(Color::asRGB).toList(),
                             effect.getFadeColors().stream().map(Color::asRGB).toList());
  }
}