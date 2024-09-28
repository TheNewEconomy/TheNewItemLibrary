package net.tnemc.item.providers;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import java.util.Objects;
import java.util.UUID;

/**
 * SkullProfile
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class SkullProfile {

  protected String name = null;
  protected UUID uuid = null;
  protected String texture = null;

  public SkullProfile() {

  }

  public SkullProfile(final String name, final UUID uuid, final String texture) {

    this.name = name;
    this.uuid = uuid;
    this.texture = texture;
  }

  public String getName() {

    return name;
  }

  public void setName(final String name) {

    this.name = name;
  }

  public UUID getUuid() {

    return uuid;
  }

  public void setUuid(final UUID uuid) {

    this.uuid = uuid;
  }

  public String getTexture() {

    return texture;
  }

  public void setTexture(final String texture) {

    this.texture = texture;
  }

  public boolean equals(final SkullProfile profile) {

    return Objects.equals(profile.getName(), name)
           && profile.getUuid() == uuid
           && Objects.equals(profile.getTexture(), texture);
  }
}