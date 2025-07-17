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
import net.tnemc.item.component.helper.ExplosionData;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Objects;

/**
 * FireworkExplosionComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class FireworkExplosionComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected ExplosionData explosion;

  public FireworkExplosionComponent() {

  }

  public FireworkExplosionComponent(final ExplosionData explosion) {

    this.explosion = explosion;
  }

  public FireworkExplosionComponent(final String shape, final boolean hasTrail,
                                    final boolean hasTwinkle, final List<Integer> colors,
                                    final List<Integer> fadeColors) {

    this.explosion = new ExplosionData(shape, hasTrail, hasTwinkle, colors, fadeColors);
  }

  @Override
  public String identifier() {

    return "firework_explosion";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("explosion", explosion.toJSON());
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    if(json.has("explosion")) {
      explosion.readJSON(json.getHelper("explosion"));
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final FireworkExplosionComponent<?, ?> other)) return false;

    return Objects.equals(this.explosion, other.explosion);
  }

  @Override
  public int hashCode() {

    return Objects.hash(explosion);
  }

  public ExplosionData explosion() {

    return explosion;
  }

  public void explosion(final ExplosionData explosion) {

    this.explosion = explosion;
  }
}