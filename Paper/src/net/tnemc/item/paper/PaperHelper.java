package net.tnemc.item.paper;

/*
 * The New Item Library Minecraft Server Plugin
 *
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

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.HelperMethods;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemFlag;

import java.util.LinkedList;
import java.util.UUID;

/**
 * BukkitHelper
 *
 * @author creatorfromhell
 * @since 0.1.7.5-Pre-5
 */
public class PaperHelper implements HelperMethods {

  final LinkedList<String> materialKeys = new LinkedList<>();
  final LinkedList<String> enchantmentKeys = new LinkedList<>();
  final LinkedList<String> itemFlagKeys = new LinkedList<>();

  public PaperHelper() {

    Registry.MATERIAL.forEach((material)->{

      if(material.isItem()) {
        materialKeys.add(material.getKey().getKey());
      }
    });

    if(VersionUtil.isVersion(PaperItemPlatform.instance().version(), "1.21")) {

      RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).forEach((enchantment)->{
        if(enchantment != null) {

          enchantmentKeys.add(enchantment.getKey().toString());
        }
      });


    } else {

      Registry.ENCHANTMENT.forEach((enchantment) -> {
        if(enchantment != null) {

          enchantmentKeys.add(enchantment.getKey().toString());
        }
      });
    }

    for(final ItemFlag itemFlag : ItemFlag.values()) {
      itemFlagKeys.add(itemFlag.name());
    }
  }

  @Override
  public LinkedList<String> materials() {

    return materialKeys;
  }

  @Override
  public LinkedList<String> enchantments() {

    return enchantmentKeys;
  }

  @Override
  public LinkedList<String> flags() {

    return itemFlagKeys;
  }

  public PlayerProfile base64(final String base64) {

    final PlayerProfile profile = Bukkit.createProfile(new UUID(base64.hashCode(), base64.hashCode()));
    profile.setProperty(new ProfileProperty("textures", base64));
    return profile;
  }
}