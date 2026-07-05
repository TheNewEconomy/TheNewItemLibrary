package net.tnemc.item.paper.platform.impl.modern;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.FoodProperties;
import net.tnemc.item.component.impl.FoodComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperFoodComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperFoodComponent extends FoodComponent<PaperItemStack, ItemStack> {

  public PaperFoodComponent() {

  }

  public PaperFoodComponent(final boolean canAlwaysEat) {

    super(canAlwaysEat);
  }

  public PaperFoodComponent(final boolean canAlwaysEat, final float saturation) {

    super(canAlwaysEat, saturation);
  }

  public PaperFoodComponent(final boolean canAlwaysEat, final float saturation, final int nutrition) {

    super(canAlwaysEat, saturation, nutrition);
  }

  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneFour(version);
  }

  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }

  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperFoodComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperFoodComponent component = componentOptional.get();

    item.setData(DataComponentTypes.FOOD,
                 FoodProperties.food()
                         .nutrition(component.nutrition())
                         .saturation(component.saturation())
                         .canAlwaysEat(component.canAlwaysEat()));

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final FoodProperties food = item.getData(DataComponentTypes.FOOD);
    if(food == null) {
      return serialized;
    }

    final PaperFoodComponent component = (serialized.paperComponent(identifier()) instanceof final FoodComponent<?, ?> getComponent)?
                                         (PaperFoodComponent)getComponent : new PaperFoodComponent();

    component.nutrition(food.nutrition());
    component.saturation(food.saturation());
    component.canAlwaysEat(food.canAlwaysEat());

    serialized.applyComponent(component);
    return serialized;
  }
}