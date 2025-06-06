package net.tnemc.sponge.platform;
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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.providers.ItemProvider;
import net.tnemc.sponge.SpongeItemCalculationsProvider;
import net.tnemc.sponge.SpongeItemStack;
import net.tnemc.sponge.VanillaProvider;
import net.tnemc.sponge.platform.impl.SpongeBundleComponent;
import net.tnemc.sponge.platform.impl.SpongeContainerComponent;
import net.tnemc.sponge.platform.impl.SpongeCustomNameComponent;
import net.tnemc.sponge.platform.impl.SpongeDamageComponent;
import net.tnemc.sponge.platform.impl.SpongeEnchantmentsComponent;
import net.tnemc.sponge.platform.impl.SpongeItemModelComponent;
import net.tnemc.sponge.platform.impl.SpongeItemNameComponent;
import net.tnemc.sponge.platform.impl.SpongeLoreComponent;
import net.tnemc.sponge.platform.impl.SpongeMaxStackComponent;
import net.tnemc.sponge.platform.impl.SpongeModelDataComponent;
import net.tnemc.sponge.platform.impl.SpongeModelDataOldComponent;
import net.tnemc.sponge.platform.impl.SpongeProfileComponent;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

/**
 * SpongeItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class SpongeItemPlatform extends ItemPlatform<SpongeItemStack, ItemStack, Inventory> {

  private static volatile SpongeItemPlatform instance;

  protected final VanillaProvider defaultProvider = new VanillaProvider();
  protected final SpongeItemCalculationsProvider calculationsProvider = new SpongeItemCalculationsProvider();

  private SpongeItemPlatform() {

    super();
  }

  @Override
  public SpongeItemStack createStack(final String material) {
    return new SpongeItemStack().of(material, 1);
  }

  public static SpongeItemPlatform instance() {

    final SpongeItemPlatform result = instance;
    if(result != null) {

      return result;
    }

    synchronized(SpongeItemPlatform.class) {

      if(instance == null) {

        instance = new SpongeItemPlatform();
        instance.addDefaults();
      }
      return instance;
    }
  }

  /**
   * @return the version that is being used currently
   * @since 0.2.0.0
   */
  @Override
  public String version() {

    return Sponge.game().platform().minecraftVersion().name();
  }

  @Override
  public void addDefaults() {

    registerConversions();

    addMulti(new SpongeBundleComponent());
    addMulti(new SpongeContainerComponent());
    addMulti(new SpongeCustomNameComponent());
    addMulti(new SpongeDamageComponent());
    addMulti(new SpongeEnchantmentsComponent());
    addMulti(new SpongeItemModelComponent());
    addMulti(new SpongeItemNameComponent());
    addMulti(new SpongeLoreComponent());
    addMulti(new SpongeMaxStackComponent());
    addMulti(new SpongeModelDataComponent());
    addMulti(new SpongeModelDataOldComponent());
    addMulti(new SpongeProfileComponent());

    addItemProvider(defaultProvider);
  }

  /**
   * Retrieves the default provider for the item stack comparison.
   *
   * @return the default provider for the item stack comparison.
   * @since 0.2.0.0
   */
  @Override
  public @NotNull ItemProvider<ItemStack> defaultProvider() {

    return defaultProvider;
  }

  /**
   * Retrieves the identifier of the default provider for the item stack comparison.
   *
   * @return The identifier of the default provider for the item stack comparison.
   * @since 0.2.0.0
   */
  @Override
  public @NotNull String defaultProviderIdentifier() {

    return defaultProvider.identifier();
  }

  @Override
  public SpongeItemCalculationsProvider calculations() {
    return calculationsProvider;
  }

  /**
   * Converts the given locale stack to an instance of {@link AbstractItemStack}
   *
   * @param locale the locale to convert
   *
   * @return the converted locale of type I
   * @since 0.2.0.0
   */
  @Override
  public SpongeItemStack locale(final ItemStack locale) {

    return new SpongeItemStack().of(locale);
  }

  private void registerConversions() {

    //TODO: Convert over conversions.
  }

  /**
   * Initializes and returns an AbstractItemStack object by deserializing the provided JSON object.
   *
   * @param object the JSON object to deserialize
   *
   * @return an initialized AbstractItemStack object
   * @since 0.2.0.0
   */
  @Override
  public Optional<SpongeItemStack> initSerialized(final JSONObject object) {

    try {
      return Optional.ofNullable(new SpongeItemStack().of(object));
    } catch(final ParseException e) {
      return Optional.empty();
    }
  }
}
