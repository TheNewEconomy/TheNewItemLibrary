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
 * MinimumAttackChargeComponent
 * @see <a href="https://minecraft.wiki/w/Data_component_format#minimum_attack_charge">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class MinimumAttackChargeComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected float minimumCharge = 1.0f;

  public MinimumAttackChargeComponent() {

  }

  public MinimumAttackChargeComponent(final float minimumCharge) {
    this.minimumCharge = minimumCharge;
  }

  @Override
  public String identifier() {

    return "minimum_attack_charge";
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
    json.put("minimumCharge", minimumCharge);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    minimumCharge(json.getFloat("minimumCharge"));
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final MinimumAttackChargeComponent<?, ?> other)) return false;
    return Objects.equals(this.minimumCharge, other.minimumCharge);
  }

  @Override
  public int hashCode() {

    return Objects.hash(minimumCharge);
  }

  public float minimumCharge() {

    return minimumCharge;
  }

  public void minimumCharge(final float minimumCharge) {

    this.minimumCharge = minimumCharge;
  }
}
