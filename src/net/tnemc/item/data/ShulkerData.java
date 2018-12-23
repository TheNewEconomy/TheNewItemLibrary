package net.tnemc.item.data;


import net.tnemc.item.ItemCalculations;
import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TheNewItemLibrary
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by Daniel on 11/11/2017.
 */
public class ShulkerData implements SerialItemData {

  private Map<Integer, SerialItem> items = new HashMap<>();

  private boolean valid = false;

  @Override
  public SerialItemData initialize(ItemStack stack) {
    if(ItemCalculations.isShulker(stack.getType())) {

      ItemMeta meta = stack.getItemMeta();
      if(meta instanceof BlockStateMeta) {

        BlockStateMeta state = (BlockStateMeta)meta;
        if(state.getBlockState() instanceof ShulkerBox) {
          valid = true;

          ShulkerBox shulker = (ShulkerBox)state.getBlockState();
          Inventory inventory = shulker.getInventory();
          for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
              if(items.containsKey(i)) {
                items.remove(i);
              }
            } else {
              items.put(i, new SerialItem(inventory.getItem(i)));
            }
          }
        }
      }
    }
    return this;
  }

  @Override
  public ItemStack build(ItemStack stack) {
    if(valid) {
      BlockStateMeta meta = (BlockStateMeta)stack.getItemMeta();
      ShulkerBox shulkerBox = (ShulkerBox)meta.getBlockState();
      items.forEach((slot, item)->shulkerBox.getInventory().setItem(slot, item.getStack()));
      meta.setBlockState(shulkerBox);
      stack.setItemMeta(meta);
    }
    return stack;
  }

  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "shulker");
    JSONObject itemsObj = new JSONObject();
    items.forEach((slot, item)->{
      itemsObj.put(slot, item.toJSON());
    });
    json.put("items", itemsObj);
    return json;
  }

  @Override
  public void readJSON(JSONHelper json) {
    valid = true;
    json.getJSON("items").forEach((key, value)->{
      final int slot = Integer.valueOf(String.valueOf(key));
      items.put(slot, SerialItem.fromJSON((JSONObject)value));
    });
  }

  public Map<Integer, SerialItem> getItems() {
    return items;
  }

  public void setItems(Map<Integer, SerialItem> items) {
    this.items = items;
  }
}