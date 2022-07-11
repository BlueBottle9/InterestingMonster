package bluescreen9.minecraft.bukkit.interestingmonster;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.alibaba.fastjson.JSONObject;

public class EntityDeathListener implements Listener{
						@EventHandler
						public void onEntityDie(EntityDeathEvent event) {
								Entity entity = event.getEntity();
								if (entity instanceof Mob) {
									if (entity instanceof Monster) {
										Monster monster = (Monster) entity;
											//�������
											JSONObject jsonObject = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(monster)).getJSONObject("xp-drops");
											if (jsonObject != null) {
												if (jsonObject.getBooleanValue("enable")) {
													event.setDroppedExp((int) Green_Random.randomLong(jsonObject.getLongValue("min"), jsonObject.getLongValue("max")));
												}
											}
											//������Ʒ����
											UUID uuid = monster.getUniqueId();
											if (LootData.Data.keySet().contains(uuid)) {
													for (ItemStack item:LootData.Data.get(uuid)) {
															if (item != null) {
															monster.getLocation().getWorld().dropItemNaturally(monster.getLocation(), item);
															}
													}
													LootData.Data.remove(uuid);
											} 
											
									}
								}
								if (ExtraInventory.Data.containsKey(entity.getUniqueId())) {
									ExtraInventory.Data.remove(entity.getUniqueId());
								}
								
								for (Mob mob:entity.getWorld().getEntitiesByClass(Mob.class)) {
									if (entity.equals(mob.getTarget())) {
										mob.setTarget(null);
									}
								}
						}
}
