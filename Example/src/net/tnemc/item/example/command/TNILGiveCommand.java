package net.tnemc.item.example.command;
/*
 * The New Economy
 * Copyright (C) 2025 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import net.tnemc.item.example.Example;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

/**
 * TNILGiveCommand
 *
 * @author creatorfromhell
 * @since 1.0.0.0
 */
public class TNILGiveCommand implements CommandExecutor {

  @Override
  public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
    if(args.length != 2) {

      sender.sendMessage(ChatColor.RED + "Usage: /tnilgive <player> <amount>");
      return true;
    }

    final Player target = Bukkit.getPlayerExact(args[0]);
    if(target == null) {

      sender.sendMessage(ChatColor.RED + "Player not found.");
      return true;
    }

    final int amount;
    try {

      amount = Integer.parseInt(args[1]);
    } catch(final NumberFormatException e) {

      sender.sendMessage(ChatColor.RED + "Invalid number.");
      return true;
    }



    Example.instance().getPlatform().calculations().giveItems(Collections.singletonList(Example.instance().getItem().amount(10)), target.getInventory());
    Example.instance().getPlatform().calculations().giveItems(Collections.singletonList(Example.instance().getItem().amount(10)), target.getEnderChest());

    Example.instance().getPlatform().calculations().giveItems(Collections.singletonList(Example.instance().getNexoItem().amount(10)), target.getInventory());
    Example.instance().getPlatform().calculations().giveItems(Collections.singletonList(Example.instance().getNexoItem().amount(10)), target.getEnderChest());

    sender.sendMessage(ChatColor.GREEN + "Gave " + amount + " TNIL tokens to " + target.getName() + ".");
    return true;
  }
}