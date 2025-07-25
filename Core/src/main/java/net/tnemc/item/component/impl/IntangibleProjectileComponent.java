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
import net.tnemc.item.JSONHelper;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * IntangibleProjectileComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#intangible_projectile">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class IntangibleProjectileComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  public IntangibleProjectileComponent() {

  }

  @Override
  public String identifier() {

    return "intangible_projectile";
  }

  @Override
  public JSONObject toJSON() {

    return new JSONObject(); // Empty JSON as no additional data is needed
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    // Nothing to read since the component does not contain additional data
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    return component instanceof IntangibleProjectileComponent;
  }

  @Override
  public int hashCode() {

    return Objects.hash(identifier());
  }
}