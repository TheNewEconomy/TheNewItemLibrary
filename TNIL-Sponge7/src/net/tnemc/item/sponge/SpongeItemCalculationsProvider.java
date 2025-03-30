package net.tnemc.item.sponge;

import net.tnemc.item.InventoryType;
import net.tnemc.item.providers.CalculationsProvider;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.world.Location;

import java.util.*;

public class SpongeItemCalculationsProvider implements CalculationsProvider<SpongeItemStack, ItemStack, Inventory> {

  @Override
  public boolean drop(Collection<SpongeItemStack> left, UUID identifier) {

    final Optional<Player> player = Sponge.getGame().getServer().getPlayer(identifier);

    if(player.isPresent()) {
      for(SpongeItemStack stack : left) {
        final Location<?> location = player.get().getLocation();
        final Item item = (Item)location.createEntity(EntityTypes.ITEM);

        item.offer(Keys.ACTIVE_ITEM, stack.locale().createSnapshot());
        location.spawnEntity(item);
      }
    }
    return false;
  }

  @Override
  public int removeAll(SpongeItemStack stack, Inventory inventory) {

    final ItemStack compare = stack.locale().copy();
    compare.setQuantity(1);

    int amount = 0;
    for(Inventory slot : inventory.slots()) {
      final Optional<ItemStack> slotItem = slot.peek();

      if(slotItem.isPresent()) {

        final ItemStack compareI = slotItem.get().copy();
        compareI.setQuantity(1);
        if(compareI.equalTo(stack.locale())) {
          amount += slotItem.get().getQuantity();
          slot.clear();
        }
      }
    }
    return amount;
  }

  @Override
  public int count(SpongeItemStack stack, Inventory inventory) {

    final ItemStack compare = stack.locale().copy();
    compare.setQuantity(1);
    int count = 0;

    for(Inventory slot : inventory.slots()) {
      final Optional<ItemStack> slotItem = slot.peek();

      if(slotItem.isPresent()) {

        final ItemStack compareI = slotItem.get().copy();
        compareI.setQuantity(1);
        if(compareI.equalTo(stack.locale())) {
          count += slot.totalItems();
        }
      }
    }
    return count;
  }

  @Override
  public void takeItems(Collection<SpongeItemStack> items, Inventory inventory) {

    items.forEach(itemStack->removeItem(itemStack, inventory));
  }

  @Override
  public Collection<SpongeItemStack> giveItems(Collection<SpongeItemStack> items, Inventory inventory) {

    final Collection<SpongeItemStack> leftOver = new ArrayList<>();

    for(SpongeItemStack stack : items) {
      final InventoryTransactionResult result = inventory.offer(stack.locale());
      final Collection<ItemStackSnapshot> rejected = result.getRejectedItems();
      if(rejected.size() > 0) {
        rejected.forEach(snapshot->leftOver.add(new SpongeItemStack().of(snapshot.createStack())));
      }
    }
    return leftOver;
  }

  @Override
  public int removeItem(SpongeItemStack stack, Inventory inventory) {

    int left = stack.locale().copy().getQuantity();

    final ItemStack compare = stack.locale().copy();
    compare.setQuantity(1);
    for(Inventory slot : inventory.slots()) {
      if(left <= 0) break;

      final Optional<ItemStack> slotItem = slot.peek();
      if(slotItem.isPresent()) {


        final ItemStack compare1 = slotItem.get().copy();
        compare1.setQuantity(1);
        if(compare.equalTo(compare1)) {
          final int quantity = slotItem.get().getQuantity();

          if(quantity > left) {
            slotItem.get().setQuantity(quantity - left);
            slot.set(slotItem.get());
            left = 0;
          } else {
            left -= quantity;
            slot.clear();
          }
        }
      }
    }
    return left;
  }

  @Override
  public Optional<Inventory> getInventory(UUID identifier, InventoryType type) {

    final Optional<Player> player = Sponge.getServer().getPlayer(identifier);
    if(player.isPresent() && player.get().isOnline()) {

      if(type.equals(InventoryType.ENDER_CHEST)) {
        return Optional.of(player.get().getEnderChestInventory());
      } else {
        return Optional.of(player.get().getInventory());
      }
    }
    return Optional.empty();
  }
}
