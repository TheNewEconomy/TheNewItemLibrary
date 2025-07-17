package net.tnemc.sponge.platform.impl;/*
 * The New Item Library
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
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

import net.kyori.adventure.text.Component;
import net.tnemc.item.component.impl.LoreComponent;
import net.tnemc.sponge.SpongeItemStack;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * SpongeLoreComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class SpongeLoreComponent extends LoreComponent<SpongeItemStack, ItemStack> {

  public SpongeLoreComponent() {

  }

  public SpongeLoreComponent(final List<Component> lore) {

    super(lore);
  }

  public SpongeLoreComponent(final Component... lore) {

    super(lore);
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    try {

      final Key<ListValue<Component>> lore = Keys.LORE;
      return true;
    } catch(final NoSuchElementException ignore) {

      return false;
    }
  }

  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.supports(Keys.LORE);
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   *
   * @since 0.2.0.0
   */
  @Override
  public ItemStack apply(final SpongeItemStack serialized, final ItemStack item) {

    final Optional<SpongeLoreComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->item.offer(Keys.LORE, component.lore));

    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   *
   * @since 0.2.0.0
   */
  @Override
  public SpongeItemStack serialize(final ItemStack item, final SpongeItemStack serialized) {

    final Optional<List<Component>> keyOptional = item.get(Keys.LORE);
    keyOptional.ifPresent((key->{

      final SpongeLoreComponent component = (serialized.spongeComponent(identifier()) instanceof final LoreComponent<?, ?> getComponent)?
                                            (SpongeLoreComponent)getComponent : new SpongeLoreComponent();

      component.lore(key);
    }));
    return serialized;
  }
}
