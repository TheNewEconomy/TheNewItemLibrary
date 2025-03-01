package net.tnemc.item.paper.platform.impl.modern;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import net.tnemc.item.component.impl.FoodComponent;
import net.tnemc.item.paper.PaperItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

/**
 * PaperOldFoodComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperOldFoodComponent extends FoodComponent<PaperItemStack, ItemStack> {

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   */
  @Override
  public boolean enabled(final String version) {

    //TODO: What version?
    return true;
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   */
  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperOldFoodComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      final ItemMeta meta = item.getItemMeta();

      if(meta != null && meta.hasFood()) {

        final org.bukkit.inventory.meta.components.FoodComponent bukkitComponent = meta.getFood();

        bukkitComponent.setCanAlwaysEat(componentOptional.get().canAlwaysEat);
        bukkitComponent.setSaturation(componentOptional.get().saturation);
        bukkitComponent.setNutrition(componentOptional.get().nutrition);

        meta.setFood(bukkitComponent);

        item.setItemMeta(meta);
      }
    });
    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   */
  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final ItemMeta meta = item.getItemMeta();
    if(meta != null && meta.hasFood()) {

      this.canAlwaysEat = meta.getFood().canAlwaysEat();
      this.saturation = meta.getFood().getSaturation();
      this.nutrition = meta.getFood().getNutrition();
    }

    serialized.applyComponent(this);
    return serialized;
  }

  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.getItemMeta() != null && item.getItemMeta().hasFood();
  }
}