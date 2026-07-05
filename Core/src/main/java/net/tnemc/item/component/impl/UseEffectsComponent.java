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
import net.tnemc.item.JSONHelper;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * UseEffectsComponent
 * @see <a href="https://minecraft.wiki/w/Data_component_format#use_effects">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class UseEffectsComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  //If the player can sprint during use. Defaults to false.
  protected boolean canSprint = false;
  //A ranged float from 0.0 to 1.0 inclusive speed multiplier inflicted during use. Defaults to 0.2.
  protected float speedMultiplier = 0.2f;
  //If using the item should trigger interaction vibrations. Defaults to true.
  protected boolean interactVibrations = true;

  public UseEffectsComponent() {

  }

  public UseEffectsComponent(final boolean canSprint,
                             final float speedMultiplier,
                             final boolean interactVibrations) {

    this.canSprint = canSprint;
    speedMultiplier(speedMultiplier);
    this.interactVibrations = interactVibrations;
  }

  @Override
  public String identifier() {

    return "use_effects";
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

    return VersionUtil.isOneTwentyOneEleven(version);
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    json.put("canSprint", canSprint);
    json.put("speedMultiplier", speedMultiplier);
    json.put("interactVibrations", interactVibrations);

    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    canSprint(json.getBoolean("canSprint"));
    speedMultiplier(json.getFloat("speedMultiplier"));
    interactVibrations(json.getBoolean("interactVibrations"));
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final UseEffectsComponent<?, ?> other)) return false;

    return this.canSprint == other.canSprint &&
           Float.compare(this.speedMultiplier, other.speedMultiplier) == 0 &&
           this.interactVibrations == other.interactVibrations;
  }

  @Override
  public int hashCode() {

    return Objects.hash(canSprint, speedMultiplier, interactVibrations);
  }

  public boolean canSprint() {

    return canSprint;
  }

  public void canSprint(final boolean canSprint) {

    this.canSprint = canSprint;
  }

  public float speedMultiplier() {

    return speedMultiplier;
  }

  public void speedMultiplier(final float speedMultiplier) {

    if(speedMultiplier < 0.0f || speedMultiplier > 1.0f) {
      throw new IllegalArgumentException("speedMultiplier must be between 0.0 and 1.0.");
    }

    this.speedMultiplier = speedMultiplier;
  }

  public boolean interactVibrations() {

    return interactVibrations;
  }

  public void interactVibrations(final boolean interactVibrations) {

    this.interactVibrations = interactVibrations;
  }
}