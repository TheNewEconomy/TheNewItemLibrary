package net.tnemc.item.sponge.version;

import net.tnemc.item.SerialItemData;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public interface Version {

  /**
   * Used to attempt to get the {@link SerialItemData item data} from an item stack.
   *
   * @param stack    The stack to use for this operation.
   * @param itemType The item type, including the namespace to use.
   *
   * @return An optional containing the item data if it was possible to get, otherwise an empty
   * optional.
   */
  Optional<SerialItemData<ItemStack>> findData(final ItemStack stack, final String itemType);
}