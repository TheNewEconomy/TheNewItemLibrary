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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * FireworksComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class FireworksComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<ExplosionData> explosions = new ArrayList<>();
  protected byte flightDuration;

  public FireworksComponent() {

  }

  public FireworksComponent(final byte flightDuration) {

    this.flightDuration = flightDuration;
  }

  public FireworksComponent(final byte flightDuration, final List<ExplosionData> explosions) {

    this.flightDuration = flightDuration;
    this.explosions.addAll(explosions);
  }

  @Override
  public String identifier() {
    return "fireworks";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();

    final JSONArray explosionsArray = new JSONArray();
    for(final ExplosionData explosion : explosions) {

      explosionsArray.add(explosion.toJSON());
    }
    json.put("explosions", explosionsArray);
    json.put("flight_duration", flightDuration);

    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    explosions.clear();

    final JSONArray explosionsArray = (JSONArray) json.getObject().get("explosions");
    if(explosionsArray != null) {
      for(final Object obj : explosionsArray) {

        final ExplosionData explosion = new ExplosionData();
        explosion.readJSON(new JSONHelper((JSONObject) obj));
        explosions.add(explosion);
      }
    }

    if(json.has("flight_duration")) {
      flightDuration = Byte.parseByte(json.getString("flight_duration"));
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {
    if(!(component instanceof final FireworksComponent<?, ?> other)) return false;

    return this.flightDuration == other.flightDuration &&
           Objects.equals(this.explosions, other.explosions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(explosions, flightDuration);
  }

  public List<ExplosionData> explosions() {

    return explosions;
  }

  public void explosions(final List<ExplosionData> explosions) {

    this.explosions.clear();
    this.explosions.addAll(explosions);
  }

  public void explosions(final ExplosionData... explosions) {

    this.explosions.clear();
    this.explosions.addAll(Arrays.asList(explosions));
  }

  public byte flightDuration() {

    return flightDuration;
  }

  public void flightDuration(final byte flightDuration) {

    this.flightDuration = flightDuration;
  }
}
