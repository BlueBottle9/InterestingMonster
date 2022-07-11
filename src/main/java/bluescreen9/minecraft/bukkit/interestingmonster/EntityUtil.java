package bluescreen9.minecraft.bukkit.interestingmonster;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import net.minecraft.nbt.NBTTagCompound;

public class EntityUtil {
						public static void setMoveSpeed(Entity entity,double speed) {
							((Attributable)entity).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
						}
						
						public static void setMaxHealth(Entity entity,double health) {
							((Attributable)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
						}
						
						public static void setArmorLvl(Entity entity,double armorlvl) {
							((Attributable)entity).getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armorlvl);
						}
						
						public static void setAttackDamage(Entity entity,double damage) {
							((Attributable)entity).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
						}
						
						public static void setAttackKnockBack(Entity entity,double knockback) {
							((Attributable)entity).getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(knockback);
						}
						
						public static void setKnockBackResistence(Entity entity,double knockback_resistence) {
							((Attributable)entity).getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(knockback_resistence);
						}
						
						public static double getArmorLvl(Entity entity) {
							return ((Attributable)entity).getAttribute(Attribute.GENERIC_ARMOR).getBaseValue();
						}
						
						public static double getMaxHealth(Entity entity) {
							return ((Attributable)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
						}
						
						public static double getAttackDamage(Entity entity) {
							return ((Attributable)entity).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();
						}
						
						public static double getAttackKnockBack(Entity entity) {
							return ((Attributable)entity).getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).getBaseValue();
						}
						
						public static double getKnockbackResistance(Entity entity) {
							return ((Attributable)entity).getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getBaseValue();
						}
						
						public static double getMoveSpeed(Entity entity) {
							return ((Attributable)entity).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
						}
						
						public static void setNBT(Entity entity,NBTTagCompound nbt) {
										net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity)entity).getHandle();
										nmsEntity.d(nbt);
						}
						
						public static NBTTagCompound getNBT(Entity entity) {
							return ((CraftEntity)entity).getHandle().f(new NBTTagCompound());
						}
						
						public static void setFollowRange(Entity entity,double follow_range) {
							((Attributable)entity).getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(follow_range);
						}
}
