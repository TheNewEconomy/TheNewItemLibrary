package net.tnemc.item.sponge.data;

import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.CrossBowMeta;
import org.spongepowered.api.item.inventory.ItemStack;

public class SpongeCrossbowMeta extends CrossBowMeta<ItemStack> {

  protected boolean applies = false;

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {

  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {

    return stack;
  }

  @Override
  public boolean applies() {

    return applies;
  }
}