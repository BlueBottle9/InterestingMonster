package bluescreen9.minecraft.bukkit.interestingmonster.ai;

import java.util.EnumSet;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

import bluescreen9.minecraft.bukkit.interestingmonster.ExtraInventory;
import bluescreen9.minecraft.bukkit.interestingmonster.Green_Random;
import bluescreen9.minecraft.bukkit.interestingmonster.Main;

public class EnderPrearlAI implements Goal<Mob>{

	private Mob mob;
	
	public EnderPrearlAI(Mob mob) {
			this.mob = mob;
	}
	
	public boolean shouldActivate() {
				if (!AIStates.States.get(mob.getUniqueId()).getCanPearl()) {
						return false;
				}
				if (mob.getTarget() == null) {
					return false;
				}
				if (!(mob.getLocation().distance(mob.getTarget().getLocation()) >= 12)) {
					return false;	
				}
				if (!ExtraInventory.Data.get(mob.getUniqueId()).containsAtLeast(Items.ENDER_PEARL, 1)) {
						return false;
				}
				if (!Green_Random.chance(0.05)) {
					return false;
				}
				if (EnderPearlTargets.Targets.containsValue(mob)) {
					return false;
				}
		return true;
	}

	public GoalKey<Mob> getKey() {
		return GoalKey.of(Mob.class, new NamespacedKey(Main.getPlugin(Main.class), "monsterenhance"));
	}

	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.MOVE);
	}
	
	public void start() {
				try {
					AIStates.States.get(mob.getUniqueId()).setCanPearl(false);
					new BukkitRunnable() {
						public void run() {
							AIStates.States.get(mob.getUniqueId()).setCanPearl(true);
						}
					}.runTaskLater(Main.getPlugin(Main.class), 60L);
				} catch (Exception e) {
					
				}
				ItemStack orgainalItem = mob.getEquipment().getItemInMainHand();
				Inventory inventory = ExtraInventory.Data.get(mob.getUniqueId());
				ItemStack item = inventory.getItem(inventory.first(Material.ENDER_PEARL));
				mob.getEquipment().setItemInMainHand(item, true);
				EnderPearl enderPearl = (EnderPearl) mob.getWorld().spawnEntity(mob.getEyeLocation(),EntityType.ENDER_PEARL); 
				EnderPearlTargets.Targets.put(enderPearl, mob);
				enderPearl.setVelocity(mob.getLocation().toVector().subtract(mob.getTarget().getLocation().toVector()).multiply(-0.1));
				item.setAmount(item.getAmount() - 1);
				mob.getEquipment().setItemInMainHand(orgainalItem);
	}

}
