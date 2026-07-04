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
import io.papermc.paper.datacomponent.item.Fireworks;
import net.tnemc.item.component.helper.ExplosionData;
import net.tnemc.item.component.impl.FireworksComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * PaperFireworksComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperFireworksComponent extends FireworksComponent<PaperItemStack, ItemStack> {

  public PaperFireworksComponent() {

  }

  public PaperFireworksComponent(final byte flightDuration) {

    super(flightDuration);
  }

  public PaperFireworksComponent(final byte flightDuration, final List<ExplosionData> explosions) {

    super(flightDuration, explosions);
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

    final Optional<PaperFireworksComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperFireworksComponent component = componentOptional.get();

    item.setData(DataComponentTypes.FIREWORKS, Fireworks.fireworks(component.explosions().stream().map(this::fireworkEffect).toList(), component.flightDuration()));

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final Fireworks fireworks = item.getData(DataComponentTypes.FIREWORKS);
    if(fireworks == null) {
      return serialized;
    }

    final PaperFireworksComponent component = (serialized.paperComponent(identifier()) instanceof final FireworksComponent<?, ?> getComponent)?
                                              (PaperFireworksComponent)getComponent : new PaperFireworksComponent();

    component.flightDuration((byte)fireworks.flightDuration());
    component.explosions(fireworks.effects().stream().map(this::explosionData).toList());

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