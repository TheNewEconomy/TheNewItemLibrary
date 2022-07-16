package net.tnemc.item.providers;

import com.vdurmont.semver4j.Semver;

public class VersionChecker {

  /**
   * @return Whether the bukkit in use is for MC >= 1.8
   */
  public static boolean isVersion(final String version, final String compare) {
    return new Semver(version).isGreaterThanOrEqualTo(compare);
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
}