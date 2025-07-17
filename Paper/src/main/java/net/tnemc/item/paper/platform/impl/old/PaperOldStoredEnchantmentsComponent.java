package net.tnemc.item.paper.platform.impl.old;
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

import net.tnemc.item.component.impl.StoredEnchantmentsComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;
import java.util.Optional;

/**
 * PaperOldStoredEnchantmentsComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperOldStoredEnchantmentsComponent extends StoredEnchantmentsComponent<PaperItemStack, ItemStack> {

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    return true;
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   *
   * @since 0.2.0.0
   */
  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperOldStoredEnchantmentsComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      if(item.hasItemMeta() && item.getItemMeta() instanceof final EnchantmentStorageMeta meta) {

        for(final Map.Entry<String, Integer> entry : componentOptional.get().levels.entrySet()) {

          try {

            final Enchantment enchant = PaperItemPlatform.instance().converter().convert(entry.getKey(), Enchantment.class);
            if(enchant != null) {

              meta.addStoredEnchant(enchant, entry.getValue(), true);
            }
          } catch(final Exception ignore) {
            //enchantment couldn't be found.
          }
        }
      }
    });
    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   *
   * @since 0.2.0.0
   */
  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    if(item.hasItemMeta() && item.getItemMeta() instanceof final EnchantmentStorageMeta meta) {

      final PaperOldStoredEnchantmentsComponent component = (serialized.paperComponent(identifier()) instanceof final StoredEnchantmentsComponent<?, ?> getComponent)?
                                                            (PaperOldStoredEnchantmentsComponent)getComponent : new PaperOldStoredEnchantmentsComponent();

      for(final Map.Entry<Enchantment, Integer> entry : meta.getStoredEnchants().entrySet()) {

        component.levels.put(PaperItemPlatform.instance().converter().convert(entry.getKey(), String.class), entry.getValue());
      }

      serialized.applyComponent(component);
    }
    return serialized;
  }

  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.hasItemMeta() && item.getItemMeta() instanceof EnchantmentStorageMeta;
  }
}