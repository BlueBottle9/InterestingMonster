package bluescreen9.minecraft.bukkit.interestingmonster;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SlimeSplitEvent;

import com.alibaba.fastjson.JSONObject;

public class SplitListener implements Listener{
					@EventHandler
					public void onSplit(SlimeSplitEvent event) {
						JSONObject explosionConfig = Configuration.Configuration.getJSONObject("monsters").getJSONObject("slime").getJSONObject("explosion");
						float power = explosionConfig.getFloatValue("power");
						boolean fire = explosionConfig.getBooleanValue("fire");
						boolean breakBlocks = explosionConfig.getBooleanValue("break-blocks");
						boolean damageEntities = explosionConfig.getBooleanValue("damage-entities");
								if (power > 0) {
									Entity entity = event.getEntity();
										if (!damageEntities) {
											ExplosionData.NoDamageList.add(entity.getUniqueId());
										}
										World world = entity.getWorld();
										Location loc = entity.getLocation();
										world.createExplosion(entity, loc, power, fire, breakBlocks);
								}
					}
}
