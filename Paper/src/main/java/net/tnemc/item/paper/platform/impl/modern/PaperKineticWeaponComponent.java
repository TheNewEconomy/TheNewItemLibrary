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
import io.papermc.paper.datacomponent.item.KineticWeapon;
import net.kyori.adventure.key.Key;
import net.tnemc.item.component.helper.KineticWeaponCondition;
import net.tnemc.item.component.helper.Sound;
import net.tnemc.item.component.impl.KineticWeaponComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.Optional;

/**
 * PaperKineticWeaponComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperKineticWeaponComponent extends KineticWeaponComponent<PaperItemStack, ItemStack> {

  public PaperKineticWeaponComponent() {

  }

  public PaperKineticWeaponComponent(final int delayTicks,
                                     final KineticWeaponCondition damageConditions,
                                     final KineticWeaponCondition dismountConditions,
                                     final KineticWeaponCondition knockbackConditions,
                                     final float forwardMovement,
                                     final float damageMultiplier,
                                     final Sound sound,
                                     final Sound hitSound) {

    super(delayTicks, damageConditions, dismountConditions, knockbackConditions, forwardMovement, damageMultiplier, sound, hitSound);
  }

  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneEleven(version);
  }

  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }

  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperKineticWeaponComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperKineticWeaponComponent component = componentOptional.get();

    final KineticWeapon.Builder builder = KineticWeapon.kineticWeapon()
            .delayTicks(component.delayTicks())
            .damageConditions(condition(component.damageConditions()))
            .dismountConditions(condition(component.dismountConditions()))
            .knockbackConditions(condition(component.knockbackConditions()))
            .forwardMovement(component.forwardMovement())
            .damageMultiplier(component.damageMultiplier());

    if(component.sound() != null && component.sound().sound() != null) {
      builder.sound(Key.key(component.sound().sound()));
    }

    if(component.hitSound() != null && component.hitSound().sound() != null) {
      builder.hitSound(Key.key(component.hitSound().sound()));
    }

    item.setData(DataComponentTypes.KINETIC_WEAPON, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final KineticWeapon weapon = item.getData(DataComponentTypes.KINETIC_WEAPON);
    if(weapon == null) {
      return serialized;
    }

    final PaperKineticWeaponComponent component = (serialized.paperComponent(identifier()) instanceof final KineticWeaponComponent<?, ?> getComponent)?
                                                  (PaperKineticWeaponComponent)getComponent : new PaperKineticWeaponComponent();

    component.delayTicks(weapon.delayTicks());
    component.damageConditions(condition(weapon.damageConditions()));
    component.dismountConditions(condition(weapon.dismountConditions()));
    component.knockbackConditions(condition(weapon.knockbackConditions()));
    component.forwardMovement(weapon.forwardMovement());
    component.damageMultiplier(weapon.damageMultiplier());

    if(weapon.sound() != null) {
      component.sound(new Sound(weapon.sound().asString(), 0.0f));
    }

    if(weapon.hitSound() != null) {
      component.hitSound(new Sound(weapon.hitSound().asString(), 0.0f));
    }

    serialized.applyComponent(component);
    return serialized;
  }

  private KineticWeapon.Condition condition(final KineticWeaponCondition condition) {

    if(condition == null) {
      return null;
    }

    final JSONObject json = condition.toJSON();

    return KineticWeapon.condition(Integer.parseInt(json.get("maxDurationTicks").toString()),
                                   Float.parseFloat(json.get("minSpeed").toString()),
                                   Float.parseFloat(json.get("minRelativeSpeed").toString()));
  }

  private KineticWeaponCondition condition(final KineticWeapon.Condition condition) {

    if(condition == null) {
      return null;
    }

    return new KineticWeaponCondition(condition.maxDurationTicks(), condition.minSpeed(), condition.minRelativeSpeed());
  }
}