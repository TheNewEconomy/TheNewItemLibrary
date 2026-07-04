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
import io.papermc.paper.datacomponent.item.UseCooldown;
import net.kyori.adventure.key.Key;
import net.tnemc.item.component.impl.UseCooldownComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperUseCooldownComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperUseCooldownComponent extends UseCooldownComponent<PaperItemStack, ItemStack> {

  public PaperUseCooldownComponent(final float seconds) {

    super(seconds);
  }

  public PaperUseCooldownComponent(final String cooldownGroup) {

    super(cooldownGroup);
  }

  public PaperUseCooldownComponent(final String cooldownGroup, final float seconds) {

    super(cooldownGroup, seconds);
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

    final Optional<PaperUseCooldownComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperUseCooldownComponent component = componentOptional.get();
    final UseCooldown.Builder builder = UseCooldown.useCooldown(component.seconds());

    if(component.cooldownGroup() != null && !component.cooldownGroup().isEmpty()) {
      builder.cooldownGroup(Key.key(component.cooldownGroup()));
    }

    item.setData(DataComponentTypes.USE_COOLDOWN, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final UseCooldown cooldown = item.getData(DataComponentTypes.USE_COOLDOWN);
    if(cooldown == null) {
      return serialized;
    }

    final PaperUseCooldownComponent component = (serialized.paperComponent(identifier()) instanceof final UseCooldownComponent<?, ?> getComponent)?
                                                (PaperUseCooldownComponent)getComponent : new PaperUseCooldownComponent(cooldown.seconds());

    component.seconds(cooldown.seconds());

    if(cooldown.cooldownGroup() != null) {
      component.cooldownGroup(cooldown.cooldownGroup().asString());
    }

    serialized.applyComponent(component);
    return serialized;
  }
}