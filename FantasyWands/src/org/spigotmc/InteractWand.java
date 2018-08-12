/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spigotmc;

import com.sucy.skill.SkillAPI;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventException;
import org.bukkit.event.block.Action;
import org.bukkit.Sound;
import java.util.HashMap;
import com.sucy.skill.api.classes.RPGClass;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class InteractWand implements Listener
{
            
    public HashMap<String, Long> cooldowns = new HashMap<>();
    double strength = 0;
    Player dude;
    
    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) throws EventException
    {
        Player p = event.getPlayer();
        dude = p;
        RPGClass mage = SkillAPI.getClass("Mage");
        RPGClass wizard = SkillAPI.getClass("Wizard");
        RPGClass sorceror = SkillAPI.getClass("Sorceror");
        RPGClass warlock = SkillAPI.getClass("Warlock");
        RPGClass sage = SkillAPI.getClass("Sage");
        int cooldownTime = 2;
        long secondsLeft = 0;
        int level = 1;
        String skill = "";
        Location crosshair = p.getTargetBlock(null, 100).getLocation();
        
        
        if((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK))
        {
            if(!((p.getInventory().getItemInMainHand().getType()== Material.STICK) || (p.getInventory().getItemInMainHand().getType()== Material.END_ROD) || (p.getInventory().getItemInMainHand().getType()== Material.BLAZE_ROD) || (p.getInventory().getItemInMainHand().getType()== Material.BOOK) || (p.getInventory().getItemInMainHand().getType()== Material.REDSTONE_TORCH_ON))) return;

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
            
            for(String s: nocolorlores)
            {
                if(s.contains("Skill: "))
                {
                    skill = s.substring(7);
                    break;
                }
            }
            
            if(!(nocolorlores.contains("Class: Mage"))) return;
            if(skill.isEmpty()) return;
            
            if(level > SkillAPI.getPlayerAccountData(p).getActiveData().getMainClass().getLevel())
            {
                p.sendMessage(ChatColor.DARK_RED + "You cannot equip that item");
                return;
            }
            
            if((!(SkillAPI.getPlayerAccountData(p).getActiveData().isClass(mage))) && (!(SkillAPI.getPlayerAccountData(p).getActiveData().isClass(wizard))) && (!(SkillAPI.getPlayerAccountData(p).getActiveData().isClass(sorceror))) && (!(SkillAPI.getPlayerAccountData(p).getActiveData().isClass(warlock))) && (!(SkillAPI.getPlayerAccountData(p).getActiveData().isClass(sage))) )
            {
                p.sendMessage(ChatColor.DARK_RED + "You cannot equip that item");
                return;
            }
            
            for(String s: nocolorlores)
            {
                if(s.contains("Strength: "))
                {
                    String attrvalue = s.substring(10);
                    strength = (double) Integer.parseInt(attrvalue);
                    break;
                }
            }

            if(cooldowns.containsKey(p.getName()))
            {
                secondsLeft = ((cooldowns.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
            }

            if(secondsLeft > 0)
            {
                p.sendMessage(ChatColor.DARK_RED + "Cooldown: " + secondsLeft + " seconds");
                return;
            }
            
            if(skill.equals("Fireball"))
            {
                SmallFireball fire = p.getWorld().spawn(event.getPlayer().getEyeLocation(), SmallFireball.class);
                p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
                fire.setVelocity(p.getLocation().getDirection().multiply(2));
                fire.setIsIncendiary(false);
                fire.setYield(0.0f);
                fire.setShooter(p);
            }
            else if(skill.equals("Lightning"))
            {
                
                LightningStrike lightning = p.getWorld().strikeLightning(crosshair);
            }
            
            cooldowns.put(p.getName(), System.currentTimeMillis());
        }
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) 
    {
        double damage = strength;
        Entity victim = event.getEntity();
        
        if(SkillAPI.getPlayerAccountData(dude).getActiveData().hasAttribute("Strength"))
            damage += SkillAPI.getPlayerAccountData(dude).getActiveData().getInvestedAttribute("Strength");

        if (event.getDamager() instanceof SmallFireball) 
        {
            if(victim instanceof Damageable)
            {
                ((Damageable) victim).damage((event.getDamage()-8) + damage);
            }
        }
    }
    
}
