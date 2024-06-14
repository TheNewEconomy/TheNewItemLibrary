package net.tnemc.item.providers;

/*
 * The New Item Library Minecraft Server Plugin
 *
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

import java.util.Collection;

/**
 * HelperMethods
 *
 * @author creatorfromhell
 * @since 0.1.7.5-Pre-2
 */
public interface HelperMethods {

  /**
   * Returns a collection of materials.
   *
   * @return a collection of materials
   */
  Collection<String> materials();

  /**
   * Returns a collection of enchantments.
   *
   * @return a collection of enchantments
   */
  Collection<String> enchantments();

  /**
   * Returns a collection of flags.
   *
   * @return a collection of flags
   */
  Collection<String> flags();
}