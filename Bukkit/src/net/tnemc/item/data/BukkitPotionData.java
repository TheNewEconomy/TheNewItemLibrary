package net.tnemc.item.data;

import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.potion.PotionEffectData;
import org.bukkit.inventory.ItemStack;

public class BukkitPotionData extends SerialPotionData<ItemStack> {

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
    return null;
  }
}