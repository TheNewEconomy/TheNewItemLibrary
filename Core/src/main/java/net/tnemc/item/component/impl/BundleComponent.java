package net.tnemc.item.component.impl;
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

import java.util.Map;

/**
 * BundleComponent - The items stored inside this bundle.
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#bundle_contents">Reference</a>
 * @since 0.2.0.0
 */
public abstract class BundleComponent<I extends AbstractItemStack<T>, T> extends ContainerComponent<I, T> {

  public BundleComponent() {

    super();
  }

  public BundleComponent(final Map<Integer, AbstractItemStack<T>> items) {

    super(items);
  }

  @Override
  public String identifier() {

    return "bundle";
  }
}