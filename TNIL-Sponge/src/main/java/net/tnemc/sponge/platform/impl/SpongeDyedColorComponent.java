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

import net.tnemc.item.component.impl.DyedColorComponent;
import net.tnemc.sponge.SpongeItemStack;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * SpongeDyedColorComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class SpongeDyedColorComponent extends DyedColorComponent<SpongeItemStack, ItemStack> {

  public SpongeDyedColorComponent() {

  }

  public SpongeDyedColorComponent(final int rgb) {

    super(rgb);
  }

  public SpongeDyedColorComponent(final int red, final int green, final int blue) {

    super(red, green, blue);
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

      final Key<Value<DyeColor>> color = Keys.DYE_COLOR;
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

    return item.supports(Keys.DYE_COLOR);
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

    final Optional<SpongeDyedColorComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->item.offer(Keys.DYE_COLOR, colorFromRGB(component.rgb)));

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

    final Optional<DyeColor> keyOptional = item.get(Keys.DYE_COLOR);
    keyOptional.ifPresent((key->{

      final SpongeDyedColorComponent component = (serialized.spongeComponent(identifier()) instanceof final DyedColorComponent<?, ?> getComponent)?
                                                 (SpongeDyedColorComponent)getComponent : new SpongeDyedColorComponent();

      component.rgb = key.color().rgb();
    }));
    return serialized;
  }

  private DyeColor colorFromRGB(final int rgb) {

    for(final DyeColor color : DyeColors.registry().stream().toList()) {

      if(color.color().rgb() == rgb) return color;
    }
    return DyeColors.BLACK.get();
  }
}