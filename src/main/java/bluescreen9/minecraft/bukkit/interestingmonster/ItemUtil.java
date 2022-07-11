package bluescreen9.minecraft.bukkit.interestingmonster;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import net.minecraft.nbt.NBTTagCompound;

public class ItemUtil {
			public static void setDurability(ItemStack item,int durability) {
						Damageable damageable = (Damageable) item.getItemMeta();
						damageable.setDamage(durability);
						item.setItemMeta(damageable);
			}
			
			public static int getMaxDurability(Material type) {
				return new ItemStack(type).getAmount();
			}
			
			public static int getDurability(ItemStack item) {
				return ((Damageable)item.getItemMeta()).getDamage();
			}
			
			public static ItemStack setNBTData(ItemStack item,NBTTagCompound nbt) {
						net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
						nmsItem.c(nbt);
						return CraftItemStack.asBukkitCopy(nmsItem);
			}
			
			public static NBTTagCompound getNBTData(ItemStack item) {
				net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
				return nmsItem.b(new NBTTagCompound());
			}
			
			public static EquipmentType getEquipmentType(ItemStack equipment) {
					if (equipment.getType().name().toLowerCase().endsWith("boot")) {
						return EquipmentType.BOOT;
					}
					if (equipment.getType().name().toLowerCase().endsWith("chestplate")) {
						return EquipmentType.CHESTPLATE;
					}
					if (equipment.getType().name().toLowerCase().endsWith("legging")) {
						return EquipmentType.LEGGING;
					}
					
					if (equipment.getType().name().toLowerCase().endsWith("helmet")) {
						return EquipmentType.HELMET;
					}
					
					return EquipmentType.WEAPON;
			}
}
