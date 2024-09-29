package net.tnemc.sponge.platform;
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

import net.tnemc.item.SerialItemData;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.sponge.SpongeItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

/**
 * SpongeItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class SpongeItemPlatform extends ItemPlatform<SpongeItemStack, ItemStack> {

  public static final SpongeItemPlatform PLATFORM = new SpongeItemPlatform();

  private SpongeItemPlatform() {

    super();
  }

  /**
   * @return the version that is being used currently
   */
  @Override
  public String version() {

    return Sponge.game().platform().minecraftVersion().name();
  }

  @Override
  public void addDefaults() {

  }

  /**
   * Initializes and returns an AbstractItemStack object by deserializing the provided JSON object.
   *
   * @param object the JSON object to deserialize
   *
   * @return an initialized AbstractItemStack object
   */
  @Override
  public Optional<SpongeItemStack> initSerialized(final JSONObject object) {

    try {
      return Optional.ofNullable(new SpongeItemStack().of(object));
    } catch(final ParseException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<SerialItemData<ItemStack>> parseMeta(final ItemStack stack) {

    return Optional.empty();
  }
}