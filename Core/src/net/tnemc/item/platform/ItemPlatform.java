package net.tnemc.item.platform;
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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.component.helper.effect.ApplyEffectsComponentEffect;
import net.tnemc.item.component.helper.effect.ComponentEffect;
import net.tnemc.item.component.helper.effect.PlaySoundComponentEffect;
import net.tnemc.item.component.helper.effect.RemoveEffectsComponentEffect;
import net.tnemc.item.component.helper.effect.TeleportRandomlyComponentEffect;
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
import net.tnemc.item.platform.conversion.PlatformConverter;
import net.tnemc.item.platform.serialize.ItemSerializer;
import net.tnemc.item.providers.CalculationsProvider;
import net.tnemc.item.providers.ItemProvider;
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
 * @param <I> The implementation's instance of {@link AbstractItemStack}
 * @param <S> The implementation's instance of item stacks.
 * @param <U> The implementation's instace of inventories.
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class ItemPlatform<I extends AbstractItemStack<S>, S, U> {

  protected final Map<String, Class<? extends PersistentDataType<?>>> classes = new ConcurrentHashMap<>();

  protected final Map<String, ItemProvider<S>> itemProviders = new ConcurrentHashMap<>();

  protected final Map<String, ItemCheck<S>> checks = new HashMap<>();
  protected final Map<String, LocaleItemCheck<S>> localeChecks = new HashMap<>();
  protected final Map<String, ItemApplicator<I, S>> applicators = new HashMap<>();
  protected final Map<String, ItemSerializer<I, S>> serializers = new HashMap<>();

  protected final Map<String, Class<? extends ComponentEffect>> effects = new HashMap<>();

  protected final PlatformConverter converter;

  public ItemPlatform() {

    this(new PlatformConverter());
  }

  public ItemPlatform(final PlatformConverter converter) {

    this.converter = converter;

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

    addEffect(new ApplyEffectsComponentEffect());
    addEffect(new PlaySoundComponentEffect());
    addEffect(new RemoveEffectsComponentEffect());
    addEffect(new TeleportRandomlyComponentEffect());
  }

  /**
   * Creates a new stack based on the given material.
   *
   * @param material The material used for creating the stack.
   * @return The newly created stack.
   * @since 0.2.0.0
   */
  public abstract I createStack(final String material);

  /**
   * @return the version that is being used currently
   * @since 0.2.0.0
   */
  public abstract String version();

  /**
   * Adds default configurations or settings to be used by the implementing class.
   * @since 0.2.0.0
   */
  public abstract void addDefaults();

  /**
   * Retrieves the platform converter associated with the current item platform.
   *
   * @return The {@link PlatformConverter} instance used by the item platform.
   * @since 0.2.0.0
   */
  public PlatformConverter converter() {

    return converter;
  }

  /**
   * Retrieves the default provider for the item stack comparison.
   *
   * @return the default provider for the item stack comparison.
   * @since 0.2.0.0
   */
  public abstract @NotNull ItemProvider<S> defaultProvider();

  /**
   * Retrieves the identifier of the default provider for the item stack comparison.
   *
   * @return The identifier of the default provider for the item stack comparison.
   * @since 0.2.0.0
   */
  public abstract @NotNull String defaultProviderIdentifier();

  /**
   * Provides access to the calculations provider for performing various platform-specific operations.
   *
   * @return An instance of {@link CalculationsProvider} that handles calculations related to the item platform.
   * @since 0.2.0.0
   */
  public abstract CalculationsProvider<I, S, U> calculations();

  /**
   * Checks if any of the registered item providers are applicable to the given serialized item and item.
   *
   * @param serialized The serialized item stack to check against.
   * @param item The item to check for applicability.
   * @return True if an item provider is found that applies to the serialized item and item, otherwise false.
   * @since 0.2.0.0
   */
  public boolean providerApplies(final AbstractItemStack<? extends S> serialized, final S item) {

    for(final ItemProvider<S> provider : itemProviders.values()) {

      if(provider.identifier().equalsIgnoreCase(defaultProvider().identifier())) {
        continue;
      }

      if(provider.appliesTo(serialized, item)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Retrieves the item provider for the given itemProvider name, or returns the default provider if not found.
   *
   * @param itemProvider The name of the ItemProvider to retrieve.
   * @return The ItemProvider associated with the itemProvider name, or the default provider if not found.
   * @since 0.2.0.0
   */
  public ItemProvider<S> provider(final String itemProvider) {

    return itemProviders.getOrDefault(itemProvider, defaultProvider());
  }

  /**
   * Adds an ItemProvider to the ItemPlatform.
   *
   * @param provider The ItemProvider to add to the platform
   * @since 0.2.0.0
   */
  public void addItemProvider(final ItemProvider<S> provider) {

    itemProviders.put(provider.identifier(), provider);
  }

  /**
   * Adds a persistent data type to the item platform.
   *
   * @param identifier The identifier for the persistent data type.
   * @param type       The class representing the persistent data type.
   * @since 0.2.0.0
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
   * @since 0.2.0.0
   */
  public void addMulti(@NotNull final Object object) {

    if(object instanceof final ItemCheck<?> check) {

      try {

        if(!check.enabled(version())) {

          return;
        }

        if(check instanceof final LocaleItemCheck<?> localeItemCheck) {

          localeChecks.put(localeItemCheck.identifier(), (LocaleItemCheck<S>)localeItemCheck);
        } else {

          checks.put(check.identifier(), (ItemCheck<S>)check);
        }
      } catch(final Exception ignore) {
        //Just in case it passes the instance check, but the Generic is
        //incorrect for w.e reason, we want to fail safely.
      }
    }

    if(object instanceof final ItemApplicator<?, ?> applicator) {

      try {

        if(!applicator.enabled(version())) {

          return;
        }

        applicators.put(applicator.identifier(), (ItemApplicator<I, S>)applicator);
      } catch(final Exception ignore) {
        //Just in case it passes the instance check, but the Generic is
        //incorrect for w.e reason, we want to fail safely.
      }
    }

    if(object instanceof final ItemSerializer<?, ?> serializer) {

      try {

        if(!serializer.enabled(version())) {

          return;
        }

        serializers.put(serializer.identifier(), (ItemSerializer<I, S>)serializer);
      } catch(final Exception ignore) {
        //Just in case it passes the instance check, but the Generic is
        //incorrect for w.e reason, we want to fail safely.
      }
    }
  }

  /**
   * Converts the given locale stack to an instance of {@link AbstractItemStack}
   *
   * @param locale the locale to convert
   * @return the converted locale of type I
   * @since 0.2.0.0
   */
  public abstract I locale(final S locale);

  /**
   * @param check the {@link ItemCheck check} to add.
   * @since 0.2.0.0
   */
  public void addCheck(@NotNull final ItemCheck<S> check) {

    checks.put(check.identifier(), check);
  }

  /**
   * @param applicator the applicator to add
   * @since 0.2.0.0
   */
  public void addApplicator(@NotNull final ItemApplicator<I, S> applicator) {

    applicators.put(applicator.identifier(), applicator);
  }

  /**
   * @param serializer the deserializer to add
   * @since 0.2.0.0
   */
  public void addSerializer(@NotNull final ItemSerializer<I, S> serializer) {

    serializers.put(serializer.identifier(), serializer);
  }

  /**
   * Adds a ReviveEffect to the reviveEffects map.
   *
   * @param effect The ReviveEffect instance to add. Must not be null.
   * @since 0.2.0.0
   */
  public void addEffect(@NotNull final ComponentEffect effect) {

    // Add the effect's class to the map using its type as the key
    effects.put(effect.getType(), effect.getClass());
  }

  /**
   * Used to check if two locale stacks are comparable.
   * How they are performed:
   * - if a locale check applies, the check result is returned, true is the default return.
   *
   * @param original       the original stack
   * @param check          the stack to use for the check
   * @param disabledChecks the {@link ItemCheck#identifier()} check identifiers that should be
   *                       disabled for the check.
   *
   * @return True if the check passes, otherwise false.
   * @since 0.2.0.0
   */
  public boolean check(@NotNull final S original, @NotNull final S check, @NotNull final String... disabledChecks) {

    final List<String> disabled = Arrays.asList(disabledChecks);

    for(final LocaleItemCheck<S> localeCheck : localeChecks.values()) {

      if(disabled.contains(localeCheck.identifier())) {
        continue;
      }

      if(!localeCheck.enabled(version())) {
        continue;
      }

      if(!localeCheck.applies(original, check)) {
        continue;
      }
      return localeCheck.check(original, check);
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
   * @since 0.2.0.0
   */
  public boolean checkOrder(@NotNull final S original, @NotNull final S check, @NotNull final String... order) {

    for(final String id : order) {

      if(!checks.containsKey(id)) {
        continue;
      }


      final LocaleItemCheck<S> localeCheck = localeChecks.get(id);
      if(!localeCheck.enabled(version())) {
        continue;
      }

      if(!localeCheck.applies(original, check)) {
        continue;
      }
      return localeCheck.check(original, check);
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
   * @since 0.2.0.0
   */
  public boolean check(@NotNull final I original, @NotNull final I check, final String... disabledChecks) {

    final List<String> disabled = Arrays.asList(disabledChecks);
    for(final ItemCheck<S> checkItem : checks.values()) {

      System.out.println("Check: " + checkItem.identifier());

      if(disabled.contains(checkItem.identifier())) {
        continue;
      }

      if(!checkItem.enabled(version())) {
        continue;
      }

      if(!checkItem.applies(original, check)) {
        continue;
      }

      if(!checkItem.check(original, check)) {

        System.out.println("Failed check: " + checkItem.identifier());
        return false;
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
   * @since 0.2.0.0
   */
  public boolean checkOrder(@NotNull final I original, @NotNull final I check, @NotNull final String... order) {

    for(final String id : order) {

      if(!checks.containsKey(id)) {
        continue;
      }

      final ItemCheck<S> checkItem = checks.get(id);

      if(!checkItem.enabled(version())) {
        continue;
      }

      if(!checkItem.applies(original, check)) {
        continue;
      }

      if(!checkItem.check(original, check)) {
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
   * @since 0.2.0.0
   */
  public S apply(@NotNull final I serialized, @NotNull S item) {

    for(final ItemApplicator<I, S> applicator : applicators.values()) {

      System.out.println("Try applicator: " + applicator.identifier());

      if(applicator.enabled(version())) {

        System.out.println("Applicator ready to apply");

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
   * @since 0.2.0.0
   */
  public I serializer(@NotNull final S item, @NotNull I serialized) {

    for(final ItemSerializer<I, S> serializer : serializers.values()) {
      if(serializer.enabled(version())) {

        System.out.println("Serializer ready to apply: " + serializer.identifier());

        if(serializer instanceof final SerialComponent<I,S> component) {

          System.out.println("Serializer is component");
          if(!component.appliesTo(item)) {

            System.out.println("Serializer doesn't apply");

            continue;
          }
        }

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
   * @since 0.2.0.0
   */
  public abstract Optional<I> initSerialized(final JSONObject object);

  public static String componentString(@NotNull final Component component) {

    return PlainTextComponentSerializer.plainText().serialize(component);
  }

  public Map<String, Class<? extends ComponentEffect>> effects() {

    return effects;
  }
}