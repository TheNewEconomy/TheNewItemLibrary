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
import net.tnemc.item.SerialItemData;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * JukeBoxComponent
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public abstract class JukeBoxComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected String song;

  public JukeBoxComponent() {

  }

  public JukeBoxComponent(final String song) {

    this.song = song;
  }

  /**
   * @return the type of component this is.
   */
  @Override
  public String identifier() {

    return "jukebox";
  }

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject jukebox = new JSONObject();
    jukebox.put("name", "jukebox-component");

    return jukebox;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {

    song = json.getString("song");
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data. For instance, book copies will return false when compared to the original.
   *
   * @param component The component to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean equals(final SerialComponent<I, T> component) {

    if(component instanceof final JukeBoxComponent<?, ?> jukeBox) {

      return Objects.equals(jukeBox.song, this.song);
    }
    return false;
  }

  public String song() {

    return song;
  }

  public void song(final String song) {

    this.song = song;
  }
}