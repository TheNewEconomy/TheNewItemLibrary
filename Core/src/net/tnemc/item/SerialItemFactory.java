package net.tnemc.item;

import net.tnemc.item.providers.CalculationsProvider;
import net.tnemc.item.providers.Utils;

public abstract class SerialItemFactory {

  private Utils utils;

  private CalculationsProvider calculations;

  public SerialItemFactory(Utils utils, CalculationsProvider calculations) {
    this.utils = utils;
    this.calculations = calculations;
  }

  public Utils getUtils() {
    return utils;
  }

  public void setUtils(Utils utils) {
    this.utils = utils;
  }

  public CalculationsProvider getCalculations() {
    return calculations;
  }

  public void setCalculations(CalculationsProvider calculations) {
    this.calculations = calculations;
  }
}