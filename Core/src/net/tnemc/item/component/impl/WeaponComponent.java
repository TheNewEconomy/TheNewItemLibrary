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
 * WeaponComponent - as of MC Snapshot 25w02a
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#weapon">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class WeaponComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected int damagePerAttack = 1;
  protected boolean canDisableBlocking = false;

  public WeaponComponent() {

  }

  public WeaponComponent(final int damagePerAttack) {

    this.damagePerAttack = damagePerAttack;
  }

  public WeaponComponent(final boolean canDisableBlocking) {

    this.canDisableBlocking = canDisableBlocking;
  }

  public WeaponComponent(final int damagePerAttack, final boolean canDisableBlocking) {

    this.damagePerAttack = damagePerAttack;
    this.canDisableBlocking = canDisableBlocking;
  }

  /**
   * @return the type of component this is.
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {

    return "weapon";
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
    json.put("damage_per_attack", damagePerAttack);
    json.put("can_disable_blocking", canDisableBlocking);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    damagePerAttack = json.getInteger("damage_per_attack");
    canDisableBlocking = json.getBoolean("can_disable_blocking");
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if(!(component instanceof final WeaponComponent<?, ?> other)) return false;
    return this.damagePerAttack == other.damagePerAttack && this.canDisableBlocking == other.canDisableBlocking;
  }

  @Override
  public int hashCode() {
    return Objects.hash(damagePerAttack, canDisableBlocking);
  }

  public int damagePerAttack() {

    return damagePerAttack;
  }

  public void damagePerAttack(final int damagePerAttack) {

    this.damagePerAttack = damagePerAttack;
  }

  public boolean canDisableBlocking() {

    return canDisableBlocking;
  }

  public void canDisableBlocking(final boolean canDisableBlocking) {

    this.canDisableBlocking = canDisableBlocking;
  }
}