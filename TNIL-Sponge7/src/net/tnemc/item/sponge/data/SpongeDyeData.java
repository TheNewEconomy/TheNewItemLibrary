package net.tnemc.item.sponge.data;

import net.tnemc.item.SerialItemData;
import net.tnemc.sponge.data.abs.DyeData;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryTypes;

import java.util.Optional;

public class SpongeDyeData extends DyeData<ItemStack> {

  protected boolean applies = false;

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {

    final Optional<DyeColor> dye = stack.get(Keys.DYE_COLOR);
    dye.ifPresent(color->{
      this.dye = color.key(RegistryTypes.DYE_COLOR).formatted();
      applies = true;
    });
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {

    if(!dye.equalsIgnoreCase("")) {
      stack.offer(Keys.DYE_COLOR, DyeColors.registry().value(ResourceKey.resolve(dye)));
    }

    return stack;
  }

  @Override
  public boolean applies() {

    return applies;
  }
}