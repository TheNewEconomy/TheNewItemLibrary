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
import io.papermc.paper.datacomponent.item.LodestoneTracker;
import net.tnemc.item.component.impl.LodestoneTrackerComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperLodestoneTrackerComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperLodestoneTrackerComponent extends LodestoneTrackerComponent<PaperItemStack, ItemStack> {

  public PaperLodestoneTrackerComponent() {

  }

  public PaperLodestoneTrackerComponent(final String target, final int[] pos, final String dimension, final boolean tracked) {

    super(target, pos, dimension, tracked);
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

    final Optional<PaperLodestoneTrackerComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperLodestoneTrackerComponent component = componentOptional.get();
    Location location = null;

    if(component.dimension() != null && component.pos() != null && component.pos().length >= 3) {
      final World world = Bukkit.getWorld(component.dimension());
      if(world != null) {
        location = new Location(world, component.pos()[0], component.pos()[1], component.pos()[2]);
      }
    }

    item.setData(DataComponentTypes.LODESTONE_TRACKER, LodestoneTracker.lodestoneTracker(location, component.tracked()));
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final LodestoneTracker tracker = item.getData(DataComponentTypes.LODESTONE_TRACKER);
    if(tracker == null) {
      return serialized;
    }

    final PaperLodestoneTrackerComponent component = (serialized.paperComponent(identifier()) instanceof final LodestoneTrackerComponent<?, ?> getComponent)?
                                                     (PaperLodestoneTrackerComponent)getComponent : new PaperLodestoneTrackerComponent();

    final Location location = tracker.location();
    if(location != null) {
      component.pos(new int[] { location.getBlockX(), location.getBlockY(), location.getBlockZ() });

      if(location.getWorld() != null) {
        component.dimension(location.getWorld().getName());
      }
    }

    component.tracked(tracker.tracked());

    serialized.applyComponent(component);
    return serialized;
  }
}