package bluescreen9.minecraft.bukkit.interestingmonster;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import bluescreen9.minecraft.bukkit.interestingmonster.ai.AILoader;
import net.minecraft.nbt.MojangsonParser;


public class EntitySpawnListener implements Listener{
					@EventHandler
					public void onMobSpawn(EntitySpawnEvent event) {
							if (!(event.getEntity() instanceof Mob)) {
								return;
							}
							if (event.getEntityType().equals(EntityType.FOX) || event.getEntityType().equals(EntityType.BAT) ||
									event.getEntityType().equals(EntityType.SQUID) || event.getEntityType().equals(EntityType.GLOW_SQUID) ||
									event.getEntityType().equals(EntityType.SALMON)) {
								return;
							}
							
							Mob mob = (Mob) event.getEntity();
							//视野距离
							EntityUtil.setFollowRange(mob, Configuration.Configuration.getJSONObject("gamerules").getDoubleValue("monster-search-range"));
							//最大生命值
							double maxHealth = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getDoubleValue("health");
							EntityUtil.setMaxHealth(mob, maxHealth);
							mob.setHealth(maxHealth);
							
							//额外护甲值ֵ
							double basicArmor =  Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getDoubleValue("basic-armorlvl");
							JSONObject extraArmor =  Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getJSONObject("extra-armorlvl");
							EntityUtil.setArmorLvl(mob, basicArmor);
							if (extraArmor != null && extraArmor.containsKey("enable")) {
								if (extraArmor.getBoolean("enable")) {
									EntityUtil.setArmorLvl(mob, EntityUtil.getArmorLvl(mob) + Green_Random.randomLong(extraArmor.getLongValue("min"), extraArmor.getLongValue("max")));
							}
							}
							
							
							//抗击退
							double knockbackResistence = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getDoubleValue("knock-resistence");
							EntityUtil.setKnockBackResistence(mob, knockbackResistence);
							
							//药水效果
							JSONArray jsonArray = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getJSONArray("extra-effects");
							if (jsonArray != null) {
								ArrayList<JSONObject> effects = (ArrayList<JSONObject>)jsonArray.toJavaList(JSONObject.class) ;
								if (effects != null) {
									if (!effects.isEmpty()) {
										for (JSONObject jsonObject:effects) {
											if (Green_Random.chance(jsonObject.getDoubleValue("chance"))) {
												int time = jsonObject.getIntValue("time");
												String type = jsonObject.getString("type").replaceAll("minecraft:", "").toUpperCase();
												int lvl = jsonObject.getIntValue("lvl");
												if (time <= 0) {
													time = Integer.MAX_VALUE;
												}
												PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(type), time, lvl, false, false, false);
												potionEffect.apply(mob);
											}
										}
									}
								}
							}
							
						//额外物品
						JSONArray jsonArray2 = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getJSONArray("extra-item-drops");
						if (jsonArray2 != null) {
							ArrayList<JSONObject> itemDrops = (ArrayList<JSONObject>) jsonArray2.toJavaList(JSONObject.class);
							if (itemDrops != null) {
								if (!itemDrops.isEmpty()) {
									Inventory inventory = Bukkit.createInventory(null, 36);
									for (JSONObject jsonObject:itemDrops) {
										if (Green_Random.chance(jsonObject.getDoubleValue("chance"))) {
											String nbt = jsonObject.getString("nbt");
											String type = jsonObject.getString("type").replaceAll("minecraft:", "").toUpperCase();
											int count = (int) Green_Random.randomLong(jsonObject.getLongValue("min-counts"), jsonObject.getLongValue("max-counts"));
											ItemStack item = new ItemStack(Material.valueOf(type), count);
											ItemStack nmsedItem = null;
											try {
												nmsedItem = ItemUtil.setNBTData(item,net.minecraft.nbt.MojangsonParser.a(nbt));
											} catch (CommandSyntaxException e) {
												e.printStackTrace();
											}
											if (jsonObject.getBooleanValue("random-durability")) {
												ItemUtil.setDurability(nmsedItem, (int) Green_Random.randomLong(1, ItemUtil.getMaxDurability(nmsedItem.getType())));
											}
											inventory.addItem(nmsedItem);
										}
									}
									if (!inventory.isEmpty()) {
										LootData.Data.put(mob.getUniqueId(), inventory);
									}else {
										inventory = null;
									}
								}
							}
						}
						
