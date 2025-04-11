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

import net.tnemc.item.JSONHelper;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * ExplosionData
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class ExplosionData {

  protected String shape; // small_ball, large_ball, star, creeper, burst
  protected final List<Integer> colors = new ArrayList<>();
  protected final List<Integer> fadeColors = new ArrayList<>();
  protected boolean hasTrail;
  protected boolean hasTwinkle;

  public ExplosionData() {

  }

  public ExplosionData(final String shape, final boolean hasTrail, final boolean hasTwinkle) {

    this.shape = shape;
    this.hasTrail = hasTrail;
    this.hasTwinkle = hasTwinkle;
  }

  public ExplosionData(final String shape, final boolean hasTrail, final boolean hasTwinkle, final List<Integer> colors, final List<Integer> fadeColors) {

    this.shape = shape;
    this.hasTrail = hasTrail;
    this.hasTwinkle = hasTwinkle;
    this.colors.addAll(colors);
    this.fadeColors.addAll(fadeColors);
  }

  /**
   * Converts this explosion data to a JSON object.
   *
   * @return The JSONObject representing this explosion data.
   * @since 0.2.0.0
   */
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("shape", shape);
    json.put("colors", colors);
    json.put("fade_colors", fadeColors);
    json.put("has_trail", hasTrail);
    json.put("has_twinkle", hasTwinkle);
    return json;
  }

  /**
   * Reads JSON data and populates this explosion data.
   *
   * @param json The JSONHelper instance of the JSON data.
   * @since 0.2.0.0
   */
  public void readJSON(final JSONHelper json) {
    shape = json.getString("shape");

    colors.clear();
    colors.addAll(json.getIntegerList("colors"));

    fadeColors.clear();
    fadeColors.addAll(json.getIntegerList("fade_colors"));

    hasTrail = json.getBoolean("has_trail");
    hasTwinkle = json.getBoolean("has_twinkle");
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof final ExplosionData other)) return false;

    return Objects.equals(this.shape, other.shape) &&
           Objects.equals(this.colors, other.colors) &&
           Objects.equals(this.fadeColors, other.fadeColors) &&
           this.hasTrail == other.hasTrail &&
           this.hasTwinkle == other.hasTwinkle;
  }

  @Override
  public int hashCode() {
    return Objects.hash(shape, colors, fadeColors, hasTrail, hasTwinkle);
  }

  public String shape() {

    return shape;
  }

  public void shape(final String shape) {

    this.shape = shape;
  }

  public List<Integer> colors() {

    return colors;
  }

  public void colors(final List<Integer> colors) {

    this.colors.clear();
    this.colors.addAll(colors);
  }

  public void colors(final Integer... colors) {

    this.colors.clear();
    this.colors.addAll(Arrays.asList(colors));
  }

  public List<Integer> fadeColors() {

    return fadeColors;
  }

  public void fadeColors(final List<Integer> colors) {

    this.fadeColors.clear();
    this.fadeColors.addAll(colors);
  }

  public void fadeColors(final Integer... colors) {

    this.fadeColors.clear();
    this.fadeColors.addAll(Arrays.asList(colors));
  }

  public boolean hasTrail() {

    return hasTrail;
  }

  public void hasTrail(final boolean hasTrail) {

    this.hasTrail = hasTrail;
  }

  public boolean hasTwinkle() {

    return hasTwinkle;
  }

  public void hasTwinkle(final boolean hasTwinkle) {

    this.hasTwinkle = hasTwinkle;
  }
}