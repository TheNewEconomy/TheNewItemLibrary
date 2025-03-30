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
import net.tnemc.item.providers.SkullProfile;
import org.json.simple.JSONObject;

import java.util.Objects;
import java.util.UUID;

/**
 * ProfileComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class ProfileComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected SkullProfile profile;

  public ProfileComponent() {

  }

  public ProfileComponent(final SkullProfile profile) {

    this.profile = profile;
  }

  /**
   * @return the type of component this is.
   */
  @Override
  public String identifier() {

    return "profile";
  }

  /**
   * Converts the {@link SerialComponent} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialComponent}.
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    if(profile != null) {
      if(profile.getName() != null) json.put("name", profile.getName());
      if(profile.getUuid() != null) json.put("uuid", profile.getUuid());
      if(profile.getTexture() != null) json.put("texture", profile.getTexture());
    }
    return json;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialComponent} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {

    profile = new SkullProfile();
    if(json.has("name")) profile.setName(json.getString("name"));

    try {

      if(json.has("uuid")) profile.setUuid(UUID.fromString(json.getString("uuid")));

    } catch(final Exception ignore) { }

    if(json.has("texture")) profile.setTexture(json.getString("texture"));
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

    if(!(component instanceof final ProfileComponent<?, ?> other)) return false;
    if(profile != null) {

      if(other.profile == null) return false;

      return profile.equals(other.profile);
    }

    return other.profile == null;
  }

  @Override
  public int hashCode() {

    return Objects.hash(false);
  }

  public SkullProfile profile() {

    return profile;
  }

  public void profile(final SkullProfile profile) {

    this.profile = profile;
  }

  public void profile(final String name, final UUID uuid) {

    this.profile = new SkullProfile(name, uuid);
  }

  public void profile(final String name, final UUID uuid, final String texture) {

    this.profile = new SkullProfile(name, uuid, texture);
  }
}