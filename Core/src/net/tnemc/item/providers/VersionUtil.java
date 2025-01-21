package net.tnemc.item.providers;

/*
 * The New Item Library Minecraft Server Plugin
 *
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

import com.vdurmont.semver4j.Semver;

public class VersionUtil {

  /**
   * @return Whether the bukkit in use is for MC >= 1.8
   */
  public static boolean isVersion(final String version, final String compare) {

    return new Semver(version, Semver.SemverType.LOOSE).isGreaterThanOrEqualTo(compare);
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.7
   */
  public static boolean isOneSeven(final String version) {

    return isVersion(version, "1.7.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.8
   */
  public static boolean isOneEight(final String version) {

    return isVersion(version, "1.8.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.9
   */
  public static boolean isOneNine(final String version) {

    return isVersion(version, "1.9.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.10
   */
  public static boolean isOneTen(final String version) {

    return isVersion(version, "1.10.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.11
   */
  public static boolean isOneEleven(final String version) {

    return isVersion(version, "1.11.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.12
   */
  public static boolean isOneTwelve(final String version) {

    return isVersion(version, "1.12.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.13
   */
  public static boolean isOneThirteen(final String version) {

    return isVersion(version, "1.13.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.14
   */
  public static boolean isOneFourteen(final String version) {

    return isVersion(version, "1.14.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.15
   */
  public static boolean isOneFifteen(final String version) {

    return isVersion(version, "1.15.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.16
   */
  public static boolean isOneSixteen(final String version) {

    return isVersion(version, "1.16.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.17
   */
  public static boolean isOneSeventeen(final String version) {

    return isVersion(version, "1.17.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.20
   */
  public static boolean isOneTwenty(final String version) {

    return isVersion(version, "1.20.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.21
   */
  public static boolean isOneTwentyOne(final String version) {

    return isVersion(version, "1.21.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.21.3
   */
  public static boolean isOneTwentyOneThree(final String version) {

    return isVersion(version, "1.21.3");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.21.4
   */
  public static boolean isOneTwentyOneFour(final String version) {

    return isVersion(version, "1.21.4");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.22
   */
  public static boolean isOneTwentyTwo(final String version) {

    return isVersion(version, "1.22.0");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.23
   */
  public static boolean isOneTwentyThree(final String version) {

    return isVersion(version, "1.23.0");
  }
}