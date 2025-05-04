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
import net.tnemc.item.component.impl.DamageComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.paper.platform.impl.PaperSerialComponent;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Optional;

/**
 * PaperOldDamageComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperDamageComponent extends DamageComponent<PaperItemStack, ItemStack> implements PaperSerialComponent<PaperItemStack, ItemStack> {

  /**
   * Represents a component that manages damage information. This component stores and provides
   * methods for handling damage values.
   * @since 0.2.0.0
   */
  public PaperDamageComponent() {

  }

  /**
   * Constructs a new DamageComponent with the specified damage amount.
   *
   * @param damage the amount of damage for the component
   * @since 0.2.0.0
   */
  public PaperDamageComponent(final int damage) {

    super(damage);
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

    final Optional<PaperDamageComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    item.setData(DataComponentTypes.DAMAGE, this.damage);
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

    final Optional<PaperDamageComponent> componentOptional = serialized.component(identifier());

    if(componentOptional.isPresent()) {
      if(VersionUtil.isOneThirteen(PaperItemPlatform.instance().version())) {

        if(item.hasItemMeta() && item.getItemMeta() instanceof final Damageable meta) {

          meta.setDamage(componentOptional.get().damage());
          item.setItemMeta(meta);
        }
      } else {

        item.setDurability((short)componentOptional.get().damage);
      }
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

    final Integer damageValue = item.getData(DataComponentTypes.DAMAGE);
    if(damageValue == null) {
      return serialized;
    }

    this.damage = damageValue;

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

    if(VersionUtil.isOneThirteen(PaperItemPlatform.instance().version())) {

      if(item.hasItemMeta() && item.getItemMeta() instanceof final Damageable meta) {
        this.damage = meta.getDamage();
      }
    } else {
      this.damage = item.getDurability();
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
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }
}