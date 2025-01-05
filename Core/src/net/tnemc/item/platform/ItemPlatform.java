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
import net.tnemc.item.SerialItemData;
import net.tnemc.item.component.helper.revive.ApplyEffectsReviveEffect;
import net.tnemc.item.component.helper.revive.PlaySoundReviveEffect;
import net.tnemc.item.component.helper.revive.RemoveEffectsReviveEffect;
import net.tnemc.item.component.helper.revive.ReviveEffect;
import net.tnemc.item.component.helper.revive.TeleportRandomlyReviveEffect;
import net.tnemc.item.persistent.PersistentDataType;
import net.tnemc.item.persistent.impl.PersistentBool;
import net.tnemc.item.persistent.impl.PersistentByte;
import net.tnemc.item.persistent.impl.PersistentByteArray;
import net.tnemc.item.persistent.impl.PersistentDouble;
import net.tnemc.item.persistent.impl.PersistentFloat;
import net.tnemc.item.persistent.impl.PersistentInt;
import net.tnemc.item.persistent.impl.PersistentIntArray;
import net.tnemc.item.persistent.impl.PersistentLong;
import net.tnemc.item.persistent.impl.PersistentLongArray;
import net.tnemc.item.persistent.impl.PersistentShort;
import net.tnemc.item.persistent.impl.PersistentString;
import net.tnemc.item.platform.applier.ItemApplicator;
import net.tnemc.item.platform.check.ItemCheck;
import net.tnemc.item.platform.check.LocaleItemCheck;
import net.tnemc.item.platform.serialize.ItemSerializer;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public abstract class ItemPlatform<I extends AbstractItemStack<T>, T> {

  private final Map<String, Class<? extends PersistentDataType<?>>> classes = new ConcurrentHashMap<>();

  protected final Map<String, ItemCheck<T>> checks = new HashMap<>();
  protected final Map<String, ItemApplicator<I, T>> applicators = new HashMap<>();
  protected final Map<String, ItemSerializer<I, T>> serializers = new HashMap<>();

  protected final Map<String, Class<? extends ReviveEffect>> reviveEffects = new HashMap<>();

  public ItemPlatform() {

    addPersistentDataType("bool", PersistentBool.class);
    addPersistentDataType("byte", PersistentByte.class);
    addPersistentDataType("byte-array", PersistentByteArray.class);
    addPersistentDataType("double", PersistentDouble.class);
    addPersistentDataType("float", PersistentFloat.class);
    addPersistentDataType("int", PersistentInt.class);
    addPersistentDataType("int-array", PersistentIntArray.class);
    addPersistentDataType("long", PersistentLong.class);
    addPersistentDataType("long-array", PersistentLongArray.class);
    addPersistentDataType("short", PersistentShort.class);
    addPersistentDataType("string", PersistentString.class);

    addReviveEffect(new ApplyEffectsReviveEffect());
    addReviveEffect(new PlaySoundReviveEffect());
    addReviveEffect(new RemoveEffectsReviveEffect());
    addReviveEffect(new TeleportRandomlyReviveEffect());
  }

  /**
   * @return the version that is being used currently
   */
  public abstract String version();

  public abstract void addDefaults();

  /**
   * Adds a persistent data type to the item platform.
   *
   * @param identifier The identifier for the persistent data type.
   * @param type       The class representing the persistent data type.
   */
  public void addPersistentDataType(final String identifier, @NotNull final Class<? extends PersistentDataType<?>> type) {

    classes.put(identifier, type);
  }

  public Map<String, Class<? extends PersistentDataType<?>>> getClasses() {

    return classes;
  }

  /**
   * Used to add an object that is capable of being dual/tri purpose as a check, applicator and/or
   * deserializer.
   *
   * @param object the object to add.
   */
  public void addMulti(@NotNull final Object object) {

    if(object instanceof final ItemCheck<?> check) {

      try {

        checks.put(check.identifier(), (ItemCheck<T>)check);
      } catch(final Exception ignore) {
        //Just in case it passes the instance check, but the Generic is
        //incorrect for w.e reason, we want to fail safely.
      }
    }

    if(object instanceof final ItemApplicator<?, ?> check) {

      try {

        applicators.put(check.identifier(), (ItemApplicator<I, T>)check);
      } catch(final Exception ignore) {
        //Just in case it passes the instance check, but the Generic is
        //incorrect for w.e reason, we want to fail safely.
      }
    }

    if(object instanceof final ItemSerializer<?, ?> check) {

      try {

        serializers.put(check.identifier(), (ItemSerializer<I, T>)check);
      } catch(final Exception ignore) {
        //Just in case it passes the instance check, but the Generic is
        //incorrect for w.e reason, we want to fail safely.
      }
    }
  }

  /**
   * @param check the {@link ItemCheck check} to add.
   */
  public void addCheck(@NotNull final ItemCheck<T> check) {

    checks.put(check.identifier(), check);
  }

  /**
   * @param applicator the applicator to add
   */
  public void addApplicator(@NotNull final ItemApplicator<I, T> applicator) {

    applicators.put(applicator.identifier(), applicator);
  }

  /**
   * @param serializer the deserializer to add
   */
  public void addSerializer(@NotNull final ItemSerializer<I, T> serializer) {

    serializers.put(serializer.identifier(), serializer);
  }

  /**
   * Adds a ReviveEffect to the reviveEffects map.
   *
   * @param effect The ReviveEffect instance to add. Must not be null.
   */
  public void addReviveEffect(@NotNull final ReviveEffect effect) {

    // Add the effect's class to the map using its type as the key
    reviveEffects.put(effect.getType(), effect.getClass());
  }

  /**
   * Used to check if two locale stacks are comparable.
   *
   * @param original       the original stack
   * @param check          the stack to use for the check
   * @param disabledChecks the {@link ItemCheck#identifier()} check identifiers that should be
   *                       disabled for the check.
   *
   * @return True if the check passes, otherwise false.
   */
  public boolean check(@NotNull final T original, @NotNull final T check, @NotNull final String... disabledChecks) {

    final List<String> disabled = Arrays.asList(disabledChecks);
    for(final ItemCheck<T> checkItem : checks.values()) {

      if(disabled.contains(checkItem.identifier())) {
        continue;
      }

      if(checkItem instanceof final LocaleItemCheck<T> locale) {

        if(locale.enabled(version()) && locale.applies(original, check)) {
          return locale.check(original, check);
        }
      }
    }
    return true;
  }

  /**
   * Used to check if two locale stacks are comparable based on a specific order of checks.
   *
   * @param original the original stack
   * @param check    the stack to use for the check
   * @param order    the order of the checks to run for the comparison
   *
   * @return True if the check passes, otherwise false.
   */
  public boolean checkOrder(@NotNull final T original, @NotNull final T check, @NotNull final String... order) {

    for(final String id : order) {
      if(checks.containsKey(id)) {

        final ItemCheck<T> checkItem = checks.get(id);
        if(checkItem instanceof final LocaleItemCheck<T> locale) {

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
   *
   * @param original the original stack
   * @param check    the stack to use for the check
   * @param order    the order of the checks to run for the comparison
   *
   * @return True if the check passes, otherwise false.
   */
  public boolean checkOrder(@NotNull final I original, @NotNull final I check, @NotNull final String... order) {

    for(final String id : order) {
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
   *
   * @param original       the original stack
   * @param check          the stack to use for the check
   * @param disabledChecks the {@link ItemCheck#identifier()} check identifiers that should be
   *                       disabled for the check.
   *
   * @return True if the check passes, otherwise false.
   */
  public boolean check(@NotNull final I original, @NotNull final I check, final String... disabledChecks) {

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
   * @param item       the locale itemstack object to apply the applications to
   *
   * @return the updated item stack after applying the applicators
   */
  public T apply(@NotNull final I serialized, @NotNull T item) {

    for(final ItemApplicator<I, T> applicator : applicators.values()) {
      if(applicator.enabled(version())) {
        item = applicator.apply(serialized, item);
      }
    }
    return item;
  }

  /**
   * Applies all enabled serializers to the given item.
   *
   * @param item       the item that we should use to serialize.
   * @param serialized the serialized item stack we should use to apply this serializer to
   *
   * @return the updated serialized item.
   */
  public I serializer(@NotNull final T item, @NotNull I serialized) {

    for(final ItemSerializer<I, T> serializer : serializers.values()) {
      if(serializer.enabled(version())) {
        serialized = serializer.serialize(item, serialized);
      }
    }
    return serialized;
  }

  /**
   * Initializes and returns an AbstractItemStack object by deserializing the provided JSON object.
   *
   * @param object the JSON object to deserialize
   *
   * @return an initialized AbstractItemStack object
   */
  public abstract Optional<I> initSerialized(final JSONObject object);

  /**
   * Parses the meta of the given stack and returns an Optional containing the SerialItemData.
   *
   * @param stack The stack to parse the meta from.
   * @return Optional containing the SerialItemData, empty if the meta parsing failed.
   */
  public abstract Optional<SerialItemData<T>> parseMeta(final T stack);

  public static String componentString(@NotNull final Component component) {

    return PlainTextComponentSerializer.plainText().serialize(component);
  }

  public Map<String, Class<? extends ReviveEffect>> reviveEffects() {

    return reviveEffects;
  }
}