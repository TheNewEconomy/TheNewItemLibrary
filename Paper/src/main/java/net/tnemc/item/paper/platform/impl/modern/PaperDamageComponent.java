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
   *
   * @since 0.2.0.0
   */
  public PaperDamageComponent() {

  }

  /**
   * Constructs a new DamageComponent with the specified damage amount.
   *
   * @param damage the amount of damage for the component
   *
   * @since 0.2.0.0
   */
  public PaperDamageComponent(final int damage) {

    super(damage);
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   *
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
   *
   * @since 0.2.0.0
   */
  @Override
  public ItemStack applyModern(final PaperItemStack serialized, final ItemStack item) {

    //System.out.println("=== Apply Modern Damage ===");
    final Optional<PaperDamageComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      //System.out.println("Empty Damage");

      return item;
    }
    //System.out.println("Damage: " + componentOptional.get().damage);

    item.setData(DataComponentTypes.DAMAGE, componentOptional.get().damage);
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
    //System.out.println("Apply Legacy Damage");

    final Optional<PaperDamageComponent> componentOptional = serialized.component(identifier());

    if(componentOptional.isPresent()) {
      if(VersionUtil.isOneThirteen(PaperItemPlatform.instance().version())) {

        if(item.hasItemMeta() && item.getItemMeta() instanceof final Damageable meta) {

          meta.setDamage(componentOptional.get().damage());
          item.setItemMeta(meta);
        }
      } else {
        //System.out.println("Empty Damage");

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
   *
   * @since 0.2.0.0
   */
  @Override
  public PaperItemStack serializeModern(final ItemStack item, final PaperItemStack serialized) {
    //System.out.println("==== Damage ====");

    final Integer damageValue = item.getData(DataComponentTypes.DAMAGE);
    if(damageValue == null) {

      //System.out.println("No damage value");
      return serialized;
    }

    final PaperDamageComponent component = (serialized.paperComponent(identifier()) instanceof final DamageComponent<?, ?> getComponent)?
                                           (PaperDamageComponent)getComponent : new PaperDamageComponent();

    //System.out.println("Damage: " + damageValue);
    component.damage(damageValue);

    serialized.applyComponent(component);
    //System.out.println("==== End Damage ====");
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

    int damage = 0;
    if(VersionUtil.isOneThirteen(PaperItemPlatform.instance().version())) {

      if(item.hasItemMeta() && item.getItemMeta() instanceof final Damageable meta) {
        damage = meta.getDamage();
      }
    } else {
      damage = item.getDurability();
    }

    final PaperDamageComponent component = (serialized.paperComponent(identifier()) instanceof final DamageComponent<?, ?> getComponent)?
                                           (PaperDamageComponent)getComponent : new PaperDamageComponent();
    component.damage(damage);


    serialized.applyComponent(component);
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

    return true;
  }
}