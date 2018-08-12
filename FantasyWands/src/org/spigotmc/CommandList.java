/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spigotmc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.md_5.bungee.api.ChatColor;
/**
 *
 * @author Vincent Tan Z S
 */
public class CommandList implements CommandExecutor {
    
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args)
    {
        sender.sendMessage(ChatColor.WHITE.UNDERLINE + "Wand List");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GREEN + "Stick, Blaze Rod, Redstone Torch, End Rod, Book");
        
        return true;
    }
    
}
