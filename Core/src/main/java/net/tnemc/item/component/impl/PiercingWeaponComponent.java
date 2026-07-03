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
import net.tnemc.item.component.helper.Sound;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * PiercingWeaponComponent
 * @see <a href="https://minecraft.wiki/w/Data_component_format#piercing_weapon">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class PiercingWeaponComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected boolean dealsKnockback = true;
  protected boolean dismounts = false;
  protected Sound sound;
  protected Sound hitSound;

  public PiercingWeaponComponent() {

  }

  public PiercingWeaponComponent(final boolean dealsKnockback,
                                 final boolean dismounts,
                                 final Sound sound,
                                 final Sound hitSound) {

    this.dealsKnockback = dealsKnockback;
    this.dismounts = dismounts;
    this.sound = sound;
    this.hitSound = hitSound;
  }

  @Override
  public String identifier() {

    return "piercing_weapon";
  }

  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneEleven(version);
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    json.put("dealsKnockback", dealsKnockback);
    json.put("dismounts", dismounts);

    if(sound != null) {
      json.put("sound", soundToJSON(sound));
    }

    if(hitSound != null) {
      json.put("hitSound", soundToJSON(hitSound));
    }

    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    final JSONObject object = json.getObject();

    if(object.containsKey("dealsKnockback")) {
      dealsKnockback(Boolean.parseBoolean(object.get("dealsKnockback").toString()));
    }

    if(object.containsKey("dismounts")) {
      dismounts(Boolean.parseBoolean(object.get("dismounts").toString()));
    }

    if(object.containsKey("sound")) {
      sound(soundFromJSON((JSONObject)object.get("sound")));
    }

    if(object.containsKey("hitSound")) {
      hitSound(soundFromJSON((JSONObject)object.get("hitSound")));
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final PiercingWeaponComponent<?, ?> other)) return false;

    return Objects.equals(this.dealsKnockback, other.dealsKnockback) &&
           Objects.equals(this.dismounts, other.dismounts) &&
           soundsEqual(this.sound, other.sound) &&
           soundsEqual(this.hitSound, other.hitSound);
  }

  @Override
  public int hashCode() {

    return Objects.hash(dealsKnockback, dismounts, soundHash(sound), soundHash(hitSound));
  }

  public boolean dealsKnockback() {

    return dealsKnockback;
  }

  public void dealsKnockback(final boolean dealsKnockback) {

    this.dealsKnockback = dealsKnockback;
  }

  public boolean dismounts() {

    return dismounts;
  }

  public void dismounts(final boolean dismounts) {

    this.dismounts = dismounts;
  }

  public Sound sound() {

    return sound;
  }

  public void sound(final Sound sound) {

    this.sound = sound;
  }

  public Sound hitSound() {

    return hitSound;
  }

  public void hitSound(final Sound hitSound) {

    this.hitSound = hitSound;
  }

  private JSONObject soundToJSON(final Sound sound) {

    final JSONObject json = new JSONObject();

    json.put("soundId", sound.sound());
    json.put("range", sound.range());

    return json;
  }

  private Sound soundFromJSON(final JSONObject json) {

    String soundId = null;
    float range = 0.0f;

    if(json.containsKey("soundId")) {
      soundId = json.get("soundId").toString();
    }

    if(json.containsKey("range")) {
      range = Float.parseFloat(json.get("range").toString());
    }

    return new Sound(soundId, range);
  }

  private boolean soundsEqual(final Sound first,
                              final Sound second) {

    if(first == null || second == null) return first == second;

    return Objects.equals(first.sound(), second.sound()) &&
           Objects.equals(first.range(), second.range());
  }

  private int soundHash(final Sound sound) {

    if(sound == null) return 0;
    return Objects.hash(sound.sound(), sound.range());
  }
}
