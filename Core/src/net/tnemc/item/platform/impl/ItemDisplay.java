package net.tnemc.item.platform.impl;
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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.platform.applier.ItemApplicator;
import net.tnemc.item.platform.check.ItemCheck;
import net.tnemc.item.platform.deserialize.ItemDeserializer;

/**
 * ItemDisplay
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public abstract class ItemDisplay<I extends AbstractItemStack<T>, T> implements ItemCheck<T>, ItemApplicator<I, T>, ItemDeserializer<I, T> {

  /**
   * @return the identifier for this check.
   */
  @Override
  public String identifier() {
    return "display";
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   */
  @Override
  public boolean enabled(String version) {
    return true;
  }

  /**
   * @param original the original stack
   * @param check    the stack to use for the check
   *
   * @return True if the check passes, otherwise false.
   */
  @Override
  public boolean check(AbstractItemStack<T> original, AbstractItemStack<T> check) {
    return ItemPlatform.componentString(original.display()).equals(ItemPlatform.componentString(check.display()));
  }
}