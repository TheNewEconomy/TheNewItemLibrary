package net.tnemc.item.component.helper;
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

/**
 * Sound
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class Sound {

  private String soundId;
  private float range;

  public Sound(final String sound, final float range) {
    this.soundId = sound;
    this.range = range;
  }

  public String sound() {

    return soundId;
  }

  public void sound(final String sound) {

    this.soundId = sound;
  }

  public float range() {

    return range;
  }

  public void range(final float range) {

    this.range = range;
  }
}