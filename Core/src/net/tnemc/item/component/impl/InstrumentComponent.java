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
 * @see <a href="https://minecraft.wiki/w/Data_component_format#item_model">Reference</a>
 * <p>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class InstrumentComponent<T> implements SerialComponent<T> {

  protected String soundEvent;
  protected int useDuration;
  protected int range;

  @Override
  public String getType() {
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
  public <I extends AbstractItemStack<T>> void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    soundEvent = json.getString("sound_event");
    useDuration = json.getInteger("use_duration");
    range = json.getInteger("range");
  }

  @Override
  public boolean equals(final SerialComponent<? extends T> component) {
    if (!(component instanceof final InstrumentComponent<?> other)) return false;

    return Objects.equals(this.soundEvent, other.soundEvent) &&
           this.useDuration == other.useDuration &&
           this.range == other.range;
  }

  @Override
  public int hashCode() {
    return Objects.hash(soundEvent, useDuration, range);
  }
}