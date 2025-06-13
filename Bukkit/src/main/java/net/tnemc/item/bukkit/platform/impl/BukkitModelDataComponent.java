package net.tnemc.item.bukkit.platform.impl;
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

import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.component.impl.ModelDataComponent;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * BukkitModelData
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitModelDataComponent extends ModelDataComponent<BukkitItemStack, ItemStack> {

  public BukkitModelDataComponent() {

  }

  public BukkitModelDataComponent(final List<String> colours, final List<Float> floats, final List<Boolean> flags, final List<String> strings) {

    super(colours, floats, flags, strings);
  }

  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.hasItemMeta() && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData();
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   * @since 0.2.0.0
   */
  @Override
  public ItemStack apply(final BukkitItemStack serialized, final ItemStack item) {

    final Optional<BukkitModelDataComponent> componentOptional = serialized.component(identifier());

    if(componentOptional.isPresent()) {

      final ItemMeta meta = item.getItemMeta();
      if(meta != null) {

        final CustomModelDataComponent component = meta.getCustomModelDataComponent();
        final List<Color> colourList = new ArrayList<>();
        for(final String colourStr : componentOptional.get().colours) {

          try {

            colourList.add(Color.fromARGB(Integer.parseInt(colourStr)));
          } catch(final Exception ignore) {}
        }

        component.setColors(colourList);
        component.setFlags(componentOptional.get().flags);
        component.setFloats(componentOptional.get().floats);
        component.setStrings(componentOptional.get().strings);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);
      }
    }
    return item;
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneFive(version);
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack serialize(final ItemStack item, final BukkitItemStack serialized) {

    if(item.hasItemMeta()) {

      final BukkitModelDataComponent component = (serialized.bukkitComponent(identifier()) instanceof final ModelDataComponent<?, ?> getComponent)?
                                                    (BukkitModelDataComponent)getComponent : new BukkitModelDataComponent();

      final CustomModelDataComponent dataComponent = item.getItemMeta().getCustomModelDataComponent();
      for(final Color color : dataComponent.getColors()) {

        component.colours.add(String.valueOf(color.asARGB()));
      }

      component.flags.addAll(dataComponent.getFlags());
      component.floats.addAll(dataComponent.getFloats());
      component.strings.addAll(dataComponent.getStrings());

      serialized.applyComponent(component);
    }
    return serialized;
  }
}