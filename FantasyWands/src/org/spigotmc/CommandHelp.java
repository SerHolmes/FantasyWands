/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spigotmc;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Vincent Tan Z S
 */
public class CommandHelp implements CommandExecutor {

    String[] help = {ChatColor.GOLD.UNDERLINE + "Fantasy Wands",
                     "",
                     ChatColor.GREEN + "Custom Basic Steed's Fantasy plugin",
                     ChatColor.LIGHT_PURPLE + "Plugin is designed just for wands but their lore must include ",
                     ChatColor.LIGHT_PURPLE + "Class: Mage AND Skill: {Skill_Name}",
                     ChatColor.LIGHT_PURPLE + "Coloring that lore will work. Only sticks can be used as wands currently",
                     ChatColor.LIGHT_PURPLE + "Additionally, the Strength attribute for Scout increases attack damage per 1",
                     "",
                     ChatColor.GREEN + "/fw - FantasyWands Info and Help",
                     ChatColor.GREEN + "/fwlist - List of Items usable for Wands",
                     ChatColor.GREEN + "/fwskills - List of Skills usable for Wands"};
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args)
    {
        if(sender instanceof Player)
        {
            sender.sendMessage(help);   
        }
        
        return true;
    }
}
