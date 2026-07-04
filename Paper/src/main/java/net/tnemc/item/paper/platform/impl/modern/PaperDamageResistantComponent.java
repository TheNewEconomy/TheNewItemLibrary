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
import io.papermc.paper.datacomponent.item.DamageResistant;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistrySet;
import net.tnemc.item.component.impl.DamageResistantComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Registry;
import org.bukkit.damage.DamageType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * PaperDamageResistantComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperDamageResistantComponent extends DamageResistantComponent<PaperItemStack, ItemStack> {

  public PaperDamageResistantComponent() {

  }

  public PaperDamageResistantComponent(final String type) {

    super(type);
  }

  public PaperDamageResistantComponent(final List<String> types) {

    super(types);
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

    final Optional<PaperDamageResistantComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty() || componentOptional.get().types().isEmpty()) {
      return item;
    }
    final List<TypedKey<DamageType>> typeSet = new ArrayList<>();
    for(final String type : componentOptional.get().types()) {


      typeSet.add(RegistryKey.DAMAGE_TYPE.typedKey(type));
    }

    item.setData(DataComponentTypes.DAMAGE_RESISTANT, DamageResistant.damageResistant(RegistrySet.keySet(RegistryKey.DAMAGE_TYPE, typeSet)));

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final DamageResistant resistant = item.getData(DataComponentTypes.DAMAGE_RESISTANT);
    if(resistant == null) {
      return serialized;
    }

    final PaperDamageResistantComponent component = (serialized.paperComponent(identifier()) instanceof final DamageResistantComponent<?, ?> getComponent)?
                                                    (PaperDamageResistantComponent)getComponent : new PaperDamageResistantComponent();

    component.types(resistant.types().registryKey().key().asString());

    serialized.applyComponent(component);
    return serialized;
  }
}