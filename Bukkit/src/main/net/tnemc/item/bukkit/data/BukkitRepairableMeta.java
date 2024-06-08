package net.tnemc.item.bukkit.data;

import net.tnemc.item.SerialItemData;
import net.tnemc.item.bukkit.ParsingUtil;
import net.tnemc.item.data.RepairableData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;

public class BukkitRepairableMeta extends RepairableData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final Repairable meta = (Repairable)stack.getItemMeta();

    if(meta != null && meta.hasRepairCost()) {
      cost = meta.getRepairCost();
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {

    final Repairable meta = (Repairable)ParsingUtil.buildFor(stack, Repairable.class);

    if(hasCost()) {
      meta.setRepairCost(cost);
    }
    stack.setItemMeta(meta);

    return stack;
  }
}