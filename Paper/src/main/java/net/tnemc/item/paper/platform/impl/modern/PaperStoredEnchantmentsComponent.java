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
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import net.tnemc.item.component.impl.StoredEnchantmentsComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;

/**
 * PaperStoredEnchantments
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperStoredEnchantmentsComponent extends StoredEnchantmentsComponent<PaperItemStack, ItemStack> {

  public PaperStoredEnchantmentsComponent() {

  }

  public PaperStoredEnchantmentsComponent(final Map<String, Integer> levels) {

    super(levels);
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

    final Optional<PaperStoredEnchantmentsComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final ItemEnchantments.Builder builder = ItemEnchantments.itemEnchantments();

    for(final Map.Entry<String, Integer> entry : componentOptional.get().levels.entrySet()) {
      try {
        final Enchantment enchant = PaperItemPlatform.instance().converter().convert(entry.getKey(), Enchantment.class);
        if(enchant != null) {
          builder.add(enchant, entry.getValue());
        }
      } catch(final Exception ignore) {
        // enchantment couldn't be found.
      }
    }

    item.setData(DataComponentTypes.STORED_ENCHANTMENTS, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final ItemEnchantments enchants = item.getData(DataComponentTypes.STORED_ENCHANTMENTS);
    if(enchants == null) {
      return serialized;
    }

    final PaperStoredEnchantmentsComponent component = (serialized.paperComponent(identifier()) instanceof final StoredEnchantmentsComponent<?, ?> getComponent)?
                                                       (PaperStoredEnchantmentsComponent)getComponent : new PaperStoredEnchantmentsComponent();

    for(final Map.Entry<Enchantment, Integer> entry : enchants.enchantments().entrySet()) {
      component.levels.put(PaperItemPlatform.instance().converter().convert(entry.getKey(), String.class), entry.getValue());
    }

    serialized.applyComponent(component);
    return serialized;
  }
}