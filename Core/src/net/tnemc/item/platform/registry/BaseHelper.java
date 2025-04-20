package net.tnemc.item.platform.registry;
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
import java.util.LinkedList;
import java.util.Map;

/**
 * BaseHelper
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class BaseHelper {

  protected final Map<String, RegistryHandler> registryHandlers = new HashMap<>();

  public void initializeHandlers() {
    registryHandlers.values().forEach(RegistryHandler::initialize);
  }

  public RegistryHandler getHandler(final String identifier) {
    return registryHandlers.get(identifier);
  }

  public void registerHandler(final String identifier, final RegistryHandler handler) {
    registryHandlers.put(identifier, handler);
  }

  public LinkedList<String> getKeys(final String identifier) {

    if(registryHandlers.containsKey(identifier)) {
      return registryHandlers.get(identifier).keys;
    }
    return new LinkedList<>();
  }

  /**
   * Returns a collection of materials.
   *
   * @return a collection of materials
   * @since 0.2.0.0
   */
  public LinkedList<String> materials() {

    return getKeys("materials");
  }

  /**
   * Returns a collection of enchantments.
   *
   * @return a collection of enchantments
   * @since 0.2.0.0
   */
  public LinkedList<String> enchantments() {

    return getKeys("enchantments");
  }

  /**
   * Returns a collection of flags.
   *
   * @return a collection of flags
   * @since 0.2.0.0
   */
  public LinkedList<String> flags() {

    return getKeys("flags");
  }
}