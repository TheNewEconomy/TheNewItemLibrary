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
import io.papermc.paper.datacomponent.item.PiercingWeapon;
import net.kyori.adventure.key.Key;
import net.tnemc.item.component.helper.Sound;
import net.tnemc.item.component.impl.PiercingWeaponComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperPiercingWeaponComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperPiercingWeaponComponent extends PiercingWeaponComponent<PaperItemStack, ItemStack> {

  public PaperPiercingWeaponComponent() {

  }

  public PaperPiercingWeaponComponent(final boolean dealsKnockback,
                                      final boolean dismounts,
                                      final Sound sound,
                                      final Sound hitSound) {

    super(dealsKnockback, dismounts, sound, hitSound);
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

    final Optional<PaperPiercingWeaponComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperPiercingWeaponComponent component = componentOptional.get();
    final PiercingWeapon.Builder builder = PiercingWeapon.piercingWeapon()
            .dealsKnockback(component.dealsKnockback())
            .dismounts(component.dismounts());

    if(component.sound() != null && component.sound().sound() != null) {
      builder.sound(Key.key(component.sound().sound()));
    }

    if(component.hitSound() != null && component.hitSound().sound() != null) {
      builder.hitSound(Key.key(component.hitSound().sound()));
    }

    item.setData(DataComponentTypes.PIERCING_WEAPON, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final PiercingWeapon weapon = item.getData(DataComponentTypes.PIERCING_WEAPON);
    if(weapon == null) {
      return serialized;
    }

    final PaperPiercingWeaponComponent component = (serialized.paperComponent(identifier()) instanceof final PiercingWeaponComponent<?, ?> getComponent)?
                                                   (PaperPiercingWeaponComponent)getComponent : new PaperPiercingWeaponComponent();

    component.dealsKnockback(weapon.dealsKnockback());
    component.dismounts(weapon.dismounts());

    if(weapon.sound() != null) {
      component.sound(new Sound(weapon.sound().asString(), 0.0f));
    }

    if(weapon.hitSound() != null) {
      component.hitSound(new Sound(weapon.hitSound().asString(), 0.0f));
    }

    serialized.applyComponent(component);
    return serialized;
  }
}