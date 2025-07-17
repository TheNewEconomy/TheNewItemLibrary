package net.tnemc.item.component.helper;
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

/**
 * PatternData
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PatternData {

  protected String color;
  protected String pattern;

  public PatternData(final String color, final String pattern) {

    this.color = color;
    this.pattern = pattern;
  }

  public String getColor() {

    return color;
  }

  public void setColor(final String color) {

    this.color = color;
  }

  public String getPattern() {

    return pattern;
  }

  public void setPattern(final String pattern) {

    this.pattern = pattern;
  }

  @Override
  public PatternData clone() throws CloneNotSupportedException {

    return new PatternData(this.color, this.pattern);
  }
}