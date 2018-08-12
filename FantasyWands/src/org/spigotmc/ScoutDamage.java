/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spigotmc;

import com.sucy.skill.SkillAPI;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventException;
import com.sucy.skill.api.classes.RPGClass;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ScoutDamage implements Listener
{
            
    double strength = 0;
    String lvl = "equal";
    RPGClass scout = SkillAPI.getClass("Scout");
    RPGClass thief = SkillAPI.getClass("Thief");
    RPGClass rogue = SkillAPI.getClass("Rogue");
    RPGClass assassin = SkillAPI.getClass("Assassin");
    RPGClass reaper = SkillAPI.getClass("Reaper");
    Player dude;
    
    
    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) throws EventException
    {
        Player p = event.getPlayer();
        dude = p;
        int level = 1;
        
        if(p.getInventory().getItemInMainHand().getType()== Material.AIR) return;
        
        if(!(p.getInventory().getItemInMainHand().getItemMeta().hasLore())) return;
        
        List<String> lores = p.getInventory().getItemInMainHand().getItemMeta().getLore();
        List<String> nocolorlores = new ArrayList<>();
        for(String s: lores)
            nocolorlores.add(ChatColor.stripColor(s));
        
        for(String s: nocolorlores)
        {
            if(s.contains("Level: "))
            {
                String levelvalue = s.substring(7);
                level = Integer.parseInt(levelvalue);
                break;
            }
        }
        
            
        if(!(nocolorlores.contains("Class: Scout"))) return;
        
        if(!(SkillAPI.getPlayerAccountData(p).getActiveData().isClass(scout))) return;
        
        if((level > SkillAPI.getPlayerAccountData(p).getActiveData().getMainClass().getLevel()))
        {
            lvl = "High";
            return;
        }

        for(String s: nocolorlores)
            {
                if(s.contains("Strength: "))
                {
                    String attrvalue = s.substring(10);
                    strength = (double) Integer.parseInt(attrvalue);
                    p.sendMessage(attrvalue);
                    break;
                }
            }
        }
    
    
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) 
    {
        double damage = strength;
        Player p = dude;
        int strengthvalue = SkillAPI.getPlayerData(p).getAttribute("Strength");
        damage += strengthvalue;
        Entity victim = event.getEntity();
        if(p.getInventory().getItemInMainHand().getType()== Material.AIR) return;
        
            if(lvl.equals("equal"))
            {
                if((SkillAPI.getPlayerAccountData(p).getActiveData().isClass(scout)) || (SkillAPI.getPlayerAccountData(p).getActiveData().isClass(thief)) || (SkillAPI.getPlayerAccountData(p).getActiveData().isClass(rogue)) || (SkillAPI.getPlayerAccountData(p).getActiveData().isClass(assassin)) || (SkillAPI.getPlayerAccountData(p).getActiveData().isClass(reaper)))
                {
                    if(victim instanceof Damageable)
                        ((Damageable) victim).damage((event.getDamage()) + damage);
                }
            }
    }
}