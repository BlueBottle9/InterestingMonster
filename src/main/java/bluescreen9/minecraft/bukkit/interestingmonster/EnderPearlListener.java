package bluescreen9.minecraft.bukkit.interestingmonster;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import bluescreen9.minecraft.bukkit.interestingmonster.ai.EnderPearlTargets;

public class EnderPearlListener implements Listener{
					@EventHandler
					public void onEnderPearHit(ProjectileHitEvent event) {
								if (event.getEntity().getType().equals(EntityType.ENDER_PEARL)) {
										EnderPearl pearl = (EnderPearl) event.getEntity();
										if (EnderPearlTargets.Targets.containsKey(pearl)) {
												EnderPearlTargets.Targets.get(pearl).teleport(pearl.getLocation());
												EnderPearlTargets.Targets.remove(pearl);
										}
								}
					}
}
