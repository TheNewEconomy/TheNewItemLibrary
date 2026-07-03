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
 * DamageTypeComponent
 *
 * Specifies the damage type this item deals.
 *
 * @see <a href="https://minecraft.wiki/w/Data_component_format#damage_type">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class DamageTypeComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected String damageType = "minecraft:generic";

  public DamageTypeComponent() {

  }

  public DamageTypeComponent(final String damageType) {

    this.damageType = damageType;
  }

  @Override
  public String identifier() {

    return "damage_type";
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

    json.put("damageType", damageType);

    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    damageType(json.getString("damageType"));
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final DamageTypeComponent<?, ?> other)) return false;
    return Objects.equals(this.damageType, other.damageType);
  }

  @Override
  public int hashCode() {

    return Objects.hash(damageType);
  }

  public String damageType() {

    return damageType;
  }

  public void damageType(final String damageType) {

    this.damageType = damageType;
  }
}
