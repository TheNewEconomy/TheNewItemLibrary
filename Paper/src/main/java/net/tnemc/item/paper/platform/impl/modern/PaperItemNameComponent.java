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

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.tnemc.item.component.impl.ItemNameComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.impl.PaperSerialComponent;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

/**
 * BukkitItemNameComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperItemNameComponent extends ItemNameComponent<PaperItemStack, ItemStack> implements PaperSerialComponent<PaperItemStack, ItemStack> {

  public PaperItemNameComponent() {

  }

  public PaperItemNameComponent(final Component itemName) {

    super(itemName);
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneFour(version);
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   * @since 0.2.0.0
   */
  @Override
  public ItemStack applyModern(final PaperItemStack serialized, final ItemStack item) {

    //System.out.println("=== Apply ItemName Modern ===");
    final Optional<PaperItemNameComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {

      //System.out.println("No component found");
      return item;
    }

    //System.out.println("applying");
    item.setData(DataComponentTypes.ITEM_NAME, componentOptional.get().itemName());
    return item;
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
  public ItemStack applyLegacy(final PaperItemStack serialized, final ItemStack item) {

    final ItemMeta meta = item.getItemMeta();
    final Optional<PaperItemNameComponent> componentOptional = serialized.component(identifier());
    if(meta != null && componentOptional.isPresent()) {

      meta.setItemName(LegacyComponentSerializer.legacySection().serialize(componentOptional.get().itemName()));
      item.setItemMeta(meta);
    }
    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   * @since 0.2.0.0
   */
  @Override
  public PaperItemStack serializeModern(final ItemStack item, final PaperItemStack serialized) {

    //System.out.println("=== Serialize ItemName Modern ===");

    final Component name = item.getData(DataComponentTypes.ITEM_NAME);
    if(name == null) {
      //System.out.println("item name is null");
      return serialized;
    }

    final PaperItemNameComponent component = (serialized.paperComponent(identifier()) instanceof final ItemNameComponent<?, ?> getComponent)?
                                              (PaperItemNameComponent)getComponent : new PaperItemNameComponent();

    component.itemName(name);

    serialized.applyComponent(component);
    return serialized;
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
  public PaperItemStack serializeLegacy(final ItemStack item, final PaperItemStack serialized) {

    final ItemMeta meta = item.getItemMeta();
    if(meta != null && meta.hasItemName()) {

      final PaperItemNameComponent component = (serialized.paperComponent(identifier()) instanceof final ItemNameComponent<?, ?> getComponent)?
                                               (PaperItemNameComponent)getComponent : new PaperItemNameComponent();

      component.itemName(LegacyComponentSerializer.legacySection().deserialize(meta.getItemName()));

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
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }
}