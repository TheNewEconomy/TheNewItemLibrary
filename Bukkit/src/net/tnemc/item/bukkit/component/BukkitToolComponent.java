package net.tnemc.item.bukkit.component;
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
import net.tnemc.item.component.impl.ToolComponent;
import net.tnemc.item.component.helper.ToolRule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;

/**
 * BukkitToolComponent
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitToolComponent extends ToolComponent<ItemStack> {

  public static BukkitToolComponent create(final ItemStack stack) {

    final BukkitToolComponent component = new BukkitToolComponent();
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
      if(meta.hasTool()) {

        final org.bukkit.inventory.meta.components.ToolComponent tool = meta.getTool();
        this.blockDamage = tool.getDamagePerBlock();
        this.defaultSpeed = tool.getDefaultMiningSpeed();

        for(final org.bukkit.inventory.meta.components.ToolComponent.ToolRule rule : tool.getRules()) {

          final ToolRule newRule = new ToolRule(rule.getSpeed(), rule.isCorrectForDrops());


          for(final Material material : rule.getBlocks()) {
            newRule.getMaterials().add(material.getKey().getKey());
          }

          rules.add(newRule);

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

    final org.bukkit.inventory.meta.components.ToolComponent toolComponent = meta.getTool();
    toolComponent.setDamagePerBlock(blockDamage);
    toolComponent.setDefaultMiningSpeed(defaultSpeed);

    for(final ToolRule rule : rules) {

      final Collection<Material> mats = new ArrayList<>();

      for(final String material : rule.getMaterials()) {
        mats.add(Material.matchMaterial(material));
      }

      toolComponent.addRule(mats, rule.getSpeed(), rule.isDrops());
    }

    meta.setTool(toolComponent);
    stack.setItemMeta(meta);
    return stack;
  }
}