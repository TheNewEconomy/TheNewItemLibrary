package net.tnemc.item.component.helper.effect;
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

import java.util.Objects;

/**
 * ReviveEffect
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class ComponentEffect {

  protected float probability = 1.0f; // Default to 100% chance

  public abstract String getType();

  public abstract JSONObject toJSON();

  public abstract void readJSON(JSONHelper json);

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof final ComponentEffect other)) return false;

    return Float.compare(this.probability, other.probability) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(probability);
  }
}
