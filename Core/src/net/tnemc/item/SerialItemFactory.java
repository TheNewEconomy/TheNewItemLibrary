package net.tnemc.item;

import net.tnemc.item.providers.CalculationsProvider;
import net.tnemc.item.providers.VersionUtilities;

public abstract class SerialItemFactory {

  private VersionUtilities utils;

  private CalculationsProvider calculations;

  public SerialItemFactory(VersionUtilities utils, CalculationsProvider calculations) {
    this.utils = utils;
    this.calculations = calculations;
  }

  public VersionUtilities getUtils() {
    return utils;
  }

  public void setUtils(VersionUtilities utils) {
    this.utils = utils;
  }

  public CalculationsProvider getCalculations() {
    return calculations;
  }

  public void setCalculations(CalculationsProvider calculations) {
    this.calculations = calculations;
  }
}