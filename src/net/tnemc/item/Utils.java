package net.tnemc.item;

import org.bukkit.Bukkit;

public class Utils {

  /**
   * @return Whether the bukkit in use is for MC >= 1.8
   */
  public static boolean isOneSix() {
    return Bukkit.getVersion().contains("1.6");
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.8
   */
  public static boolean isOneSeven() {
    return Bukkit.getVersion().contains("1.7") || isOneEight() || isOneNine() || isOneTen() || isOneEleven() || isOneTwelve() || isOneThirteen() || isOneFourteen();
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.8
   */
  public static boolean isOneEight() {
    return Bukkit.getVersion().contains("1.8") || isOneNine() || isOneTen() || isOneEleven() || isOneTwelve() || isOneThirteen() || isOneFourteen();
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.9
   */
  public static boolean isOneNine() {
    return Bukkit.getVersion().contains("1.9") || isOneTen() || isOneEleven() || isOneTwelve() || isOneThirteen() || isOneFourteen();
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.10
   */
  public static boolean isOneTen() {
    return Bukkit.getVersion().contains("1.10") || isOneEleven() || isOneTwelve() || isOneThirteen() || isOneFourteen();
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.11
   */
  public static boolean isOneEleven() {
    return Bukkit.getVersion().contains("1.11") || isOneTwelve() || isOneThirteen() || isOneFourteen();
  }

  /**
   * @return Whether the bukkit in use is for MC >= 1.12
   */
  public static boolean isOneTwelve() {
    return Bukkit.getVersion().contains("1.12") || isOneThirteen() || isOneFourteen();
  }

  public static boolean isOneThirteen() {
    return Bukkit.getVersion().contains("1.13") || isOneFourteen();
  }

  public static boolean isOneFourteen() {
    return Bukkit.getVersion().contains("1.14");
  }
}