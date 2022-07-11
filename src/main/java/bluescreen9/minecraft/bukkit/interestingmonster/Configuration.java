package bluescreen9.minecraft.bukkit.interestingmonster;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Configuration {
		protected static final String ConfigVersion = "0.0.1-MC-1.19";
				public static void loadConfig() {
					File dataFolder = Main.MonsterEnhance.getDataFolder();
					if (!dataFolder.exists()) {
						dataFolder.mkdirs();
					}
					if (!dataFolder.isDirectory()) {
						dataFolder.delete();
						dataFolder.mkdirs();
					}
						File ConfigFile = new File(dataFolder,"config.json");
						BufferedInputStream readStream = null;
						BufferedOutputStream writer = null;
						try {
							if (!ConfigFile.exists()) {
								ConfigFile.createNewFile();
								readStream = new BufferedInputStream(Main.MonsterEnhance.getResource("config.json"));
								writer = new BufferedOutputStream(new FileOutputStream(ConfigFile));
								writer.write(readStream.readAllBytes());
								readStream.close();
								writer.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								if (readStream != null) {
									readStream.close();	
								}
								if (writer != null) {
									writer.close();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						BufferedInputStream reader = null;
						try {
							reader = new BufferedInputStream(new FileInputStream(ConfigFile));
							byte[] data = reader.readAllBytes();
							Configuration = JSONObject.parseObject(new String(data,Charset.forName("utf-8")));
						} catch (Exception e) {
							e.printStackTrace();
						}
						finally {
							try {
								reader.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
						if (!Configuration.getString("config-version").equals(ConfigVersion)) {
								Main.MonsterEnhance.getLogger().warning("the config.json is outdated,please delete config.json to update the configuraton file");
						}
						
						for (String str:Configuration.getJSONObject("monsters").keySet()) {
								if (Configuration.getJSONObject("monsters").getJSONObject(str).getJSONObject("fireball") != null) {
										JSONArray jsonArray = Configuration.getJSONObject("monsters").getJSONObject(str).getJSONObject("fireball").getJSONArray("wont-damage-ls");
										if (jsonArray != null) {
											CannotDamage.put(EntityType.valueOf(str.toUpperCase().replaceAll("-", "_")), (ArrayList<EntityType>) jsonArray.toJavaList(EntityType.class));
										}
								}
								if (Configuration.getJSONObject("monsters").getJSONObject(str).getJSONObject("explosion") != null) {
									JSONArray jsonArray = Configuration.getJSONObject("monsters").getJSONObject(str).getJSONObject("explosion").getJSONArray("wont-damage-ls");
									if (jsonArray != null) {
										CannotDamage.put(EntityType.valueOf(str.toUpperCase().replaceAll("-", "_")), (ArrayList<EntityType>)jsonArray .toJavaList(EntityType.class));
									}
								}
						}
						
						for (String str:Configuration.getJSONObject("bosses").keySet()) {
							if (Configuration.getJSONObject("bosses").getJSONObject(str).getJSONObject("skull") != null) {
									JSONArray jsonArray = Configuration.getJSONObject("bosses").getJSONObject(str).getJSONObject("skull").getJSONArray("wont-damage-ls");
									if (jsonArray != null) {
										CannotDamage.put(EntityType.valueOf(str.toUpperCase().replaceAll("-", "_")), (ArrayList<EntityType>) jsonArray.toJavaList(EntityType.class));
									}
							}
							if (Configuration.getJSONObject("bosses." + str + ".fireball") != null) {
								JSONArray jsonArray = Configuration.getJSONObject("bosses").getJSONObject(str).getJSONObject("fireball").getJSONArray("wont-damage-ls");
								if (jsonArray != null) {
									CannotDamage.put(EntityType.valueOf(str.toUpperCase().replaceAll("-", "_")), (ArrayList<EntityType>) jsonArray.toJavaList(EntityType.class));
								}
							}
					}
						
						//������
						JSONArray jsonArray4 = Configuration.getJSONObject("monsters").getJSONObject("creeper").getJSONArray("can-spawn-on");
						if (jsonArray4 != null) {
								ArrayList<JSONObject> spawns = (ArrayList<JSONObject>) jsonArray4.toJavaList(JSONObject.class);
								for (JSONObject jsonObject:spawns) {
										EntityType type = EntityType.valueOf(jsonObject.getString("mob").toUpperCase());
										if (!CreeperSpawns.containsKey(type)) {
												CreeperSpawns.put(type, jsonObject.getIntValue("max"));
										}
								}
						}
				}
				
				protected static JSONObject Configuration;
				protected static HashMap<EntityType, ArrayList<EntityType>> CannotDamage = new HashMap<EntityType, ArrayList<EntityType>>();
				protected static HashMap<EntityType, Integer> CreeperSpawns = new HashMap<EntityType, Integer>();
				
				public static ArrayList<ItemStack> getExtraEquipments(EntityType tpye) {
					return null;
				}
				
				public static String getConfigName(EntityType entity_type) {
					return entity_type.name().toLowerCase().replaceAll(" ", "-").replaceAll("_", "-");
				}
				
				public static String getConfigName(Entity entity) {
					return entity.getType().name().toLowerCase().replaceAll(" ", "-").replaceAll("_", "-");
				}
				
				public static JSONObject copyConfig() {
						return Configuration;
				}
				
}
