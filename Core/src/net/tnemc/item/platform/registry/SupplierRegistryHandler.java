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

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.function.Supplier;

/**
 * SupplierRegistryHandler extends RegistryHandler and handles the initialization of keys using a Supplier.
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */

/**
 */
public class SupplierRegistryHandler extends RegistryHandler {

  protected final Supplier<LinkedList<String>> keysSupplier;

  public SupplierRegistryHandler(final @NotNull Supplier<LinkedList<String>> keysSupplier) {

    this.keysSupplier = keysSupplier;
  }

  @Override
  public void initialize() {

    keys.addAll(keysSupplier.get());
  }
}