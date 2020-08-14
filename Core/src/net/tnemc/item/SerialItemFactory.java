package net.tnemc.item;

import net.tnemc.item.providers.CalculationsProvider;
import net.tnemc.item.providers.UtilsProvider;

public abstract class SerialItemFactory {

  private UtilsProvider utils;

  private CalculationsProvider calculations;

  public SerialItemFactory(UtilsProvider utils, CalculationsProvider calculations) {
    this.utils = utils;
    this.calculations = calculations;
  }

  public UtilsProvider getUtils() {
    return utils;
  }

  public void setUtils(UtilsProvider utils) {
    this.utils = utils;
  }

  public CalculationsProvider getCalculations() {
    return calculations;
  }

  public void setCalculations(CalculationsProvider calculations) {
    this.calculations = calculations;
  }
}