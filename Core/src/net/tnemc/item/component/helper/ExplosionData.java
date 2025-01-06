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

  /**
   * Converts this explosion data to a JSON object.
   *
   * @return The JSONObject representing this explosion data.
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
}