					//额外装备
					JSONArray jsonArray3 = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getJSONArray("extra-equipments");
					if (jsonArray3 != null) {
						ArrayList<JSONObject> equipments = (ArrayList<JSONObject>) jsonArray3.toJavaList(JSONObject.class);
						if (equipments != null) {
							if (!equipments.isEmpty()) {
									for (JSONObject jsonObject:equipments) {
												if (Green_Random.chance(jsonObject.getDoubleValue("chance"))) {
													String type = jsonObject.getString("type").replaceAll("minecraft:", "").toUpperCase();
													String nbt = jsonObject.getString("nbt");
													ItemStack equipment = new ItemStack(Material.getMaterial(type));
													ItemStack nbtedEquip = null;
													try {
														nbtedEquip = ItemUtil.setNBTData(equipment, MojangsonParser.a(nbt));
													} catch (CommandSyntaxException e) {
														e.printStackTrace();
													}
													ItemUtil.setDurability(nbtedEquip, (int) Green_Random.randomLong(1, ItemUtil.getMaxDurability(nbtedEquip.getType())));
													
													EntityEquipment entityEquipment = mob.getEquipment();
													
													if (ItemUtil.getEquipmentType(nbtedEquip) == EquipmentType.HELMET) {
														entityEquipment.setHelmet(nbtedEquip,true);
													}
													if (ItemUtil.getEquipmentType(nbtedEquip) == EquipmentType.CHESTPLATE) {
														entityEquipment.setChestplate(nbtedEquip, true);
													}
													if (ItemUtil.getEquipmentType(nbtedEquip) == EquipmentType.BOOT) {
														entityEquipment.setBoots(nbtedEquip, true);
													}
													if (ItemUtil.getEquipmentType(nbtedEquip) == EquipmentType.LEGGING) {
														entityEquipment.setLeggings(nbtedEquip, true);
													}
													if(ItemUtil.getEquipmentType(nbtedEquip) == EquipmentType.WEAPON) {
														entityEquipment.setItemInMainHand(nbtedEquip, true);
													}
												}
									}
							}
						}
					}
						
					//苦力怕重叠生成
							if (Configuration.CreeperSpawns != null) {
									if (!Configuration.CreeperSpawns.isEmpty()) {
										if (!mob.getScoreboardTags().contains("rider")) {
											if (Configuration.CreeperSpawns.containsKey(mob.getType())) {
												if (Green_Random.chance(Configuration.Configuration.getJSONObject("monsters").getJSONObject("creeper").getDoubleValue("spawn-on-chance"))) {
													int num = (int)Green_Random.randomLong(0, Configuration.CreeperSpawns.get(mob.getType()));
													if (num > 0) {
														ArrayList<Creeper> creepers = new ArrayList<Creeper>();
															for (int i = 0;i<num;i++) {
																	Creeper creeper = (Creeper) mob.getWorld().spawnEntity(mob.getLocation(), EntityType.CREEPER,SpawnReason.CUSTOM);
																	creeper.addScoreboardTag("rider");
																	if (creepers.isEmpty()) {
																		mob.addPassenger(creeper);
																	}else {
																		creepers.get(creepers.size() - 1).addPassenger(creeper);
																	}
																	creepers.add(creeper);
															}
													}
												}
										}
										}
									}
							}
							//增强AI
							if (Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getBooleanValue("enhance-ai")) {
								AILoader.loadAI(mob);
							}
							
							//额外物品
							JSONArray jsonArray4 = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getJSONArray("extra-items");
							if (jsonArray4 != null) {
								ArrayList<JSONObject> itemDrops = (ArrayList<JSONObject>) jsonArray4.toJavaList(JSONObject.class);
								if (itemDrops != null) {
									if (!itemDrops.isEmpty()) {
										Inventory inventory = Bukkit.createInventory(null, 36);
										for (JSONObject jsonObject:itemDrops) {
											if (Green_Random.chance(jsonObject.getDoubleValue("chance"))) {
												String nbt = jsonObject.getString("nbt");
												String type = jsonObject.getString("type").replaceAll("minecraft:", "").toUpperCase();
												int count = (int) Green_Random.randomLong(jsonObject.getLongValue("min-counts"), jsonObject.getLongValue("max-counts"));
												ItemStack item = new ItemStack(Material.valueOf(type), count);
												ItemStack nmsedItem = null;
												try {
													nmsedItem = ItemUtil.setNBTData(item,net.minecraft.nbt.MojangsonParser.a(nbt));
												} catch (CommandSyntaxException e) {
													e.printStackTrace();
												}
												if (jsonObject.getBooleanValue("random-durability")) {
													ItemUtil.setDurability(nmsedItem, (int) Green_Random.randomLong(1, ItemUtil.getMaxDurability(nmsedItem.getType())));
												}
												if (nmsedItem != null) {
													inventory.addItem(nmsedItem);
												}
											}
										}
										if (!inventory.isEmpty()) {
											ExtraInventory.Data.put(mob.getUniqueId(), inventory); 
										}else {
											inventory = null;
										}
									}
								}
							}
								if (event.getEntityType().equals(EntityType.GHAST) || event.getEntityType().equals(EntityType.PHANTOM) ||
										event.getEntityType().equals(EntityType.SHULKER)) {
									return;
								}
								//额外伤害
								double extraDamage = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getDoubleValue("extra-damage");
								EntityUtil.setAttackDamage(mob, EntityUtil.getAttackDamage(mob) + extraDamage);
								
