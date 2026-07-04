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
import io.papermc.paper.datacomponent.item.ItemArmorTrim;
import net.tnemc.item.component.impl.TrimComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.Optional;

/**
 * PaperTrimComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperTrimComponent extends TrimComponent<PaperItemStack, ItemStack> {

  public PaperTrimComponent() {

  }

  public PaperTrimComponent(final String pattern, final String material) {

    super(pattern, material);
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

    final Optional<PaperTrimComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperTrimComponent component = componentOptional.get();
    final TrimMaterial material = PaperItemPlatform.instance().converter().convert(component.material(), TrimMaterial.class);
    final TrimPattern pattern = PaperItemPlatform.instance().converter().convert(component.pattern(), TrimPattern.class);

    item.setData(DataComponentTypes.TRIM, ItemArmorTrim.itemArmorTrim(new ArmorTrim(material, pattern)));
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final ItemArmorTrim trim = item.getData(DataComponentTypes.TRIM);
    if(trim == null) {
      return serialized;
    }

    final PaperTrimComponent component = (serialized.paperComponent(identifier()) instanceof final TrimComponent<?, ?> getComponent)?
                                         (PaperTrimComponent)getComponent : new PaperTrimComponent();

    component.material(PaperItemPlatform.instance().converter().convert(trim.armorTrim().getMaterial(), String.class));
    component.pattern(PaperItemPlatform.instance().converter().convert(trim.armorTrim().getPattern(), String.class));

    serialized.applyComponent(component);
    return serialized;
  }
}