package net.tnemc.item.component.impl;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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
import net.tnemc.item.providers.VersionUtil;

/**
 * SulfurCubeContentComponent
 * @see <a href="https://minecraft.wiki/w/Data_component_format#sulfur_cube_content">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class SulfurCubeContentComponent<I extends AbstractItemStack<T>, T> extends SingleItemContainerComponent<I, T> {

  public SulfurCubeContentComponent() {

  }

  public SulfurCubeContentComponent(final I item) {

    super(item);
  }

  @Override
  public String identifier() {

    return "sulfur_cube_content";
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isTwentySixTwo(version);
  }
}