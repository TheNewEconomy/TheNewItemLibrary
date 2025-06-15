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

/**
 * TNILCountCommand
 *
 * @author creatorfromhell
 * @since 1.0.0.0
 */
public class TNILCountCommand implements CommandExecutor {


  @Override
  public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

    if(!(sender instanceof Player) && args.length == 0) {

      sender.sendMessage("Console must specify a player.");
      return true;
    }

    final Player target;
    if(args.length >= 1) {

      target = Bukkit.getPlayerExact(args[0]);
      if(target == null) {

        sender.sendMessage("Player not found.");
        return true;
      }
    } else {

      target = (Player) sender;
    }

    final int normalAmount = Example.instance().getPlatform().calculations().count(Example.instance().getItem(), target.getInventory());
    final int normalAmountEnder = Example.instance().getPlatform().calculations().count(Example.instance().getItem(), target.getEnderChest());
    final int nexoItem = Example.instance().getPlatform().calculations().count(Example.instance().getNexoItem(), target.getInventory());
    final int nexoItemEnder = Example.instance().getPlatform().calculations().count(Example.instance().getNexoItem(), target.getEnderChest());


    final int totalNormal = normalAmount + normalAmountEnder;
    final int totalNexo = nexoItem + nexoItemEnder;

    sender.sendMessage(ChatColor.GOLD + "== TNIL Token Report ==");
    sender.sendMessage(ChatColor.YELLOW + "Target: " + ChatColor.AQUA + target.getName());
    sender.sendMessage(ChatColor.YELLOW + "Normal Item: " + ChatColor.GREEN + totalNormal + ChatColor.GRAY +
                       " (" + normalAmount + " inv, " + normalAmountEnder + " ender)");
    sender.sendMessage(ChatColor.YELLOW + "Nexo Item: " + ChatColor.GREEN + totalNexo + ChatColor.GRAY +
                       " (" + nexoItem + " inv, " + nexoItemEnder + " ender)");
    return true;
  }
}
