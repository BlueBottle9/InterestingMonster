package bluescreen9.minecraft.bukkit.interestingmonster.ai;

import java.util.EnumSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

import bluescreen9.minecraft.bukkit.interestingmonster.ExplosionData;
import bluescreen9.minecraft.bukkit.interestingmonster.ExtraInventory;
import bluescreen9.minecraft.bukkit.interestingmonster.Green_Random;
import bluescreen9.minecraft.bukkit.interestingmonster.Main;

public class FireBallAI implements Goal<Mob>{
	
	private Mob mob;
	
	public FireBallAI(Mob mob) {
			this.mob = mob;
	}
	
	public boolean shouldActivate() {
		LivingEntity target = mob.getTarget();
		if (!AIStates.States.get(mob.getUniqueId()).getCanFireBall()) {
			return false;
		}
		if (target == null) {
			return false;
		}
		Location loc1 =mob.getLocation();
		Location loc2 = target.getLocation();
		if (loc1.distance(loc2) < 10) {
				return false;
		}
		if (loc1.getBlockY() < loc2.getBlockY() && loc1.getBlockY() - loc2.getBlockY() > 30) {
			return false;
		}
		if (!ExtraInventory.Data.get(mob.getUniqueId()).containsAtLeast(Items.FIRE_CHARGE, 1)) {
			return false;
		}
		if (!Green_Random.chance(0.01)) {
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
		ItemStack orginalItem = mob.getEquipment().getItemInMainHand();
		Inventory inventory = ExtraInventory.Data.get(mob.getUniqueId());
		ItemStack item = inventory.getItem(inventory.first(Material.FIRE_CHARGE));
		mob.getEquipment().setItemInMainHand(item, true);
		Location loc1 = mob.getLocation();
		Location loc2 = mob.getTarget().getLocation();
		LargeFireball fireball = (LargeFireball) loc1.getWorld().spawnEntity(mob.getEyeLocation(), EntityType.FIREBALL);
		Vector vector = loc1.toVector().subtract(loc2.add(0D, -1D, 0D).toVector()).multiply(-0.1);
		ExplosionData.OtherLauchers.add(fireball);
		fireball.setDirection(vector);
		mob.getEquipment().setItemInMainHand(orginalItem);
		item.setAmount(item.getAmount() - 1);
	}
}
