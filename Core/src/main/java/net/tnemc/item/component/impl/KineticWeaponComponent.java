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
import net.tnemc.item.component.helper.KineticWeaponCondition;
import net.tnemc.item.component.helper.Sound;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * KineticWeaponComponent
 * @see <a href="https://minecraft.wiki/w/Data_component_format#kinetic_weapon">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class KineticWeaponComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected int delayTicks = 0;
  protected KineticWeaponCondition damageConditions = new KineticWeaponCondition();
  protected KineticWeaponCondition dismountConditions = new KineticWeaponCondition();
  protected KineticWeaponCondition knockbackConditions = new KineticWeaponCondition();
  protected float forwardMovement = 0.0f;
  protected float damageMultiplier = 1.0f;
  protected Sound sound;
  protected Sound hitSound;

  public KineticWeaponComponent() {

  }

  public KineticWeaponComponent(final int delayTicks,
                                final KineticWeaponCondition damageConditions,
                                final KineticWeaponCondition dismountConditions,
                                final KineticWeaponCondition knockbackConditions,
                                final float forwardMovement,
                                final float damageMultiplier,
                                final Sound sound,
                                final Sound hitSound) {

    this.delayTicks = delayTicks;
    this.damageConditions = damageConditions;
    this.dismountConditions = dismountConditions;
    this.knockbackConditions = knockbackConditions;
    this.forwardMovement = forwardMovement;
    this.damageMultiplier = damageMultiplier;
    this.sound = sound;
    this.hitSound = hitSound;
  }

  @Override
  public String identifier() {

    return "kinetic_weapon";
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

    json.put("delayTicks", delayTicks);
    json.put("damageConditions", damageConditions.toJSON());
    json.put("dismountConditions", dismountConditions.toJSON());
    json.put("knockbackConditions", knockbackConditions.toJSON());
    json.put("forwardMovement", forwardMovement);
    json.put("damageMultiplier", damageMultiplier);

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

    if(object.containsKey("delayTicks")) {
      delayTicks(Integer.parseInt(object.get("delayTicks").toString()));
    }

    if(object.containsKey("damageConditions")) {
      damageConditions.readJSON((JSONObject)object.get("damageConditions"));
    }

    if(object.containsKey("dismountConditions")) {
      dismountConditions.readJSON((JSONObject)object.get("dismountConditions"));
    }

    if(object.containsKey("knockbackConditions")) {
      knockbackConditions.readJSON((JSONObject)object.get("knockbackConditions"));
    }

    if(object.containsKey("forwardMovement")) {
      forwardMovement(Float.parseFloat(object.get("forwardMovement").toString()));
    }

    if(object.containsKey("damageMultiplier")) {
      damageMultiplier(Float.parseFloat(object.get("damageMultiplier").toString()));
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

    if(!(component instanceof final KineticWeaponComponent<?, ?> other)) return false;

    return Objects.equals(this.delayTicks, other.delayTicks) &&
           conditionsEqual(this.damageConditions, other.damageConditions) &&
           conditionsEqual(this.dismountConditions, other.dismountConditions) &&
           conditionsEqual(this.knockbackConditions, other.knockbackConditions) &&
           Objects.equals(this.forwardMovement, other.forwardMovement) &&
           Objects.equals(this.damageMultiplier, other.damageMultiplier) &&
           soundsEqual(this.sound, other.sound) &&
           soundsEqual(this.hitSound, other.hitSound);
  }

  @Override
  public int hashCode() {

    return Objects.hash(delayTicks,
                        damageConditions.toJSON(),
                        dismountConditions.toJSON(),
                        knockbackConditions.toJSON(),
                        forwardMovement,
                        damageMultiplier,
                        soundHash(sound),
                        soundHash(hitSound));
  }

  public int delayTicks() {

    return delayTicks;
  }

  public void delayTicks(final int delayTicks) {

    this.delayTicks = delayTicks;
  }

  public KineticWeaponCondition damageConditions() {

    return damageConditions;
  }

  public void damageConditions(final KineticWeaponCondition damageConditions) {

    this.damageConditions = damageConditions;
  }

  public KineticWeaponCondition dismountConditions() {

    return dismountConditions;
  }

  public void dismountConditions(final KineticWeaponCondition dismountConditions) {

    this.dismountConditions = dismountConditions;
  }

  public KineticWeaponCondition knockbackConditions() {

    return knockbackConditions;
  }

  public void knockbackConditions(final KineticWeaponCondition knockbackConditions) {

    this.knockbackConditions = knockbackConditions;
  }

  public float forwardMovement() {

    return forwardMovement;
  }

  public void forwardMovement(final float forwardMovement) {

    this.forwardMovement = forwardMovement;
  }

  public float damageMultiplier() {

    return damageMultiplier;
  }

  public void damageMultiplier(final float damageMultiplier) {

    this.damageMultiplier = damageMultiplier;
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

    json.put("sound", sound.sound());
    json.put("range", sound.range());

    return json;
  }

  private Sound soundFromJSON(final JSONObject json) {

    String sound = null;
    float range = 0.0f;

    if(json.containsKey("sound")) {
      sound = json.get("sound").toString();
    }

    if(json.containsKey("range")) {
      range = Float.parseFloat(json.get("range").toString());
    }

    return new Sound(sound, range);
  }

  private boolean conditionsEqual(final KineticWeaponCondition first,
                                  final KineticWeaponCondition second) {

    if(first == null || second == null) return first == second;
    return Objects.equals(first.toJSON(), second.toJSON());
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
