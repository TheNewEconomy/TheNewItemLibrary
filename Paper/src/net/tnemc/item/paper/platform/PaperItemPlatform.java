package net.tnemc.item.paper.platform;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemEnchantGlint;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemFireResistant;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemFlag;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemMaxStack;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemModelData;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemRarity;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemTooltip;
import net.tnemc.item.bukkitbase.platform.impl.BukkitItemUnbreakable;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.impl.PaperItemAttribute;
import net.tnemc.item.paper.platform.impl.PaperItemDisplay;
import net.tnemc.item.paper.platform.impl.PaperItemEnchant;
import net.tnemc.item.paper.platform.impl.PaperItemFood;
import net.tnemc.item.paper.platform.impl.PaperItemJuke;
import net.tnemc.item.paper.platform.impl.PaperItemLore;
import net.tnemc.item.paper.platform.impl.PaperItemMaterial;
import net.tnemc.item.paper.platform.impl.PaperItemProfile;
import net.tnemc.item.paper.platform.impl.PaperItemSerialData;
import net.tnemc.item.paper.platform.impl.PaperItemTool;
import net.tnemc.item.platform.ItemPlatform;
import org.bukkit.inventory.ItemStack;

/**
 * PaperItemPlatform
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class PaperItemPlatform extends ItemPlatform<PaperItemStack, ItemStack> {

  public static final PaperItemPlatform PLATFORM = new PaperItemPlatform();

  private PaperItemPlatform() {

  }

  /**
   * @return the version that is being used currently
   */
  @Override
  public String version() {

    return ParsingUtil.version();
  }

  @Override
  public void addDefaults() {

    //bukkit base implementation.
    addMulti(new BukkitItemEnchantGlint<>());
    addMulti(new BukkitItemFireResistant<>());
    addMulti(new BukkitItemFlag<>());
    addMulti(new BukkitItemMaxStack<>());
    addMulti(new BukkitItemModelData<>());
    addMulti(new BukkitItemRarity<>());
    addMulti(new BukkitItemTooltip<>());
    addMulti(new BukkitItemUnbreakable<>());

    //Paper-specific
    addMulti(new PaperItemSerialData());
    addMulti(new PaperItemAttribute());
    addMulti(new PaperItemDisplay());
    addMulti(new PaperItemEnchant());
    addMulti(new PaperItemFood());
    addMulti(new PaperItemJuke());
    addMulti(new PaperItemLore());
    addMulti(new PaperItemMaterial());
    addMulti(new PaperItemProfile());
    addMulti(new PaperItemTool());
  }
}
