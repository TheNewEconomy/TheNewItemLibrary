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

import com.destroystokyo.paper.profile.ProfileProperty;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import net.tnemc.item.component.impl.ProfileComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.SkullProfile;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Optional;

/**
 * PaperOldProfileComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperProfileComponent extends ProfileComponent<PaperItemStack, ItemStack> {

  public PaperProfileComponent() {

  }

  public PaperProfileComponent(final SkullProfile profile) {

    super(profile);
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

    return item.getItemMeta() instanceof SkullMeta;
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   * @since 0.2.0.0
   */
  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperProfileComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final SkullProfile profile = componentOptional.get().profile;
    if(profile.getTexture() == null) {
      return item;
    }

    final ResolvableProfile.Builder builder = ResolvableProfile.resolvableProfile();

    builder.name(profile.getName());
    builder.uuid(profile.getUuid());
    builder.addProperty(new ProfileProperty("textures", profile.getTexture()));

    item.setData(DataComponentTypes.PROFILE, builder);

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
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final ResolvableProfile resolvableProfile = item.getData(DataComponentTypes.PROFILE);
    if(resolvableProfile == null) {
      return serialized;
    }

    final SkullProfile skull = new SkullProfile();
    skull.setUuid(resolvableProfile.uuid());
    skull.setName(resolvableProfile.name());

    for(final ProfileProperty property : resolvableProfile.properties()) {
      if(property.getName().equalsIgnoreCase("textures")) {

        skull.setTexture(property.getValue());
      }
    }

    serialized.applyComponent(this);
    return serialized;
  }
}