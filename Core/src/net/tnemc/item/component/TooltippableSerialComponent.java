package net.tnemc.item.component;
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
import net.tnemc.item.JSONHelper;
import net.tnemc.item.platform.ItemPlatform;

/**
 * TooltippableSerialComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class TooltippableSerialComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected boolean showInTooltip = true;

  /**
   * Reads JSON data and converts it back to a {@link SerialComponent} object.
   *
   * @param json     The JSONHelper instance of the json data.
   * @param platform The {@link ItemPlatform platform} instance.
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {

    showInTooltip = json.getBoolean("showInTooltip");
  }

  public boolean isShowInTooltip() {

    return showInTooltip;
  }

  public void setShowInTooltip(final boolean showInTooltip) {

    this.showInTooltip = showInTooltip;
  }
}