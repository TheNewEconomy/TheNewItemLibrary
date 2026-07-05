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

import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import net.tnemc.item.component.impl.TooltipDisplayComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * PaperTooltipDisplayComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperTooltipDisplayComponent extends TooltipDisplayComponent<PaperItemStack, ItemStack> {

  public PaperTooltipDisplayComponent() {

  }

  public PaperTooltipDisplayComponent(final boolean hideTooltip, final String... hiddenComponents) {

    super(hideTooltip, hiddenComponents);
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

    final Optional<PaperTooltipDisplayComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperTooltipDisplayComponent component = componentOptional.get();
    final TooltipDisplay.Builder builder = TooltipDisplay.tooltipDisplay().hideTooltip(component.hideTooltip());

    for(final String hiddenComponent : component.hiddenComponents()) {
      final DataComponentType type = dataComponentType(hiddenComponent);
      if(type != null) {
        builder.addHiddenComponents(type);
      }
    }

    item.setData(DataComponentTypes.TOOLTIP_DISPLAY, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final TooltipDisplay display = item.getData(DataComponentTypes.TOOLTIP_DISPLAY);
    if(display == null) {
      return serialized;
    }

    final PaperTooltipDisplayComponent component = (serialized.paperComponent(identifier()) instanceof final TooltipDisplayComponent<?, ?> getComponent)?
                                                   (PaperTooltipDisplayComponent)getComponent : new PaperTooltipDisplayComponent();

    component.hideTooltip(display.hideTooltip());
    component.hiddenComponents().clear();

    for(final DataComponentType hidden : display.hiddenComponents()) {
      component.hiddenComponents().add(dataComponentName(hidden));
    }

    serialized.applyComponent(component);
    return serialized;
  }

  private DataComponentType dataComponentType(final String name) {

    try {
      final Field field = DataComponentTypes.class.getField(name.toUpperCase());
      final Object value = field.get(null);

      if(value instanceof final DataComponentType type) {
        return type;
      }
    } catch(final Exception ignore) {
      // Unknown component type.
    }

    return null;
  }

  private String dataComponentName(final DataComponentType type) {

    for(final Field field : DataComponentTypes.class.getFields()) {
      try {
        if(field.get(null) == type) {
          return field.getName().toLowerCase();
        }
      } catch(final Exception ignore) {
        // Continue.
      }
    }

    return type.toString();
  }
}