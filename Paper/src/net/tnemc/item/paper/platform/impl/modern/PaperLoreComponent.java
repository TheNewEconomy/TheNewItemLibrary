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
import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.tnemc.item.component.impl.LoreComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.impl.PaperSerialComponent;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * PaperOldLoreComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperLoreComponent extends LoreComponent<PaperItemStack, ItemStack> implements PaperSerialComponent<PaperItemStack, ItemStack> {

  public PaperLoreComponent() {

  }

  public PaperLoreComponent(final List<Component> lore) {

    super(lore);
  }

  public PaperLoreComponent(final Component... lore) {

    super(lore);
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
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.hasItemMeta() && item.getItemMeta() != null;
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

    final Optional<PaperLoreComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final ItemLore.Builder builder = ItemLore.lore().lines(componentOptional.get().lore);

    item.setData(DataComponentTypes.LORE, builder);

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

    final Optional<PaperLoreComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      final ItemMeta meta = item.getItemMeta();
      if(meta != null) {

        final LinkedList<String> newLore = new LinkedList<>();
        for(final Component comp : componentOptional.get().lore) {

          newLore.add(LegacyComponentSerializer.legacySection().serialize(comp));
        }
        meta.setLore(newLore);

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
   * @since 0.2.0.0
   */
  @Override
  public PaperItemStack serializeModern(final ItemStack item, final PaperItemStack serialized) {

    final ItemLore itemLore = item.getData(DataComponentTypes.LORE);
    if(itemLore == null) {
      return serialized;
    }

    this.lore.addAll(itemLore.lines());

    serialized.applyComponent(this);
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
    if(meta != null && meta.getLore() != null) {

      lore.clear();

      for(final String str : meta.getLore()) {

        lore.add(LegacyComponentSerializer.legacySection().deserialize(str));
      }
    }

    serialized.applyComponent(this);
    return serialized;
  }
}