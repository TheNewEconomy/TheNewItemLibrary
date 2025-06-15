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
 * PotionDurationScaleComponent - as of MC Snapshot 25w02a
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#potion_duration_scale">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class PotionDurationScaleComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected float potionDuration = 1.0f;

  public PotionDurationScaleComponent() {

  }

  public PotionDurationScaleComponent(final float potionDuration) {

    this.potionDuration = potionDuration;
  }

  /**
   * @return the type of component this is.
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {

    return "potion_duration_scale";
  }

  /**
   * Converts the {@link SerialComponent} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialComponent}.
   * @since 0.2.0.0
   */
  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("potion_duration", potionDuration);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    potionDuration = json.getFloat("potion_duration");
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {
    if(!(component instanceof final PotionDurationScaleComponent<?, ?> other)) return false;
    return Float.compare(this.potionDuration, other.potionDuration) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(potionDuration);
  }

  public float potionDuration() {

    return potionDuration;
  }

  public void potionDuration(final float potionDuration) {

    this.potionDuration = potionDuration;
  }
}