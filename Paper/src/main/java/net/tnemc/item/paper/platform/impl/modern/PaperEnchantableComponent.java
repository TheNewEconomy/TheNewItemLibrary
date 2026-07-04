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
import io.papermc.paper.datacomponent.item.Enchantable;
import net.tnemc.item.component.impl.EnchantableComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperEnchantableComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperEnchantableComponent extends EnchantableComponent<PaperItemStack, ItemStack> {

  public PaperEnchantableComponent() {

  }

  public PaperEnchantableComponent(final int value) {

    super(value);
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

    final Optional<PaperEnchantableComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    item.setData(DataComponentTypes.ENCHANTABLE, Enchantable.enchantable(componentOptional.get().value));
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final Enchantable enchantable = item.getData(DataComponentTypes.ENCHANTABLE);
    if(enchantable == null) {
      return serialized;
    }

    final PaperEnchantableComponent component = (serialized.paperComponent(identifier()) instanceof final EnchantableComponent<?, ?> getComponent)?
                                                (PaperEnchantableComponent)getComponent : new PaperEnchantableComponent();

    component.value(enchantable.value());

    serialized.applyComponent(component);
    return serialized;
  }
}