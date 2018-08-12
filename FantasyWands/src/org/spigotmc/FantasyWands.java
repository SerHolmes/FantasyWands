/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spigotmc;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author 
 */
public class FantasyWands extends JavaPlugin {
    
    
    
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new InteractWand(), this);
        getServer().getPluginManager().registerEvents(new ScoutDamage(), this);
        this.getCommand("fw").setExecutor(new CommandHelp());
        this.getCommand("fwlist").setExecutor(new CommandList());
        this.getCommand("fwskills").setExecutor(new CommandSkills());
    }
    
    @Override
    public void onDisable()
    {
    
    }
    
}