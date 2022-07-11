package bluescreen9.minecraft.bukkit.interestingmonster;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;

import com.alibaba.fastjson.JSONObject;

public class SunDamageListener implements Listener{
					@EventHandler
					public void onSunDamgeEntity(EntityCombustEvent event) {
									if (!(event instanceof EntityCombustByBlockEvent) && !(event instanceof EntityCombustByEntityEvent)) {
										if (Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(event.getEntity())) == null) {
											return;
										}
										JSONObject jsonObject = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(event.getEntity()));
											if (jsonObject.keySet().contains("sun-damage")) {
													if (!jsonObject.getBooleanValue("sun-damage")) {
															event.setCancelled(true);
															event.setDuration(-20);
													}
											}
									}
					}
}
