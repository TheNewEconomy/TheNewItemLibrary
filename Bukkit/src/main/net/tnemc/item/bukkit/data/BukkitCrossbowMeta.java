package net.tnemc.item.bukkit.data;

import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.bukkit.ParsingUtil;
import net.tnemc.item.data.CrossBowMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

public class BukkitCrossbowMeta extends CrossBowMeta<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final CrossbowMeta meta = (CrossbowMeta)stack.getItemMeta();

    if(meta != null) {
      int i = 0;
      for(final ItemStack item : meta.getChargedProjectiles()) {
        items.put(i, new SerialItem<>(BukkitItemStack.locale(item)));
        i++;
      }
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {

    final CrossbowMeta meta = (CrossbowMeta)ParsingUtil.buildFor(stack, CrossbowMeta.class);


    items.forEach((slot, item)->meta.addChargedProjectile(item.getStack().locale()));

    return stack;
  }
}