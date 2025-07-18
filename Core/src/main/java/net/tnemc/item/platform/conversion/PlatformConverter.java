package net.tnemc.item.platform.conversion;
/*
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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * PlatformConverter is used for converting TNIL-based data strings to the Platform's enum/registry
 * entries.
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PlatformConverter {

  private final Map<Class<?>, Map<Class<?>, Function<Object, Object>>> registry = new HashMap<>();

  /**
   * Registers a conversion between input and output classes using a provided converter function.
   *
   * @param <I>         The input class type
   * @param <O>         The output class type
   * @param inputClass  The class of the input object to convert from
   * @param outputClass The class of the output object to convert to
   * @param converter   The function that performs the conversion from input to output
   *
   * @since 0.2.0.0
   */
  public <I, O> void registerConversion(final Class<I> inputClass, final Class<O> outputClass, final Function<I, O> converter) {

    registry.computeIfAbsent(inputClass, k->new HashMap<>())
            .put(outputClass, input->converter.apply(inputClass.cast(input)));
  }

  /**
   * Performs a conversion from input type to output type using registered converters.
   *
   * @param <I>         The type of the input object
   * @param <O>         The class of the output object
   * @param input       The input object to be converted
   * @param outputClass The class of the output type to be converted to
   *
   * @return The converted output object
   *
   * @throws IllegalArgumentException if the input is null or if no conversion is registered for the
   *                                  specified types
   * @since 0.2.0.0
   */
  public <I, O> O convert(final I input, final Class<O> outputClass) {

    if(input == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }

    final Class<?> inputClass = input.getClass();
    final Function<Object, Object> converter = registry.getOrDefault(inputClass, Map.of()).get(outputClass);

    if(converter != null) {

      return outputClass.cast(converter.apply(input));
    }

    for(final Map.Entry<Class<?>, Map<Class<?>, Function<Object, Object>>> entry : registry.entrySet()) {
      if(entry.getKey().isAssignableFrom(inputClass)) {

        return outputClass.cast(entry.getKey().cast(input));
      }
    }

    throw new IllegalArgumentException("No conversion registered from " + inputClass.getName() + " to " + outputClass.getName());

  }
}