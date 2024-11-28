package net.tnemc.item.bukkitbase.component;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.component.impl.FoodComponent;
import net.tnemc.item.component.helper.FoodRule;
import net.tnemc.item.data.potion.PotionEffectData;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * BukkitFoodComponent
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitBaseFoodComponent extends FoodComponent<ItemStack> {

  public static BukkitBaseFoodComponent create(final ItemStack stack) {

    final BukkitBaseFoodComponent component = new BukkitBaseFoodComponent();
    component.of(stack);
    return component;
  }

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialComponent} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(final ItemStack stack) {

    if(stack.hasItemMeta()) {

      final ItemMeta meta = stack.getItemMeta();
      if(meta.hasFood()) {

        final org.bukkit.inventory.meta.components.FoodComponent foodComponent = meta.getFood();
        this.noHunger = foodComponent.canAlwaysEat();
        this.eatTime = foodComponent.getEatSeconds();
        this.saturation = foodComponent.getSaturation();
        this.nutrition = foodComponent.getNutrition();

        for(final org.bukkit.inventory.meta.components.FoodComponent.FoodEffect foodEffect : foodComponent.getEffects()) {

          final PotionEffect effect = foodEffect.getEffect();

          this.rules.add(new FoodRule(new PotionEffectData(effect.getType().getName(),
                                                           effect.getAmplifier(),
                                                           effect.getDuration(),
                                                           effect.hasParticles(),
                                                           effect.isAmbient(),
                                                           effect.hasIcon()), foodEffect.getProbability()));

        }
      }
    }
  }

  /**
   * This method is used to apply the component to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(final ItemStack stack) {

    ItemMeta meta = stack.getItemMeta();
    if(meta == null) {
      meta = Bukkit.getItemFactory().getItemMeta(stack.getType());
    }

    final org.bukkit.inventory.meta.components.FoodComponent foodComponent = meta.getFood();
    foodComponent.setCanAlwaysEat(noHunger);
    foodComponent.setEatSeconds(eatTime);
    foodComponent.setSaturation(saturation);
    foodComponent.setNutrition(nutrition);

    for(final FoodRule rule : rules) {

      final PotionEffectData effect = rule.getPotionEffect();

      foodComponent.addEffect(new PotionEffect(PotionEffectType.getByName(effect.getName()),
                                               effect.getDuration(),
                                               effect.getAmplifier(),
                                               effect.isAmbient(),
                                               effect.hasParticles(),
                                               effect.hasIcon()), rule.getChance());
    }
    meta.setFood(foodComponent);
    stack.setItemMeta(meta);
    return stack;
  }
}