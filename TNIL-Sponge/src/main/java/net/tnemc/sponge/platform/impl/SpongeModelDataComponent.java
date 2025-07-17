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

import net.tnemc.item.component.impl.ModelDataComponent;
import net.tnemc.sponge.SpongeItemStack;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * SpongeModelDataComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class SpongeModelDataComponent extends ModelDataComponent<SpongeItemStack, ItemStack> {

  public SpongeModelDataComponent() {

  }

  public SpongeModelDataComponent(final List<String> colours, final List<Float> floats, final List<Boolean> flags, final List<String> strings) {

    super(colours, floats, flags, strings);
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

      final Key<ListValue<Color>> colors = Keys.CUSTOM_MODEL_DATA_COLORS;
      final Key<ListValue<Boolean>> flags = Keys.CUSTOM_MODEL_DATA_FLAGS;
      final Key<ListValue<Float>> floats = Keys.CUSTOM_MODEL_DATA_FLOATS;
      final Key<ListValue<String>> strings = Keys.CUSTOM_MODEL_DATA_STRINGS;
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

    return item.supports(Keys.CUSTOM_MODEL_DATA_COLORS) || item.supports(Keys.CUSTOM_MODEL_DATA_FLAGS)
           || item.supports(Keys.CUSTOM_MODEL_DATA_FLOATS) || item.supports(Keys.CUSTOM_MODEL_DATA_STRINGS);
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

    final Optional<SpongeModelDataComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->item.offer(Keys.CUSTOM_MODEL_DATA_COLORS, fromStrings(component.colours)));
    componentOptional.ifPresent(component->item.offer(Keys.CUSTOM_MODEL_DATA_FLAGS, component.flags));
    componentOptional.ifPresent(component->item.offer(Keys.CUSTOM_MODEL_DATA_FLOATS, component.floats));
    componentOptional.ifPresent(component->item.offer(Keys.CUSTOM_MODEL_DATA_STRINGS, component.strings));

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

    final SpongeModelDataComponent component = (serialized.spongeComponent(identifier()) instanceof final ModelDataComponent<?, ?> getComponent)?
                                               (SpongeModelDataComponent)getComponent : new SpongeModelDataComponent();

    final Optional<List<Color>> colorsOptional = item.get(Keys.CUSTOM_MODEL_DATA_COLORS);
    colorsOptional.ifPresent((key->{

      for(final Color value : key) {

        component.colours.add(String.valueOf(value.rgb()));
      }
    }));

    final Optional<List<Boolean>> flagsOptional = item.get(Keys.CUSTOM_MODEL_DATA_FLAGS);
    flagsOptional.ifPresent((component.flags::addAll));

    final Optional<List<Float>> floatsOptional = item.get(Keys.CUSTOM_MODEL_DATA_FLOATS);
    floatsOptional.ifPresent((component.floats::addAll));

    final Optional<List<String>> stringsOptional = item.get(Keys.CUSTOM_MODEL_DATA_STRINGS);
    stringsOptional.ifPresent((component.strings::addAll));
    return serialized;
  }

  private List<Color> fromStrings(final List<String> strings) {

    final List<Color> colors = new ArrayList<>();
    for(final String string : strings) {

      colors.add(Color.ofRgb(Integer.parseInt(string)));
    }
    return colors;
  }
}