package net.tnemc.item.data;

import net.tnemc.item.SerialItemData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;

public class BukkitFireworkEffectData extends FireworkData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final FireworkEffectMeta meta = (FireworkEffectMeta)stack.getItemMeta();

    if(meta != null) {
      this.title = meta.getTitle();
      this.author = meta.getAuthor();
      this.generation = meta.getGeneration().name();
      this.pages = meta.getPages();
    }
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