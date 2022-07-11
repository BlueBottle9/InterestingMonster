package bluescreen9.minecraft.bukkit.interestingmonster.ai;

import java.util.EnumSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

import bluescreen9.minecraft.bukkit.interestingmonster.ExtraInventory;
import bluescreen9.minecraft.bukkit.interestingmonster.Green_Random;
import bluescreen9.minecraft.bukkit.interestingmonster.Main;

public class TntAI implements Goal<Mob>{

	private Mob mob;
	public TntAI(Mob thrower) {
			mob = thrower;
	}
	
	public boolean shouldActivate() {
		if (mob.getTarget() == null) {
			return false;
		}
		if (!AIStates.States.get(mob.getUniqueId()).getCanThrowTnt()) {
				return false;
		}
	LivingEntity target = mob.getTarget();
	Location mobLoc = mob.getLocation();
	Location targetLoc = target.getLocation();
	if (targetLoc.getBlockY() - mobLoc.getBlockY() > 15 && target.getLocation().getBlockY() > mob.getLocation().getBlockY()) {
		return false;
	}
	
	Inventory inventory = ExtraInventory.Data.get(mob.getUniqueId());
	if (inventory == null) {
		return false;
	}
	if (!inventory.containsAtLeast(Items.TNT, 1)) {
		return false;
	}
	if (!Green_Random.chance(0.015)) {
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
				}.runTaskLater(Main.getPlugin(Main.class), 120L);
			} catch (Exception e) {
			}
			ItemStack orginalItem = mob.getEquipment().getItemInMainHand();
			Inventory inventory = ExtraInventory.Data.get(mob.getUniqueId());
			ItemStack item = inventory.getItem(inventory.first(Material.TNT));
			mob.getEquipment().setItemInMainHand(item, true);
			TNTPrimed tnt = (TNTPrimed) mob.getWorld().spawnEntity(mob.getEyeLocation(), EntityType.PRIMED_TNT);
			Location loc1 = mob.getLocation();
			Location loc2 = mob.getTarget().getLocation();
			Vector vector = loc1.toVector().subtract(loc2.toVector()).multiply(-0.1);
			tnt.setVelocity(vector);
			mob.getEquipment().setItemInMainHand(orginalItem);
			item.setAmount(item.getAmount() - 1);
	}
}
