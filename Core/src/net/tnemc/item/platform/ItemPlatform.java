package net.tnemc.item.platform;
/*
 * The New Item Library
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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.platform.applier.ItemApplicator;
import net.tnemc.item.platform.check.ItemCheck;
import net.tnemc.item.platform.check.LocaleItemCheck;
import net.tnemc.item.platform.deserialize.ItemDeserializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public abstract class ItemPlatform<I extends AbstractItemStack<T>, T> {

  protected final Map<String, ItemCheck<T>> checks = new HashMap<>();
  protected final Map<String, ItemApplicator<I, T>> applicators = new HashMap<>();
  protected final Map<String, ItemDeserializer<I, T>> deserializers = new HashMap<>();

  /**
   * @return the version that is being used currently
   */
  public abstract String version();

  protected void addDefaultChecks() {
  }

  /**
   * @param check the {@link ItemCheck check} to add.
   */
  public void addCheck(final ItemCheck<T> check) {
    checks.put(check.identifier(), check);
  }

  /**
   * @param applicator the applicator to add
   */
  public void addApplicator(final ItemApplicator<I, T> applicator) {
    applicators.put(applicator.identifier(), applicator);
  }

  /**
   * @param deserializer the deserializer to add
   */
  public void addDeserializer(final ItemDeserializer<I, T> deserializer) {
    deserializers.put(deserializer.identifier(), deserializer);
  }

  /**
   * Used to check if two locale stacks are comparable.
   * @param original the original stack
   * @param check the stack to use for the check
   * @param disabledChecks the {@link ItemCheck#identifier()} check identifiers that should be disabled for the check.
   *
   * @return True if the check passes, otherwise false.
   */
  public boolean check(final T original, final T check, final String... disabledChecks) {

    final List<String> disabled = Arrays.asList(disabledChecks);
    for(final ItemCheck<T> checkItem : checks.values()) {

      if(disabled.contains(checkItem.identifier())) {
        continue;
      }

      if(checkItem instanceof LocaleItemCheck<T> locale) {

        if(locale.enabled(version()) && !locale.check(original, check)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Used to check if two locale stacks are comparable based on a specific order of checks.
   * @param original the original stack
   * @param check the stack to use for the check
   * @param order the order of the checks to run for the comparison
   *
   * @return True if the check passes, otherwise false.
   */
  public boolean checkOrder(final T original, final T check, final String... order) {

    for(String id : order) {
      if(checks.containsKey(id)) {

        final ItemCheck<T> checkItem = checks.get(id);
        if(checkItem instanceof LocaleItemCheck<T> locale) {

          if(checkItem.enabled(version())) {
            return locale.check(original, check);
          }
        }
      }
    }
    return true;
  }

  /**
   * Used to check if two serialized stacks are comparable based on a specific order of checks.
   * @param original the original stack
   * @param check the stack to use for the check
   * @param order the order of the checks to run for the comparison
   *
   * @return True if the check passes, otherwise false.
   */
  public boolean checkOrder(final I original, final I check, final String... order) {

    for(String id : order) {
      if(checks.containsKey(id)) {

        final ItemCheck<T> checkItem = checks.get(id);
        if(checkItem.enabled(version())) {

          return checkItem.check(original, check);
        }

      }
    }
    return true;
  }

  /**
   * Used to check if two serialized stacks are comparable.
   * @param original the original stack
   * @param check the stack to use for the check
   * @param disabledChecks the {@link ItemCheck#identifier()} check identifiers that should be disabled for the check.
   *
   * @return True if the check passes, otherwise false.
   */
  public boolean check(final I original, final I check, final String... disabledChecks) {

    final List<String> disabled = Arrays.asList(disabledChecks);
    for(final ItemCheck<T> checkItem : checks.values()) {

      if(disabled.contains(checkItem.identifier())) {
        continue;
      }

      if(checkItem.enabled(version()) && !checkItem.check(original, check)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Applies all enabled applicators to the given item.
   *
   * @param serialized the serialized item stack to use
   * @param item the locale itemstack object to apply the applications to
   * @return the updated item stack after applying the applicators
   */
  public T apply(final I serialized, T item) {
    for(final ItemApplicator<I, T> applicator : applicators.values()) {
      if(applicator.enabled(version())) {
        item = applicator.apply(serialized, item);
      }
    }
    return item;
  }

  /**
   * Applies all enabled deserializers to the given item.
   *
   * @param item the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   * @return the updated serialized item.
   */
  public I deserialize(final T item, I serialized) {
    for(final ItemDeserializer<I, T> deserializer : deserializers.values()) {
      if(deserializer.enabled(version())) {
        serialized = deserializer.deserialize(item, serialized);
      }
    }
    return serialized;
  }

  public static String componentString(final Component component) {
    return PlainTextComponentSerializer.plainText().serialize(component);
  }
}