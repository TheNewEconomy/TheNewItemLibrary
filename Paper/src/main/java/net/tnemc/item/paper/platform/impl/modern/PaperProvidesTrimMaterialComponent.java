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
import net.tnemc.item.component.impl.ProvidesTrimMaterialComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.TrimMaterial;

import java.util.Optional;

/**
 * PaperProvidesTrimMaterialComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperProvidesTrimMaterialComponent extends ProvidesTrimMaterialComponent<PaperItemStack, ItemStack> {

  public PaperProvidesTrimMaterialComponent() {

  }

  public PaperProvidesTrimMaterialComponent(final String material) {

    super(material);
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

    final Optional<PaperProvidesTrimMaterialComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final TrimMaterial material = PaperItemPlatform.instance().converter().convert(componentOptional.get().material(), TrimMaterial.class);
    item.setData(DataComponentTypes.PROVIDES_TRIM_MATERIAL, material);

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final TrimMaterial material = item.getData(DataComponentTypes.PROVIDES_TRIM_MATERIAL);
    if(material == null) {
      return serialized;
    }

    final PaperProvidesTrimMaterialComponent component = (serialized.paperComponent(identifier()) instanceof final ProvidesTrimMaterialComponent<?, ?> getComponent)?
                                                         (PaperProvidesTrimMaterialComponent)getComponent : new PaperProvidesTrimMaterialComponent();

    component.material(PaperItemPlatform.instance().converter().convert(material, String.class));

    serialized.applyComponent(component);
    return serialized;
  }
}