package net.tnemc.item.component.helper;
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

/**
 * DyeColor
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public enum DyeColor {

  WHITE("white"),
  ORANGE("orange"),
  MAGENTA("magenta"),
  LIGHT_BLUE("light_blue"),
  YELLOW("yellow"),
  LIME("lime"),
  PINK("pink"),
  GRAY("gray"),
  LIGHT_GRAY("light_gray"),
  CYAN("cyan"),
  PURPLE("purple"),
  BLUE("blue"),
  BROWN("brown"),
  GREEN("green"),
  RED("red"),
  BLACK("black");

  private final String id;

  DyeColor(final String id) {
    this.id = id;
  }

  public String id() {
    return id;
  }

  public static DyeColor fromId(final String id) {
    for(final DyeColor color : values()) {
      if(color.id.equalsIgnoreCase(id)) {
        return color;
      }
    }

    throw new IllegalArgumentException("Unknown dye color: " + id);
  }
}
