package bluescreen9.minecraft.bukkit.interestingmonster;

import java.util.ArrayList;

import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class EntityDamageListener implements Listener{
						@EventHandler
						public void onEntityDamage(EntityDamageByEntityEvent event) {
										Entity damager = event.getDamager();
										if (damager instanceof  Mob) {
												if (damager instanceof Monster || damager instanceof Giant) {//ҩˮЧ��
													JSONArray jsonArray = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(damager)).getJSONArray("attack-effects");
													if (jsonArray != null) {
														ArrayList<JSONObject> effects = (ArrayList<JSONObject>) jsonArray.toJavaList(JSONObject.class);
														if (effects != null) {
															if (!effects.isEmpty()) {
																	for (JSONObject jsonObject:effects) {
																		if (Green_Random.chance(jsonObject.getDoubleValue("chance"))) {
																			String type = jsonObject.getString("type").replaceAll("minecraft:", "").toUpperCase();
																			int time = jsonObject.getIntValue("time");
																			if (time <= 0) {
																				time = Integer.MAX_VALUE;
																			}
																			int lvl = jsonObject.getIntValue("lvl");
																			if (event.getEntity() instanceof LivingEntity) {
																			new PotionEffect(PotionEffectType.getByName(type), time, lvl, true, true, true).apply((LivingEntity) event.getEntity());
																			}
																			}
																	}
															}
														}
													}
												}
												
												if (damager instanceof EnderDragon) {
													JSONArray jsonArray = Configuration.Configuration.getJSONObject("bosses").getJSONObject(Configuration.getConfigName(damager)).getJSONArray("attack-effects");
													if (jsonArray != null) {
														ArrayList<JSONObject> effects = (ArrayList<JSONObject>) jsonArray.toJavaList(JSONObject.class);
														if (effects != null) {
															if (!effects.isEmpty()) {
																	for (JSONObject jsonObject:effects) {
																		if (Green_Random.chance(jsonObject.getDoubleValue("chance"))) {
																			String type = jsonObject.getString("type").replaceAll("minecraft:", "").toUpperCase();
																			int time = jsonObject.getIntValue("time");
																			if (time <= 0) {
																				time = Integer.MAX_VALUE;
																			}
																			int lvl = jsonObject.getIntValue("lvl");
																			new PotionEffect(PotionEffectType.getByName(type), time, lvl, true, true, true);
																		}
																			
																	}
															}
														}
													}
												}
												
												if (damager instanceof Wither) {
													JSONArray jsonArray = Configuration.Configuration.getJSONObject("bosses").getJSONObject(Configuration.getConfigName(damager)).getJSONArray(".attack-effects");
													if (jsonArray != null) {
														ArrayList<JSONObject> effects = (ArrayList<JSONObject>) jsonArray.toJavaList(JSONObject.class);
														if (effects != null) {
															if (!effects.isEmpty()) {
																	for (JSONObject jsonObject:effects) {
																		if (Green_Random.chance(jsonObject.getDoubleValue("chance"))) {
																			String type = jsonObject.getString("type").replaceAll("minecraft:", "").toUpperCase();
																			int time = jsonObject.getIntValue("time");
																			if (time <= 0) {
																				time = Integer.MAX_VALUE;
																			}
																			int lvl = jsonObject.getIntValue("lvl");
																			new PotionEffect(PotionEffectType.getByName(type), time, lvl, true, true, true);
																		}
																			
																	}
															}
														}
													}
												}
										}
						}
						
						@EventHandler
						public void onEntityDamgaeByExplosion(EntityDamageByEntityEvent event) {
							Entity damager = event.getDamager();
											if (ExplosionData.NoDamageList.contains(damager.getUniqueId())) {
														event.setCancelled(true);
														ExplosionData.NoDamageList.remove(damager.getUniqueId());
											}
											if (Configuration.CannotDamage.containsKey(damager.getType())) {
												ArrayList<EntityType> arrayList = Configuration.CannotDamage.get(damager.getType());
													if (arrayList != null) {
														if (!arrayList.isEmpty()) {
																if (arrayList.contains(event.getEntity().getType())) {
																	event.setCancelled(true);
																}
														}
													}
											}
						}
}
