package net.tnemc.item.fabric.platform;
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

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.fabric.FabricItemCalculationsProvider;
import net.tnemc.item.fabric.FabricItemStack;
import net.tnemc.item.fabric.VanillaProvider;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.providers.CalculationsProvider;
import net.tnemc.item.providers.ItemProvider;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.util.Optional;

/**
 * FabricItemPlatform
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class FabricItemPlatform extends ItemPlatform<FabricItemStack, ItemStack, Inventory> {

  private static volatile FabricItemPlatform instance;

  protected final VanillaProvider defaultProvider = new VanillaProvider();
  protected final FabricItemCalculationsProvider calculationsProvider = new FabricItemCalculationsProvider();

  private FabricItemPlatform() {

    super();
  }

  public static FabricItemPlatform instance() {

    final FabricItemPlatform result = instance;
    if(result != null) {

      return result;
    }

    synchronized(FabricItemPlatform.class) {

      if(instance == null) {

        instance = new FabricItemPlatform();
        instance.addDefaults();
      }
      return instance;
    }
  }
  /**
   * Creates a new stack based on the given material.
   *
   * @param material The material used for creating the stack.
   *
   * @return The newly created stack.
   *
   * @since 0.2.0.0
   */
  @Override
  public FabricItemStack createStack(final String material) {

    return null;
  }

  /**
   * @return the version that is being used currently
   *
   * @since 0.2.0.0
   */
  @Override
  public String version() {

    return "";
  }

  /**
   * Adds default configurations or settings to be used by the implementing class.
   *
   * @since 0.2.0.0
   */
  @Override
  public void addDefaults() {

  }

  /**
   * Retrieves the default provider for the item stack comparison.
   *
   * @return the default provider for the item stack comparison.
   *
   * @since 0.2.0.0
   */
  @Override
  public @NotNull ItemProvider<ItemStack> defaultProvider() {

    return null;
  }

  /**
   * Retrieves the identifier of the default provider for the item stack comparison.
   *
   * @return The identifier of the default provider for the item stack comparison.
   *
   * @since 0.2.0.0
   */
  @Override
  public @NotNull String defaultProviderIdentifier() {

    return "";
  }

  /**
   * Provides access to the calculations provider for performing various platform-specific
   * operations.
   *
   * @return An instance of {@link CalculationsProvider} that handles calculations related to the
   * item platform.
   *
   * @since 0.2.0.0
   */
  @Override
  public CalculationsProvider<FabricItemStack, ItemStack, Inventory> calculations() {

    return null;
  }

  /**
   * Converts the given locale stack to an instance of {@link AbstractItemStack}
   *
   * @param locale the locale to convert
   *
   * @return the converted locale of type I
   *
   * @since 0.2.0.0
   */
  @Override
  public FabricItemStack locale(final ItemStack locale) {

    return null;
  }

  /**
   * Initializes and returns an AbstractItemStack object by deserializing the provided JSON object.
   *
   * @param object the JSON object to deserialize
   *
   * @return an initialized AbstractItemStack object
   *
   * @since 0.2.0.0
   */
  @Override
  public Optional<FabricItemStack> initSerialized(final JSONObject object) {

    return Optional.empty();
  }
}