								//击退
								double attackKnockback = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getDoubleValue("attack-knockback");
								EntityUtil.setAttackKnockBack(mob, attackKnockback);
								
								//移速
								double speed = Configuration.Configuration.getJSONObject("monsters").getJSONObject(Configuration.getConfigName(mob)).getDoubleValue("speed");
								EntityUtil.setMoveSpeed(mob, speed);
}


					
					@EventHandler
					public void onBossSpawn(EntitySpawnEvent event) {
								if (!(event.getEntity() instanceof Wither || event.getEntity() instanceof EnderDragon)) {
									return;
								}
								Mob boss = (Mob) event.getEntity();
								//视野距离
								EntityUtil.setFollowRange(boss, Configuration.Configuration.getJSONObject("gamerules").getDoubleValue("monster-search-range"));
								//��ǿAI
								if (Configuration.Configuration.getJSONObject("bosses").getJSONObject(Configuration.getConfigName(boss)).getBooleanValue("enhance-ai")) {
									AILoader.getAbilities(boss);
								}
								//�������ֵ
								double maxHealth = Configuration.Configuration.getJSONObject("bosses").getJSONObject(Configuration.getConfigName(boss)).getDoubleValue("health");
								EntityUtil.setMaxHealth(boss, maxHealth);
								boss.setHealth(maxHealth);
								
								//����ֵ
								double basicArmor =  Configuration.Configuration.getJSONObject("bosses").getJSONObject(Configuration.getConfigName(boss)).getDoubleValue("basic-armorlvl");
								JSONObject extraArmor =  Configuration.Configuration.getJSONObject("bosses." + Configuration.getConfigName(boss) + ".extra-armorlvl");
								EntityUtil.setArmorLvl(boss, basicArmor);
								if (extraArmor != null) {
									if (extraArmor.getBoolean("enable")) {
										EntityUtil.setArmorLvl(boss, EntityUtil.getArmorLvl(boss) + Green_Random.randomLong(extraArmor.getLongValue("min"), extraArmor.getLongValue("max")));
								}
								}
								
								//�����˺�
								double extraDamage = Configuration.Configuration.getJSONObject("bosses").getJSONObject(Configuration.getConfigName(boss)).getDoubleValue("extra-damage");
								EntityUtil.setAttackDamage(boss, EntityUtil.getAttackDamage(boss) + extraDamage);
								
								//��������
								double attackKnockback = Configuration.Configuration.getJSONObject("bosses").getJSONObject(Configuration.getConfigName(boss)).getDoubleValue("attack-knockback");
								EntityUtil.setAttackKnockBack(boss, attackKnockback);
								
								//������
								double knockbackResistence = Configuration.Configuration.getJSONObject("bosses").getJSONObject(Configuration.getConfigName(boss)).getDoubleValue("knock-resistence");
								EntityUtil.setKnockBackResistence(boss, knockbackResistence);
								
								//ҩˮЧ��
								JSONArray jsonArray = Configuration.Configuration.getJSONObject("bosses").getJSONObject(Configuration.getConfigName(boss)).getJSONArray("extra-effects");
								if (jsonArray != null) {
									ArrayList<JSONObject> effects = (ArrayList<JSONObject>)jsonArray .toJavaList(JSONObject.class);
									if (effects != null) {
										if (!effects.isEmpty()) {
											for (JSONObject jsonObject:effects) {
												if (Green_Random.chance(jsonObject.getDoubleValue("chance"))) {
													int time = jsonObject.getIntValue("time");
													String type = jsonObject.getString("type").replaceAll("minecraft:", "").toUpperCase();
													int lvl = jsonObject.getIntValue("lvl");
													if (time <= 0) {
														time = Integer.MAX_VALUE;
													}
													PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(type), time, lvl, false, false, false);
													potionEffect.apply(boss);
												}
											}
										}

									}
								}
					}
}
