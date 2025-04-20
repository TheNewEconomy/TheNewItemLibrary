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
 * InstrumentComponent
 *
 * @see <a href="https://minecraft.wiki/w/Data_component_format#instrument">Reference</a>
 * <p>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class InstrumentComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected String soundEvent;
  protected int useDuration;
  protected int range;

  public InstrumentComponent() {

  }

  public InstrumentComponent(final String soundEvent) {

    this.soundEvent = soundEvent;
  }

  public InstrumentComponent(final String soundEvent, final int useDuration) {

    this.soundEvent = soundEvent;
    this.useDuration = useDuration;
  }

  public InstrumentComponent(final String soundEvent, final int useDuration, final int range) {

    this.soundEvent = soundEvent;
    this.useDuration = useDuration;
    this.range = range;
  }

  @Override
  public String identifier() {
    return "instrument";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("sound_event", soundEvent);
    json.put("use_duration", useDuration);
    json.put("range", range);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    soundEvent = json.getString("sound_event");
    useDuration = json.getInteger("use_duration");
    range = json.getInteger("range");
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {
    if(!(component instanceof final InstrumentComponent<?, ?> other)) return false;

    return Objects.equals(this.soundEvent, other.soundEvent) &&
           this.useDuration == other.useDuration &&
           this.range == other.range;
  }

  @Override
  public int hashCode() {
    return Objects.hash(soundEvent, useDuration, range);
  }

  public String soundEvent() {

    return soundEvent;
  }

  public void soundEvent(final String soundEvent) {

    this.soundEvent = soundEvent;
  }

  public int useDuration() {

    return useDuration;
  }

  public void useDuration(final int useDuration) {

    this.useDuration = useDuration;
  }

  public int range() {

    return range;
  }

  public void range(final int range) {

    this.range = range;
  }
}